package org.adempiere.base.event;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MClient;
import org.compiere.model.MRequest;
import org.compiere.model.MRequestType;
import org.compiere.model.MRequestUpdate;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.event.Event;

import za.co.ntier.twilio.models.X_TW_Message;
import za.co.ntier.utils.SendMessage;
import za.ntier.models.I_R_Request;
import za.ntier.models.I_R_RequestUpdate;
import za.ntier.models.X_AD_User;
import za.ntier.models.X_R_Request;

@Component(

		   reference = @Reference( 
		                 name = "IEventManager", bind = "bindEventManager", unbind="unbindEventManager", 
		                 policy = ReferencePolicy.STATIC, cardinality =ReferenceCardinality.MANDATORY, service = IEventManager.class)
		   )

public class NtierEventHandler extends AbstractEventHandler implements ManagedService {
	
	private static final CLogger s_log = CLogger.getCLogger (RequestEventHandler.class);

	public NtierEventHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doHandleEvent(Event event) {
		String topic = event.getTopic();
		if (topic.equals(IEventTopicsNtier.REQUEST_SEND_WHATSAPP)) {
			RequestSendEMailEventData eventData = (RequestSendEMailEventData) event.getProperty(EventManager.EVENT_DATA);
			// String To_Number = "+27844627361";
			String To_Number = eventData.getTo().getPhone();
			try {
				String retMess = SendMessage.send(Env.getCtx(), eventData.getClient().getAD_Client_ID(), X_TW_Message.TWILIO_MESSAGE_TYPE_Whatsapp, To_Number,eventData.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 			
		}
		else if (topic.equals(IEventTopics.PO_AFTER_NEW)) 
		{
			PO po = getPO(event);
			if (po.get_TableName().equals(I_R_RequestUpdate.Table_Name))
			{
				MRequestUpdate ru = (MRequestUpdate) po;
				MRequest r = new MRequest(Env.getCtx(),ru.getR_Request_ID(),ru.get_TrxName());
				
				MRequestType rt = r.getRequestType();
				if (ignoreRequestTypes.contains(rt.getName())) {
					return;
				}				
				afterSaveRequest(r, topic.equals(IEventTopics.PO_AFTER_NEW));
			}
			if (po.get_TableName().equals(I_R_Request.Table_Name))
			{
				MRequest r = (MRequest) po;
				
				MRequestType rt = r.getRequestType();
				if (ignoreRequestTypes.contains(rt.getName())) {
					return;
				}
				afterSaveRequest(r, topic.equals(IEventTopics.PO_AFTER_NEW));
			}
		} else if (topic.equals(IEventTopics.PO_BEFORE_CHANGE)) {
			PO po = getPO(event);
			X_R_Request r = (X_R_Request) po;
			r.setSalesRep_OLD_ID(r.get_ValueOldAsInt(I_R_Request.COLUMNNAME_SalesRep_ID));
			r.saveEx();
			
		}
	}

	@Override
	protected void initialize() {
		registerEvent(IEventTopicsNtier.REQUEST_SEND_WHATSAPP);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, I_R_Request.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, I_R_RequestUpdate.Table_Name);
		registerTableEvent(IEventTopics.PO_BEFORE_CHANGE, I_R_Request.Table_Name);
	}
	
	/**
	 * Handle before update of R_Request record
	 * @param r
	 * @param newRecord
	 * @return error message or null
	 */
	private String afterSaveRequest(MRequestUpdate ru,MRequest r, boolean newRecord)
	{
		//	New
	//	if (newRecord) {
	//		return null;
	//	}
		
		//	Change Log
		r.setIsChanged(false);
		ArrayList<String> sendInfo = new ArrayList<String>();
		//
		if (checkChange(r, ru, "R_RequestType_ID")) {
			sendInfo.add("R_RequestType_ID");
		}
		if (checkChange(r, ru, "R_Group_ID")) {
			sendInfo.add("R_Group_ID");
		}
		if (checkChange(r, ru, "R_Category_ID")) {
			sendInfo.add("R_Category_ID");
		}
		if (checkChange(r, ru, "R_Status_ID")) {
			sendInfo.add("R_Status_ID");
		}
		if (checkChange(r, ru, "R_Resolution_ID")) {
			sendInfo.add("R_Resolution_ID");
		}
		//
		if (checkChange(r, ru, "SalesRep_ID"))
		{
			//	Sender
			int AD_User_ID = Env.getContextAsInt(r.getCtx(), Env.AD_USER_ID);
			if (AD_User_ID == 0) {
				AD_User_ID = r.getUpdatedBy();
			}
			int oldSalesRepID = new X_R_Request(r.getCtx(), r.getR_Request_ID(), r.get_TrxName()).getSalesRep_OLD_ID();
			if (r.getSalesRep_ID() != oldSalesRepID)
			{
				//  RequestActionTransfer - Request {0} was transferred by {1} from {2} to {3}
				Object[] args = new Object[] {r.getDocumentNo(), 
					MUser.getNameOfUser(AD_User_ID), 
					MUser.getNameOfUser(oldSalesRepID),
					MUser.getNameOfUser(r.getSalesRep_ID())
					};
				String msg = Msg.getMsg(r.getCtx(), "RequestActionTransfer", args);
				r.addToResult(msg);
				sendInfo.add("SalesRep_ID");
			}
		}
		checkChange(r, ru, "AD_Role_ID");
		//
		checkChange(r, ru, "Priority");
		if (checkChange(r, ru, "PriorityUser")) {
			sendInfo.add("PriorityUser");
		}
		if (checkChange(r, ru, "IsEscalated")) {
			sendInfo.add("IsEscalated");
		}
		if (ru.isNewInfo() || sendInfo.size() > 0)
		{
			// Note that calling the notifications from beforeSave is causing the
			// new interested are not notified if the RV_RequestUpdates view changes
			// this is, when changed the sales rep (solved in sendNotices)
			// or when changed the request category or group or contact (unsolved - the old ones are notified)
			sendNotices(r,ru, sendInfo);
		}
		
		return null;
	}
	
	/**
	 * Handle after save of R_Request record
	 * @param r
	 * @param newRecord
	 * @return error message or null
	 */
	private String afterSaveRequest(MRequest r, boolean newRecord)
	{
		
			sendNotices(r,null, new ArrayList<String>());
		
		return null;
	}
	
	/**
	 * 	Process changes
	 *	@param ra request action
	 *	@param columnName column
	 *	@return true if columnName has changes
	 */
	public boolean checkChange (MRequest r, MRequestUpdate ru, String columnName)
	{
		String oldValue = r.get_ValueAsString(columnName);
		String newValue = ru.get_ValueAsString(columnName);
		if (oldValue == null && newValue == null) {
			return false;
		}
		if ((oldValue == null && newValue != null) || (newValue == null && oldValue != null)) {
			return true;
		}
		if (oldValue.equals(newValue)) {
			return true;
		}
		return false;
	}	//	checkChange
	
	/**
	 * 	Send Update EMail/Notices
	 * 	@param list list of changes
	 */
	private void sendNotices(MRequest r,MRequestUpdate ru, ArrayList<String> list)
	{
		//	Subject
		String subject = Msg.translate(r.getCtx(), "R_Request_ID") 
			+ " " + Msg.getMsg(r.getCtx(), "Updated") + ": " + r.getDocumentNo();
		//	Message
		StringBuilder message = new StringBuilder();
		//		UpdatedBy: Joe
		int UpdatedBy = Env.getAD_User_ID(r.getCtx());
		MUser from = MUser.get(r.getCtx(), UpdatedBy);
		if (from != null) {
			message.append(Msg.translate(r.getCtx(), "UpdatedBy")).append(": ")
				.append(from.getName());
		}
		//		LastAction/Created: ...	
		if (r.getDateLastAction() != null) {
			message.append("\n").append(Msg.translate(r.getCtx(), "DateLastAction"))
				.append(": ").append(r.getDateLastAction());
		} else {
			message.append("\n").append(Msg.translate(r.getCtx(), "Created"))
				.append(": ").append(r.getCreated());
		}
		//	Changes
		for (int i = 0; i < list.size(); i++)
		{
			String columnName = list.get(i);
			message.append("\n").append(Msg.getElement(r.getCtx(), columnName))
				.append(": ").append(r.get_DisplayValue(columnName, false))
				.append(" -> ").append(r.get_DisplayValue(columnName, true));
		}
		//	NextAction
		if (r.getDateNextAction() != null) {
			message.append("\n").append(Msg.translate(r.getCtx(), "DateNextAction"))
				.append(": ").append(r.getDateNextAction());
		}
		message.append(MRequest.SEPARATOR)
			.append(r.getSummary());
		if (r.getResult() != null) {
			message.append("\n----------\n").append(r.getResult());
		}
		message.append(getMailTrailer(r, null));
		File pdf = r.createPDF();
		if (s_log.isLoggable(Level.FINER)) {
			s_log.finer(message.toString());
		}
		
		//	Prepare sending Notice/Mail
		MClient client = MClient.get(r.getCtx());
		//	Reset from if external
		if (from.getEMailUser() == null || from.getEMailUserPW() == null) {
			from = null;
		}
		//
		ArrayList<Integer> userList = new ArrayList<Integer>();
		final String sql = "SELECT u.AD_User_ID, u.NotificationType, u.EMail, u.Name, MAX(r.AD_Role_ID),u.phone "
			+ "FROM RV_RequestUpdates_Only ru"
			+ " INNER JOIN AD_User u ON (ru.AD_User_ID=u.AD_User_ID OR u.AD_User_ID=?)"
			+ " LEFT OUTER JOIN AD_User_Roles r ON (u.AD_User_ID=r.AD_User_ID) "
			+ "WHERE ru.R_Request_ID=? "
			+ "GROUP BY u.AD_User_ID, u.NotificationType, u.EMail, u.Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, r.get_TrxName());
			pstmt.setInt (1, r.getSalesRep_ID());
			pstmt.setInt (2, r.getR_Request_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				int AD_User_ID = rs.getInt(1);
				String NotificationType = rs.getString(2);
				if (NotificationType == null) {
					continue; // Martin added
				}
				String email = rs.getString(3);
				String Name = rs.getString(4);
				String phone = rs.getString(5);
				//	Role
				int AD_Role_ID = rs.getInt(5);
				if (rs.wasNull()) {
					AD_Role_ID = -1;
				}
				
				//	No confidential to externals
				if (AD_Role_ID == -1 
					&& (r.getConfidentialTypeEntry().equals(MRequest.CONFIDENTIALTYPE_Internal)
						|| r.getConfidentialTypeEntry().equals(MRequest.CONFIDENTIALTYPE_PrivateInformation))) {
					continue;
				}
				
				if (X_AD_User.NOTIFICATIONTYPE_None.equals(NotificationType))
				{
					if (s_log.isLoggable(Level.CONFIG)) {
						s_log.config("Opt out: " + Name);
					}
					continue;
				}
				boolean notice = false;
				if (X_AD_User.NOTIFICATIONTYPE_Whatsapp.equals(NotificationType) && (phone == null || phone.length() == 0)) {
					notice = true;
				}
				if (X_AD_User.NOTIFICATIONTYPE_WhatsappPlusEmail.equals(NotificationType) && ((email == null || email.length() == 0) && (phone == null || phone.length() == 0))) {
					notice = true;
				}
				if (notice)
				{
					if (AD_Role_ID >= 0) {
						NotificationType = X_AD_User.NOTIFICATIONTYPE_Notice;
					} else
					{
						if (s_log.isLoggable(Level.CONFIG)) {
							s_log.config("No EMail and phone: " + Name);
						}
						continue;
					}
				}
				if (X_AD_User.NOTIFICATIONTYPE_Notice.equals(NotificationType)
					&& AD_Role_ID < 0)
				{
					if (s_log.isLoggable(Level.CONFIG)) {
						s_log.config("No internal User: " + Name);
					}
					continue;
				}

				//	Check duplicate receivers
				Integer ii = Integer.valueOf(AD_User_ID);
				if (userList.contains(ii)) {
					continue;
				}
				userList.add(ii);
				//
				MUser to = MUser.get (r.getCtx(), AD_User_ID);
				if (X_AD_User.NOTIFICATIONTYPE_WhatsappPlusEmail.equals(NotificationType) || X_AD_User.NOTIFICATIONTYPE_Whatsapp.equals(NotificationType))
				{
					RequestSendEMailEventData eventData = new RequestSendEMailEventData(client, from, to, subject, message.toString(), pdf, r.getR_Request_ID());
					Event event = EventManager.newEvent(IEventTopicsNtier.REQUEST_SEND_WHATSAPP, eventData, true);
					EventManager.getInstance().postEvent(event);
				}
				//	Send Mail
				if (X_AD_User.NOTIFICATIONTYPE_WhatsappPlusEmail.equals(NotificationType))
				{
					RequestSendEMailEventData eventData = new RequestSendEMailEventData(client, from, to, subject, message.toString(), pdf, r.getR_Request_ID());
					Event event = EventManager.newEvent(IEventTopics.REQUEST_SEND_EMAIL, eventData, true);
					EventManager.getInstance().postEvent(event);
				}
				//	Send Note
				/*
				 * if (X_AD_User.NOTIFICATIONTYPE_Notice.equals(NotificationType) ||
				 * X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(NotificationType)) { int
				 * AD_Message_ID = MESSAGE_REQUESTUPDATE; MNote note = new MNote(r.getCtx(),
				 * AD_Message_ID, AD_User_ID, X_R_Request.Table_ID, r.getR_Request_ID(),
				 * subject, message.toString(), r.get_TrxName()); note.saveEx(); }
				 */
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}	//	sendNotice
	
	/**
	 * 	Get mail trailer text
	 * 	@param serverAddress server address
	 *	@return Mail trailer text
	 */
	private String getMailTrailer(MRequest r, String serverAddress)
	{
		StringBuilder sb = new StringBuilder("\n").append(MRequest.SEPARATOR)
			.append(Msg.translate(r.getCtx(), "R_Request_ID"))
			.append(": ").append(r.getDocumentNo())
			.append("  ").append(r.getMailTag())
			.append("\n")
			.append(Msg.getMsg(r.getCtx(), "RequestSentBy"));
		if (serverAddress != null) {
			sb.append(" from ").append(serverAddress);
		}
		return sb.toString();
	}	//	getMailTrailer

	public static final String IGNORE_REQUEST_TYPES = "ignoreRequestTypes";
	private static ArrayList<String> ignoreRequestTypes = new ArrayList<String>();
	
	@SuppressWarnings("rawtypes")
	@Override
	public void updated(Dictionary properties) throws ConfigurationException {
		if (properties != null) {
			String p = (String) properties.get(IGNORE_REQUEST_TYPES);
			if (!Util.isEmpty(p)) {
				ignoreRequestTypes.clear();
				
				StringTokenizer st = new StringTokenizer(p, ";");
				while (st.hasMoreTokens()) {
					ignoreRequestTypes.add(st.nextToken().trim());
				}
			}
		}
	}

}
