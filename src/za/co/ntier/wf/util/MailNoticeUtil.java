package za.co.ntier.wf.util;

import java.util.Properties;
import org.compiere.model.MClient;
import org.compiere.model.MMailText;
import org.compiere.model.MNote;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.model.PO;
import org.compiere.model.X_AD_User;
import org.compiere.util.Env;
import org.compiere.util.CLogger;
import za.co.ntier.wf.model.MZZWFLines;
import za.co.ntier.wf.model.MZZWFLineRole;

public final class MailNoticeUtil {
    private static final CLogger log = CLogger.getCLogger(MailNoticeUtil.class);
    private MailNoticeUtil() {}
    public static void send(Properties ctx, int toUserId, int mailTextId, PO po, String mode, String trxName) {
        if (toUserId <= 0 || mailTextId <= 0) return;
        MMailText tpl = new MMailText(ctx, mailTextId, trxName);
        MUser to = new MUser(ctx, toUserId, trxName);
        tpl.setUser(to);
        String subject = tpl.getMailHeader();
        String body = tpl.getMailText();
        boolean doNotice = "N".equals(mode) || "B".equals(mode);
        boolean doEmail  = "E".equals(mode) || "B".equals(mode);
        if (doNotice && (X_AD_User.NOTIFICATIONTYPE_Notice.equals(to.getNotificationType()) ||
                         X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(to.getNotificationType()))) {
            MNote note = new MNote(ctx, 0, to.getAD_User_ID(), po.get_Table_ID(), po.get_ID(), subject, body, trxName);
            note.setAD_Org_ID(po.getAD_Org_ID());
            note.saveEx();
        }
        if (doEmail && (X_AD_User.NOTIFICATIONTYPE_EMail.equals(to.getNotificationType()) ||
                        X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(to.getNotificationType()))) {
            MClient client = MClient.get(ctx);
            MUser from = MUser.get(ctx, Env.getAD_User_ID(ctx));
            if (!client.sendEMail(from, to, subject, body, null, tpl.isHtml())) {
                log.fine("Email send failed for user " + toUserId);
            }
        }
    }
    public static void requestStepNotifyAll(MZZWFLines step, PO po, za.co.ntier.wf.model.MZZWFHeader hdr, Properties ctx, String trxName) {
        Integer colId = step.getZZ_Specific_Responsible_Col_ID();
        if (colId != null && colId > 0) {
            String colName = ADColumnUtil.getColumnName(ctx, colId, trxName);
            int uid = po.get_ValueAsInt(colName);
            if (uid > 0) send(ctx, uid, step.getMMailText_Request_ID(), po, hdr.getZZ_NotifyMode(), trxName);
        }
        for (int roleId : MZZWFLineRole.getRoleIds(step.get_ID(), ctx, trxName)) {
            for (MUserRoles ur : MUserRoles.getOfRole(ctx, roleId)) {
                if (ur.isActive()) send(ctx, ur.getAD_User_ID(), step.getMMailText_Request_ID(), po, hdr.getZZ_NotifyMode(), trxName);
            }
        }
    }
}
