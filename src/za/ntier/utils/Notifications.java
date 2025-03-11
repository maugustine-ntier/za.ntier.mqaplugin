package za.ntier.utils;

import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.util.ProcessUtil;
import org.compiere.model.MClient;
import org.compiere.model.MNote;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.model.MUserRoles;
import org.compiere.model.X_AD_User;
import org.compiere.util.CLogger;

import za.ntier.models.X_ZZ_Petty_Cash_Advance_Hdr;

public class Notifications {

	public final static int FROM_EMAIL_USER_ID = MSysConfig.getIntValue("FROM_EMAIL_USER_ID",1000011);
	private static final CLogger log = CLogger.getCLogger(ProcessUtil.class);

	public static void sendNotification(int ad_User_ID,int zz_Petty_Cash_Advance_Hdr_ID,String subject,String message,int ad_Message_ID,Properties ctx,String trxName) {
		MUser mUser = new MUser(ctx, ad_User_ID, null);
		if (X_AD_User.NOTIFICATIONTYPE_Notice.equals(mUser.getNotificationType()) ||
				X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(mUser.getNotificationType())	) {
			MNote note = new MNote(ctx, ad_Message_ID, ad_User_ID,
					X_ZZ_Petty_Cash_Advance_Hdr.Table_ID, zz_Petty_Cash_Advance_Hdr_ID, 
					subject, message.toString(), trxName);
			note.saveEx();
		}
		if (X_AD_User.NOTIFICATIONTYPE_EMail.equals(mUser.getNotificationType()) ||
				X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(mUser.getNotificationType())) {
			MClient client = MClient.get(ctx);
			MUser from = MUser.get(ctx, FROM_EMAIL_USER_ID);
			if (!client.sendEMail(from, mUser, subject, message, null)) {
				if (log.isLoggable(Level.FINE)) log.fine("Problem Sending Email.  Please contact Support");
			}
		}
	}


	public static void sendNotificationToRole(int ad_Role_ID,int zz_Petty_Cash_Advance_Hdr_ID,String subject,String message,int ad_Message_ID,Properties ctx,String trxName) {
		MUserRoles [] mUserRoles = MUserRoles.getOfRole(ctx, ad_Role_ID);
		for (MUserRoles mUserRole : mUserRoles) {
			if (mUserRole.isActive()) {
				sendNotification(mUserRole.getAD_User_ID(),zz_Petty_Cash_Advance_Hdr_ID,subject,message,ad_Message_ID,ctx,trxName);
			}
		}
	}
}
