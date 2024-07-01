package za.ntier.process;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.utils.DigestOfFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

@org.adempiere.base.annotation.Process
public class ZZ_Fix_Jasper_With_Incorrect_SubReports extends SvrProcess {


	private static final String RESOURCE_DIR_PARAM = "$P{RESOURCE_DIR}";
	private static final String RESOURCE_DIR_PARAM_REPLACE = "\\$P\\{RESOURCE_DIR\\}";
	private static final String SUBREPORT_DIR_PARAM = "$P{SUBREPORT_DIR}";
	private static final String SUBREPORT_DIR_PARAM_REPLACE = "\\$P\\{SUBREPORT_DIR\\}";
	private static final String BASE_PATH_PARAM = "$P{BASE_PATH}";
	private static final String BASE_PATH_PARAM_REPLACE = "\\$P\\{BASE_PATH\\}";
	String inputJasperFilePath = "/tmp/jasper";

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		createDirectoryIfNotExists("/tmp/jrxml");
		createDirectoryIfNotExists("/tmp/jasper");
		int cnt = 0;
		String sql = "select aa.ad_attachment_id "
				+ "from ad_process ap "
				+ "join ad_attachment aa  on ap.ad_process_id = aa.record_id "
				+ "where ap.jasperreport is not null "
				+ "and aa.ad_table_id  = (select ad_table_id from ad_table t where t.tablename  = 'AD_Process') "
				+ "and ap.ad_process_id = 1000128 "
				+ ""
			//	+ " and ap.created >= current_date "
				+ "group by aa.ad_attachment_id " ;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				boolean add_logo_image = false;
				int ad_attachment_ID = rs.getInt(1);
				MAttachment mAttachment = new MAttachment(Env.getCtx(), ad_attachment_ID, null);
				MAttachmentEntry[] mAttachmentEntries = mAttachment.getEntries();
				for (MAttachmentEntry entry : mAttachmentEntries) {
					if (!(entry.getName().endsWith(".jrxml") || (entry.getName().endsWith(".jasper")))) {
						continue;
					}
					String outputPath = "";
					try {
						boolean isJRXML = false;
						if (entry.getName().endsWith(".jrxml")) {
							isJRXML = true;
						}
						inputJasperFilePath = getAttachmentEntryFile(entry,isJRXML).getAbsolutePath();
						// if endwith .jrxml , no need to decompile,  already copied
						if (!(inputJasperFilePath.endsWith(".jrxml"))) {
							JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(inputJasperFilePath);
							String entryNameWithoutExtension = entry.getName().replaceFirst("[.][^.]+$", "");
							outputPath = "/tmp/jrxml/" + entryNameWithoutExtension + ".jrxml";
							JRXmlWriter.writeReport(jasperReport, outputPath, "UTF-8");
						} else {
							outputPath = inputJasperFilePath;  // for attached .jrxml files
						}
					} catch (JRException e) {
						e.printStackTrace();
					}
					addOrReplaceParameterInJrxml(outputPath,"SUBREPORT_DIR"); 
					addOrReplaceParameterInJrxml(outputPath,"RESOURCE_DIR"); 
					// Prefix subreport names with $P{SUBREPORT_DIR}
					// This step will depend on the exact format of your jrxml files, but here's a general example
					modifySubreportExpressionInJrxml(outputPath,"subreportExpression",SUBREPORT_DIR_PARAM,SUBREPORT_DIR_PARAM_REPLACE);
					modifySubreportExpressionInJrxml(outputPath,"imageExpression",RESOURCE_DIR_PARAM,RESOURCE_DIR_PARAM_REPLACE);

					// Compile back to .jasper
					try {
						if (entry.getName().equals("Header Subreport.jasper")) {
							String outputPath2 = "/home/martin/Sources/idempiere/Custom.Jasper.Reports/src/Dazzle Reports/" + entry.getName();		
							mAttachment.addEntry(new File(outputPath2));
							if (mAttachment != null)
								mAttachment.saveEx();
							add_logo_image = true;
						} else {
					    	String outputPath2 = "/tmp/jasper/" + entry.getName();
							JasperCompileManager.compileReportToFile(outputPath, outputPath2);
							mAttachment.addEntry(new File(outputPath2));
							if (mAttachment != null)
								mAttachment.saveEx();
						}
					} catch (Exception e) {
						System.out.println("Problem in Report : " + entry.getName());
						e.printStackTrace();
					}
				}
				if (add_logo_image) {
					String logoPath = "/home/martin/Sources/idempiere/Custom.Jasper.Reports/src/Dazzle Reports/Acadia.jpg";
					mAttachment.addEntry(new File(logoPath));
					if (mAttachment != null)
						mAttachment.saveEx();
				}
				
