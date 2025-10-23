package za.ntier.service;

import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;

import za.ntier.model.Approval;
import za.ntier.models.MInvoiceBatch_New;
import za.ntier.models.X_ZZ_Monthly_Levy_Files;
import za.ntier.models.X_ZZ_Monthly_Levy_Files_Hdr;
import za.ntier.repo.ApprovalsRepository;
import za.ntier.repo.BatchRepository;
import za.ntier.repo.LevyFilesRepository;
import za.ntier.repo.PartnerRepository;
import za.ntier.strategy.ApprovalAggregationStrategy;
import za.ntier.strategy.CatchUpAggregation;
import za.ntier.strategy.LegacyYearAggregation;
import za.ntier.utils.DescriptionBuilder;
import za.ntier.utils.MoneyMath;
import za.ntier.utils.RowGrouping;
import za.ntier.utils.YearUtil;

public class LevyBatchCreationService {

    private final java.util.Properties ctx;
    private final String trxName;
    private final int adUserId;
    private final int defaultCurrencyId;
    private final int p_C_Charge_ID;
    private final int p_C_DocType_ID;
    private final Timestamp p_DateDoc;

    private final ApprovalsRepository approvalsRepo;
    private final LevyFilesRepository levyRepo;
    private final PartnerRepository partnerRepo;
    private final BatchRepository batchRepo;

    public LevyBatchCreationService(java.util.Properties ctx, String trxName, int adUserId,
                                    int defaultCurrencyId, int chargeId, int docTypeId, Timestamp dateDoc) {
        this.ctx = ctx;
        this.trxName = trxName;
        this.adUserId = adUserId;
        this.defaultCurrencyId = defaultCurrencyId;
        this.p_C_Charge_ID = chargeId;
        this.p_C_DocType_ID = docTypeId;
        this.p_DateDoc = dateDoc;

        this.approvalsRepo = new ApprovalsRepository(ctx, trxName);
        this.levyRepo = new LevyFilesRepository(ctx, trxName);
        this.partnerRepo = new PartnerRepository(ctx, trxName);
        this.batchRepo = new BatchRepository(ctx, trxName, adUserId, p_DateDoc);
    }

    public String createBatchesFromHeader(int hdrId) {
        X_ZZ_Monthly_Levy_Files_Hdr hdr = levyRepo.getHeaderById(hdrId);
        if (hdr == null || hdr.get_ID() <= 0) throw new AdempiereException("Header not found: ID=" + hdrId);

        int chargeId = (p_C_Charge_ID > 0) ? p_C_Charge_ID : batchRepo.resolveDefaultChargeId();
        if (chargeId <= 0) throw new AdempiereException("C_Charge_ID required.");

        int docTypeId = batchRepo.resolveDocTypeId(p_C_DocType_ID);
        if (docTypeId <= 0) throw new AdempiereException("Could not resolve C_DocType_ID (ARI/API).");

        int currencyId = batchRepo.resolveCurrencyId(defaultCurrencyId);
        if (currencyId <= 0) throw new AdempiereException("Could not resolve C_Currency_ID.");

        // rows (unprocessed) for this header
        java.util.List<X_ZZ_Monthly_Levy_Files> rows = levyRepo.getUnprocessedRows(hdrId);
        if (rows.isEmpty()) return "No ZZ_Monthly_Levy_Files rows for header ID " + hdrId;

        // group by BP->Year->Rows, and resolve BP/location
        RowGrouping.GroupResult grouped = RowGrouping.groupByBpYear(ctx, trxName, rows, partnerRepo);

        // load approvals (asc) for the BPs in this header
        java.util.List<Approval> approvalsAsc = approvalsRepo.loadApprovalsAscForBps(new java.util.ArrayList<>(grouped.bpById.keySet()));
        if (approvalsAsc.isEmpty()) {
            return stats("No approvals found for any BP", 0, rows.size(), 0, grouped.skippedNoBP, grouped.skippedNotAL, grouped.skippedZero);
        }

        // prepare strategies
        ApprovalAggregationStrategy legacy = new LegacyYearAggregation(2010, 2024);
        ApprovalAggregationStrategy catchup = new CatchUpAggregation(2010, Integer.MAX_VALUE);

        // batch cache by year
        java.util.Map<String, MInvoiceBatch_New> batchByYear = new java.util.LinkedHashMap<>();
        java.util.Map<String, Integer> lineNoByYear = new java.util.LinkedHashMap<>();

        int createdLines = 0;

        for (Approval ap : approvalsAsc) {
            final int bpId = ap.bpId();
            final int approvedYear = ap.year();

            if (!approvalsRepo.hasApprovedForYear(bpId, Integer.toString(approvedYear))) {
                continue; // defensive recheck
            }

            java.util.Map<Integer, java.util.List<X_ZZ_Monthly_Levy_Files>> bpRowsByYear = grouped.rowsByBPYear.get(bpId);
            if (bpRowsByYear == null || bpRowsByYear.isEmpty()) continue;

            java.util.Map<Integer, java.util.List<X_ZZ_Monthly_Levy_Files>> contributing;
            if (YearUtil.isLegacy(approvedYear)) {
                contributing = legacy.contributingRows(bpRowsByYear, approvedYear);
            } else {
                contributing = catchup.contributingRows(bpRowsByYear, approvedYear);
            }
            if (contributing.isEmpty()) continue;

            // compute sums (include +/-), NEGATE for the posted amount
            java.util.Map<Integer, java.math.BigDecimal> perYear = MoneyMath.sumPerYear(contributing, r -> ((X_ZZ_Monthly_Levy_Files)r).getZZ_MG());
            java.math.BigDecimal grand = MoneyMath.sumAll(perYear.values());
            if (MoneyMath.isZero(grand)) {
                grouped.skippedZero++;
                continue;
            }

            String yearKey = Integer.toString(approvedYear);
            MInvoiceBatch_New batch = batchRepo.ensureBatchForYear(batchByYear, hdr, currencyId, yearKey);
            int lineNo = batchRepo.nextLineNo(lineNoByYear, yearKey, batch.getC_InvoiceBatch_ID());

            // build description with NEGATED per-year shown values
            String desc = DescriptionBuilder.buildAggregateDescription(
                    "MG", hdr.get_ID(), approvedYear, MoneyMath.negateMap(perYear));

            // line amount is NEGATED grand
            java.math.BigDecimal lineAmt = grand.negate();

            int bpLocId = grouped.bpLocById.getOrDefault(bpId, 0);
            int lineId = batchRepo.createBatchLine(
                    batch.getC_InvoiceBatch_ID(), docTypeId, lineNo,
                    bpId, bpLocId, chargeId, p_DateDoc,
                    lineAmt, desc
            );

            // link all contributing rows to this single line
            levyRepo.linkRowsToLine(contributing, lineId);

            // roll-up batch documentAmt by original grand (non-negated)
            batchRepo.addToBatchAmount(batch, grand);

            createdLines++;
        }

        return stats("OK", batchByYear.size(), rows.size(), createdLines,
                     grouped.skippedNoBP, grouped.skippedNotAL, grouped.skippedZero);
    }

    private static String stats(String prefix, int batches, int sourceRows, int createdLines,
                                int skippedNoBP, int skippedNotAL, int skippedZero) {
        return String.format(
            "%s. Batches created: %d. Source rows: %d. Lines created: %d. Skipped (no BP): %d, Skipped (Not L Number): %d, Skipped (net zero): %d",
            prefix, batches, sourceRows, createdLines, skippedNoBP, skippedNotAL, skippedZero
        );
    }
}
