package za.co.ntier.wf.process;

import java.util.Properties;

import za.ntier.models.X_ZZ_WF_Audit;

public final class AuditUtil {
    private AuditUtil() {}
    public static void createAudit(Properties ctx, String trxName,
                                   int adTableId, int recordId, int lineId,
                                   String action, String oldStatus, String newStatus,
                                   String oldAction, String newAction, String comment,int actorID) {
        X_ZZ_WF_Audit a = new X_ZZ_WF_Audit(ctx, 0, trxName);
        a.setAD_Table_ID(adTableId);
        a.setRecord_ID(recordId);
        a.setZZ_WF_Lines_ID(lineId);
        a.setActionTaken(action);
        a.setOldStatus(oldStatus);
        a.setNewStatus(newStatus);
        a.setOldAction(oldAction);
        a.setNewAction(newAction);
        a.setComment(comment);
        a.setActor_AD_User_ID(actorID);
        a.saveEx();
    }
}
