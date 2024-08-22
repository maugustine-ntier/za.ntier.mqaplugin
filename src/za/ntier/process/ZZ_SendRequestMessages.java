package za.ntier.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.co.ntier.twilio.models.X_TW_Message;
import za.co.ntier.utils.SendMessage;
import za.ntier.models.X_R_RequestUpdate;

@org.adempiere.base.annotation.Process
public class ZZ_SendRequestMessages extends SvrProcess {



	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}
	int cnt = 0;

	@Override
	protected String doIt() throws Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT R_RequestUpdate_ID FROM R_RequestUpdate Where ZZ_Whatsapp_Sent = 'N' and AD_Client_ID = ?";
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				X_R_RequestUpdate x_R_RequestUpdate = new X_R_RequestUpdate(getCtx(),rs.getInt(1),get_TrxName());
				X_R_Request 
				String mess = x_R_RequestUpdate.getResult();
				MUser mUser = MUser.get(x_R_RequestUpdate.get)
				String To_Number = "0844627361";
				String retMess = SendMessage.send(getCtx(), getAD_Client_ID(), X_TW_Message.TWILIO_MESSAGE_TYPE_Whatsapp, To_Number,mess); 
				if (retMess.equals("Message Sent")) {
					x_R_RequestUpdate.setZZ_Whatsapp_Sent(true);
					x_R_RequestUpdate.saveEx();
				}
			}

		}

		catch (Exception ex)	{
			log.log(Level.SEVERE, selectQuery, ex);
		}
		finally
		{
			DB.close(rs,pstmt);
			rs = null;pstmt = null;
		}




		return "Number of Shipments Created : " + cnt;



	}


}
