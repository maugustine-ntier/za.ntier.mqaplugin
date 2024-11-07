/******************************************************************************
 * Copyright (C) 2013 Elaine Tan                                              *
 * Copyright (C) 2013 Trek Global
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.base.event;

import java.io.File;
import java.util.List;

import org.compiere.model.MClient;
import org.compiere.model.MUser;

/**
 * Event data for {@link IEventTopics#REQUEST_SEND_EMAIL} event topic.
 * @author Elaine
 */
public class RequestSendEMailEventDataNtier 
{ 
	private MClient client;
	private MUser from;
	private MUser to;
	private String subject;
	private String message;
	private List<File> attachments;
	private int requestID;
	private boolean isHtml;
	private String trxName;
	private String priority;
	private String dateLastAction;
	private String updated;
	private String summary;
	private String documentNo;
	private String emailMessage;
	
	
	/**
	 * @param client
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 * @param attachment
	 * @param requestID
	 */
	public RequestSendEMailEventDataNtier(MClient client, MUser from, MUser to, String subject, String message, List<File> attachments, int requestID, String emailMessage) {
		this(client, from, to, subject, message, attachments, requestID, false,emailMessage);
	}
	
	/**
	 * @param client
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 * @param attachment
	 * @param requestID
	 * @param isHtml
	 */
	public RequestSendEMailEventDataNtier(MClient client, MUser from, MUser to, String subject, String message, List<File> attachments, int requestID, boolean isHtml,String emailMessage) {
		setClient(client);
		setFrom(from);
		setTo(to);
		setSubject(subject);
		setMessage(message);
		setAttachments(attachments);
		setRequestID(requestID);
		setHtml(isHtml);
		setEmailMessage(emailMessage);
	}
	/**
	 * @param client
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 * @param attachment
	 * @param requestID
	 */
	public RequestSendEMailEventDataNtier(MClient client, MUser from, MUser to, String subject, String message, List<File> attachments, int requestID,String trxName,
			String priority, String updated, String dateLastAction,String summary,String documentNo,String emailMessage)  {
		this(client, from, to, subject, message, attachments, requestID, false,trxName,priority, updated,dateLastAction,   summary,documentNo,emailMessage);
	}
	
	/**
	 * @param client
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 * @param attachment
	 * @param requestID
	 * @param isHtml
	 */
	public RequestSendEMailEventDataNtier(MClient client, MUser from, MUser to, String subject, String message, List<File> attachments, int requestID, boolean isHtml,String trxName,
			String priority, String updated, String dateLastAction,String summary,String documentNo,String emailMessage) {
		setClient(client);
		setFrom(from);
		setTo(to);
		setSubject(subject);
		setMessage(message);
		setAttachments(attachments);
		setRequestID(requestID);
		setHtml(isHtml);
		setTrxName(trxName);
		setPriority(priority);
		setDateLastAction(dateLastAction);
		setUpdated(updated);
		setSummary(summary);
		setDocumentNo(documentNo);
		setEmailMessage(emailMessage);
	}

	/**
	 * @return MClient
	 */
	public MClient getClient() {
		return client;
	}

	/**
	 * @param client
	 */
	public void setClient(MClient client) {
		this.client = client;
	}

	/**
	 * @return from user
	 */
	public MUser getFrom() {
		return from;
	}

	/**
	 * @param from from user
	 */
	public void setFrom(MUser from) {
		this.from = from;
	}

	/**
	 * @return to user
	 */
	public MUser getTo() {
		return to;
	}

	/**
	 * @param to to user
	 */
	public void setTo(MUser to) {
		this.to = to;
	}

	/**
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return attachment file
	 */
	public List<File> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachment
	 */
	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return R_Request_ID
	 */
	public int getRequestID() {
		return requestID;
	}

	/**
	 * @param requestID R_Request_ID
	 */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	/**
	 * @return true if message is html text
	 */
	public boolean isHtml() {
		return isHtml;
	}

	/**
	 * @param isHtml
	 */
	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	public String getTrxName() {
		return trxName;
	}

	public void setTrxName(String trxName) {
		this.trxName = trxName;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getDateLastAction() {
		return dateLastAction;
	}

	public void setDateLastAction(String dateLastAction) {
		this.dateLastAction = dateLastAction;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}
}
