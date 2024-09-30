package za.ntier.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.model.MUser;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import za.co.ntier.twilio.models.X_TW_Message;
import za.co.ntier.utils.SendMessage;

@org.adempiere.base.annotation.Process
public class ZZ_SendRequestMessages extends SvrProcess {



	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}
	int cnt = 0;

	@Override
	protected String doIt() throws Exception {
		requestUpdateSend();
		requestSend();
		return "Number of Messages Sent : " + cnt;



	} 

	private void requestUpdateSend() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT R_RequestUpdate_ID FROM R_RequestUpdate Where ZZ_Whatsapp_Sent = 'N' and AD_Client_ID = ?";
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				X_R_RequestUpdate x_R_RequestUpdate = new X_R_RequestUpdate(getCtx(),rs.getInt(1),get_TrxName());
				if (x_R_RequestUpdate.getSalesRep_ID() > 0) {
					String mess = x_R_RequestUpdate.getResult();
					MUser mUser = MUser.get(x_R_RequestUpdate.getSalesRep_ID());
					String To_Number = "+27844627361";
					String retMess = SendMessage.send(getCtx(), getAD_Client_ID(), X_TW_Message.TWILIO_MESSAGE_TYPE_Whatsapp, To_Number,mess); 
					if (retMess.equals("Message Sent")) {
						x_R_RequestUpdate.setZZ_Whatsapp_Sent(true);
						x_R_RequestUpdate.saveEx();
						cnt++;
					}
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
	}
	
	private void requestSend() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectQuery = "SELECT R_Request_ID FROM R_Request Where ZZ_Whatsapp_Sent = 'N' and AD_Client_ID = ?";
		try {
			pstmt = DB.prepareStatement(selectQuery, get_TrxName());
			pstmt.setInt(1, getAD_Client_ID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				X_R_Request x_R_Request = new X_R_Request(getCtx(),rs.getInt(1),get_TrxName());
				if (x_R_Request.getSalesRep_ID() > 0) {
					String mess = x_R_Request.getSummary();
					MUser mUser = MUser.get(x_R_Request.getSalesRep_ID());
					String To_Number = "+27844627361";
					String retMess = SendMessage.send(getCtx(), getAD_Client_ID(), X_TW_Message.TWILIO_MESSAGE_TYPE_Whatsapp, To_Number,mess); 
					if (retMess.equals("Message Sent")) {
						x_R_Request.setZZ_Whatsapp_Sent(true);
						x_R_Request.saveEx();
						cnt++;
					}
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
	}


}