				cnt++;

			}
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, sql, ex);
		}
		finally
		{
			DB.close(rs,pstmt);
			rs = null;pstmt = null;
		}
		return "Reports Fixed : " + cnt;
	}

	private File getAttachmentEntryFile(MAttachmentEntry entry,boolean isJRXML) {
		String localFile = "/tmp/" + entry.getName();
		String downloadedLocalFile = "/tmp/" + "TMP_" + entry.getName();
		if (isJRXML) {
			localFile = "/tmp/jrxml/" + entry.getName();
			downloadedLocalFile = "/tmp/jrxml/" + "TMP_" + entry.getName();
		}
		File reportFile = new File(localFile);
		if (reportFile.exists()) {
			String localMD5hash = DigestOfFile.getMD5Hash(reportFile);
			String entryMD5hash = DigestOfFile.getMD5Hash(entry.getData());
			if (localMD5hash.equals(entryMD5hash)) {
				log.info(" no need to download: local report is up-to-date");
			} else {
				log.info(" report on server is different from local copy, download and replace");
				File downloadedFile = new File(downloadedLocalFile);
				entry.getFile(downloadedFile);
				if (!reportFile.delete()) {
					throw new AdempiereException("Cannot delete temporary file " + reportFile.toString());
				}
				if (!downloadedFile.renameTo(reportFile)) {
					throw new AdempiereException("Cannot rename temporary file " + downloadedFile.toString() + " to "
							+ reportFile.toString());
				}
			}
		} else {
			entry.getFile(reportFile);
		}
		return reportFile;
	}


	public void addOrReplaceParameterInJrxml(String filePath,String parm) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(filePath);
			if (document == null) {
				document = documentBuilder.parse(filePath);
			}

			Node jasperReportNode = document.getDocumentElement();

			// Check if the SUBREPORT_DIR parameter already exists
			Node existingParameter = null;
			Node lastParameter = null;
			Node lastParameterQuery = null;
			NodeList parameters = jasperReportNode.getChildNodes();
			for (int i = 0; i < parameters.getLength(); i++) {
				Node parameter = parameters.item(i);
				if (parameter.getNodeType() == Node.ELEMENT_NODE && parameter.getNodeName().equals("parameter")) {
					if (((Element) parameter).getAttribute("name").equals(parm)) {
						existingParameter = parameter;
					} else {
						lastParameter = parameter;
					}
				}
				if (parameter.getNodeType() == Node.ELEMENT_NODE && parameter.getNodeName().equals("queryString")) {
					lastParameterQuery = parameter;
				}
			}

			// If the SUBREPORT_DIR parameter exists, remove it
			if (existingParameter != null) {
				jasperReportNode.removeChild(existingParameter);
			}

			// Create a new SUBREPORT_DIR parameter
			Element parameter = document.createElement("parameter");
			parameter.setAttribute("name", parm);
			parameter.setAttribute("class", "java.lang.String");
			parameter.setAttribute("isForPrompting", "false");

			// Create text nodes for new lines and indentation
			parameter.appendChild(document.createTextNode("\n\t\t"));

			Element defaultValueExpression = document.createElement("defaultValueExpression");
			defaultValueExpression.appendChild(document.createTextNode("\"\""));
			parameter.appendChild(defaultValueExpression);

			// Create a text node for a new line
			parameter.appendChild(document.createTextNode("\n\t"));



			// Add the parameter to the document
//			if (lastParameter != null && lastParameter.getNextSibling() != null) {
//				jasperReportNode.insertBefore(document.createTextNode("\n\t"), lastParameter.getNextSibling());
//				jasperReportNode.insertBefore(parameter, lastParameter.getNextSibling().getNextSibling());
//				
//			} else 
			if (lastParameterQuery != null && lastParameterQuery.getNextSibling() != null){
				Node next = jasperReportNode.insertBefore(document.createTextNode("\n\t"), lastParameterQuery);
				jasperReportNode.insertBefore(parameter, next.getNextSibling());			
			} else {
				jasperReportNode.appendChild(document.createTextNode("\n\t")); 
				jasperReportNode.appendChild(parameter);
			}
			removeLanguage(document);  // Old Reports had language = GROOVY
			// Write the updated document back to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	public void modifySubreportExpressionInJrxml(String filePath, String expression, String parm,String parm_replace) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(filePath);

			// Iterate over all <subreportExpression> elements
			NodeList subreportExpressions = document.getElementsByTagName(expression);
			for (int i = 0; i < subreportExpressions.getLength(); i++) {
				Node subreportExpression = subreportExpressions.item(i);
				if (subreportExpression.getNodeType() == Node.ELEMENT_NODE) {
					String content = subreportExpression.getTextContent().trim();
					boolean replace = false;
					if (content.startsWith(parm)) {
						// If the content already starts with SUBREPORT_DIR_PARAM, replace it
						content = content.replaceFirst(parm_replace, "");
						replace = true;
					}
					if (content.startsWith(BASE_PATH_PARAM)) {
						// If the content starts with BASE_PATH_PARAM, remove it
						content = content.replaceFirst(BASE_PATH_PARAM_REPLACE, "");
						replace = true;
					}
					content = content.replace("/tmp/", "");
					// Prepend SUBREPORT_DIR_PARAM
					content = (replace) ? parm + content : parm + " + " + content;
					subreportExpression.setTextContent(content);
				}
			}

			// Write the updated document back to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void createDirectoryIfNotExists(String directoryPath) {
		Path path = Paths.get(directoryPath);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				System.out.println("Directory " + directoryPath + " created successfully");
			} catch (Exception e) {
				System.out.println("Failed to create directory " + directoryPath);
				e.printStackTrace();
			}
		} else {
			System.out.println("Directory " + directoryPath + " already exists");
		}
	}
	
	public static void removeLanguage(Document document) {
	    // Find jasperReport element
	    Element jasperReportNode = (Element) document.getElementsByTagName("jasperReport").item(0);

	    if (jasperReportNode != null) {
	        // Remove language attribute
	        jasperReportNode.removeAttribute("language");
	    }
	}





}
