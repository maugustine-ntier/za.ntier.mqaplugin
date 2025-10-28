package za.co.ntier.wf.process;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.base.annotation.Parameter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import za.co.ntier.wf.model.MZZWFHeader;
import za.co.ntier.wf.model.MZZWFLines;
import za.co.ntier.wf.util.MailNoticeUtil;

public class ZZ_WF_RunProcessOLD extends SvrProcess {
    @Parameter(name="Approve")
    private String pApprove; // 'Y' or 'N' or null
    @Parameter(name="Comment")
    private String pComment;
    private PO po;
    private String trxName;
    private Properties ctx;
    private Timestamp now;
    @Override
    protected void prepare() {
        ctx = Env.getCtx();
        trxName = get_TrxName();
        now = new Timestamp(System.currentTimeMillis());
        MTable t = MTable.get(ctx, getTable_ID());
        po = t.getPO(getRecord_ID(), trxName);
    }
    @Override
    protected String doIt() {
        require(po, "ZZ_DocStatus");
        require(po, "ZZ_DocAction");
        String curStatus = po.get_ValueAsString("ZZ_DocStatus");
        String curAction = po.get_ValueAsString("ZZ_DocAction");
        MZZWFHeader hdr = MZZWFHeader.forTable(ctx, po.get_Table_ID(), trxName);
        if (hdr == null || !hdr.isActive())
            throw new AdempiereException("No active workflow found for this table.");
        MZZWFLines currentStep = (curAction != null && !curAction.isBlank())
                ? MZZWFLines.findByStatusAndAction(ctx, hdr.get_ID(), curStatus, curAction, trxName)
                : null;
        if (currentStep != null) {
            boolean approve;
            if (pApprove == null || pApprove.isBlank())
                throw new AdempiereException("Please indicate Approve=Y or Approve=N.");
            approve = "Y".equalsIgnoreCase(pApprove.trim());
            doApproveReject(hdr, currentStep, approve, pComment);
        } else {
            doRequest(hdr, curStatus);
        }
        return "@OK@";
    }
    private void doRequest(MZZWFHeader hdr, String curStatus) {
        MZZWFLines step = MZZWFLines.findFirstByAllowedFromStatus(ctx, hdr.get_ID(), curStatus, trxName);
        if (step == null)
            throw new AdempiereException("No workflow step matches current status: " + curStatus);
        String oldAction = po.get_ValueAsString("ZZ_DocAction");
        po.set_ValueOfColumn("ZZ_DocAction", step.getSetDocAction());
        po.saveEx();
        MailNoticeUtil.requestStepNotifyAll(step, po, hdr, ctx, trxName);
        AuditUtil.createAudit(ctx, trxName, po.get_Table_ID(), po.get_ID(), step.get_ID(),
                "REQUEST", curStatus, curStatus, oldAction, step.getSetDocAction(), null);
    }
    private void doApproveReject(MZZWFHeader hdr, MZZWFLines step, boolean approve, String comment) {
        int actor = Env.getAD_User_ID(ctx);
        if (!AuthUtil.isActorAuthorized(ctx, trxName, step, po, actor))
            throw new AdempiereException("You are not authorized to approve/reject this step.");
        String curStatus = po.get_ValueAsString("ZZ_DocStatus");
        String curAction = po.get_ValueAsString("ZZ_DocAction");
        String nextStatus = approve ? step.getNextStatusOnApprove() : step.getNextStatusOnReject();
        po.set_ValueOfColumn("ZZ_DocStatus", nextStatus);
        po.set_ValueOfColumn("ZZ_DocAction", null);
        po.saveEx();
        int submitter = po.getCreatedBy();
        int tmpl = approve ? step.getMMailText_Approved_ID() : step.getMMailText_Rejected_ID();
        if (submitter > 0 && tmpl > 0) {
            MailNoticeUtil.send(ctx, submitter, tmpl, po, hdr.getZZ_NotifyMode(), trxName);
        }
        AuditUtil.createAudit(ctx, trxName, po.get_Table_ID(), po.get_ID(), step.get_ID(),
                approve ? "APPROVE" : "REJECT",
                curStatus, nextStatus, curAction, null, comment);
        MZZWFLines nxt = MZZWFLines.findFirstByAllowedFromStatus(ctx, hdr.get_ID(), nextStatus, trxName);
        if (nxt != null) {
            po.set_ValueOfColumn("ZZ_DocAction", nxt.getSetDocAction());
            po.saveEx();
            MailNoticeUtil.requestStepNotifyAll(nxt, po, hdr, ctx, trxName);
            AuditUtil.createAudit(ctx, trxName, po.get_Table_ID(), po.get_ID(), nxt.get_ID(),
                    "REQUEST", nextStatus, nextStatus, null, nxt.getSetDocAction(), "Auto-advanced");
        }
    }
    private static void require(PO po, String col) {
        if (po.get_ColumnIndex(col) < 0)
            throw new AdempiereException("Required column missing: " + col);
    }
}
