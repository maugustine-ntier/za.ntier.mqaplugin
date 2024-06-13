package za.ntier.process;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MImage;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/*Scanning of Driver licenses and ID/passports.

Jane and team have volunteered to get the files from Justin and scan this information.
We need a scheme to name these files so that nTier can bulk upload these files.

The file naming scheme we came up with is as follows:
Driver's licenses:
L_<ID/Passport no>_<license expiry date>
Drivers ID/Passport:
ID_<ID/Passport no>_<1st Name>_<2nd Name>

Each transporter should have their own folder to keep these images.
When Jane's team starts on this task, they would need to do a couple of scans and send to Yogan for verification
and a go-ahead.
*/
@org.adempiere.base.annotation.Process
public class LoadDriverLicenseAndIdImages extends SvrProcess {


	String p_path = null;


	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("FileName"))
				p_path = para[i].getParameterAsString();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		} 
	}


	@Override
	protected String doIt() throws Exception {
		int cnt = 0;
		Path path = Paths.get("C:\\test\\");
		List<Path> paths = listFiles(path);
		paths.forEach(x -> processFile(x));

		return cnt + " Images loaded";

	}

	// list all files from this path
	public static List<Path> listFiles(Path path) throws IOException {

		List<Path> result;
		try (Stream<Path> walk = Files.walk(path)) {
			result = walk.filter(Files::isRegularFile)
					.collect(Collectors.toList());
		}
		return result;

	}

	private void processFile(Path pathToFile) throws AdempiereException {
		File file = pathToFile.toFile();
		if (file.exists()) {
			// read files into byte[]
			final byte[] dataEntry = new byte[(int) file.length()];
			try {
				final FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(dataEntry);
				fileInputStream.close();
				MImage mImage = new MImage(getCtx(), 0, get_TrxName());
				mImage.setBinaryData(dataEntry);
				mImage.saveEx();
			} catch (FileNotFoundException e) {
				log.severe("File Not Found.");
				e.printStackTrace();
			} catch (IOException e1) {
				log.severe("Error Reading The File.");
				e1.printStackTrace();
			}
		} else {
			log.severe("file not found: " + file.getAbsolutePath());
			return;
		}
	}

	
	private void createDriverWithImage (MImage mImage, boolean isLicense) {
		
	}






}
