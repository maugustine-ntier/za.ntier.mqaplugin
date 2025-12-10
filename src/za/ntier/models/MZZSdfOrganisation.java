package za.ntier.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MClient;
import org.compiere.model.MMailText;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class MZZSdfOrganisation extends X_ZZSdfOrganisation {

    private static final long serialVersionUID = 1L;
    private static final CLogger log = CLogger.getCLogger(MZZSdfOrganisation.class);

    // Your fixed mail template UUID
    private static final String SDF_APPROVED_MAILTEXT_UU = "4ace9d73-8349-44a6-8d2d-5a0838c79362";

    public MZZSdfOrganisation(Properties ctx, int ZZSdfOrganisation_ID, String trxName) {
        super(ctx, ZZSdfOrganisation_ID, trxName);
    }

    public MZZSdfOrganisation(Properties ctx, int ZZSdfOrganisation_ID, String trxName, String... virtualColumns) {
        super(ctx, ZZSdfOrganisation_ID, trxName, virtualColumns);
    }

    public MZZSdfOrganisation(Properties ctx, String ZZSdfOrganisation_UU, String trxName) {
        super(ctx, ZZSdfOrganisation_UU, trxName);
    }

    public MZZSdfOrganisation(Properties ctx, String ZZSdfOrganisation_UU, String trxName, String... virtualColumns) {
        super(ctx, ZZSdfOrganisation_UU, trxName, virtualColumns);
    }

    public MZZSdfOrganisation(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (!success) {
            return false;
        }

        if (!newRecord) {
            if (is_ValueChanged(COLUMNNAME_ZZ_DocStatus)
                    && ZZ_DOCSTATUS_Approved.equals(getZZ_DocStatus())) {

                Object oldStatus = get_ValueOld(COLUMNNAME_ZZ_DocStatus);
                if (oldStatus != null && !ZZ_DOCSTATUS_Approved.equals(oldStatus)) {
                    MClient client = MClient.get(Env.getCtx());
                    sendSdfApprovedMail(client);
                }
            }
        }
        return super.afterSave(newRecord, success);
    }

    /**
     * Send email to the SDF linked to this organisation when approved.
     */
    private void sendSdfApprovedMail(MClient client) {
        // 1) Find SDF user
        int adUserId = getSdfUserId();
        if (adUserId <= 0) {
            log.warning("No SDF AD_User found for ZZSdfOrganisation_ID=" + getZZSdfOrganisation_ID());
            return;
        }

        MUser user = MUser.get(getCtx(), adUserId);
        if (user == null) {
            log.warning("MUser not found for AD_User_ID=" + adUserId);
            return;
        }

        String to = user.getEMail();
        if (to == null || to.trim().isEmpty()) {
            log.warning("SDF user has no email. AD_User_ID=" + adUserId);
            return;
        }

        // 2) Load mail text by UU
        MMailText mText = new MMailText(getCtx(), SDF_APPROVED_MAILTEXT_UU, get_TrxName());
        if (mText.get_ID() <= 0) {
            log.warning("Mail text not found for UU=" + SDF_APPROVED_MAILTEXT_UU);
            return;
        }

        mText.setUser(user);
        try {
            mText.setPO(this, true); // if your version has (PO, boolean)
        } catch (Throwable t) {
            mText.setPO(this);       // fallback for older versions
        }

        String subject = mText.getMailHeader();
        String message = mText.getMailText(true); // html

        if (subject == null || subject.trim().isEmpty()) {
            subject = "SDF Organisation Approved";
        }

        // 3) Send via client
        boolean sent = client.sendEMail(to, subject, message, null, true);
        if (!sent) {
            log.warning("Failed to send SDF approval email to " + to
                    + " for ZZSdfOrganisation_ID=" + getZZSdfOrganisation_ID());
        } else {
            log.info("SDF approval email sent to " + to
                    + " for ZZSdfOrganisation_ID=" + getZZSdfOrganisation_ID());
        }
    }

    /**
     * FROM adempiere.zzsdforganisation orglink
     * JOIN adempiere.zzsdf sdf ON orglink.zzsdf_id = sdf.zzsdf_id
     * JOIN adempiere.ad_user usr ON sdf.ad_user_id = usr.ad_user_id
     */
    private int getSdfUserId() {
        String sql =
                "SELECT u.ad_user_id " +
                "FROM adempiere.zzsdforganisation orglink " +
                "JOIN adempiere.zzsdf sdf ON orglink.zzsdf_id = sdf.zzsdf_id " +
                "JOIN adempiere.ad_user u ON sdf.ad_user_id = u.ad_user_id " +
                "WHERE orglink.zzsdforganisation_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = DB.prepareStatement(sql, get_TrxName());
            pstmt.setInt(1, getZZSdfOrganisation_ID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            log.severe("Error getting SDF AD_User_ID: " + e.getMessage());
        } finally {
            DB.close(rs, pstmt);
        }
        return 0;
    }
}

