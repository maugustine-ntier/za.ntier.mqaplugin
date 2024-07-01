package za.ntier.process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

@org.adempiere.base.annotation.Process
public class ZZ_Convert_Jasper_File_To_JRXML extends SvrProcess {


	private static final String RESOURCE_DIR_PARAM = "$P{RESOURCE_DIR}";
	private static final String RESOURCE_DIR_PARAM_REPLACE = "\\$P\\{RESOURCE_DIR\\}";
	private static final String SUBREPORT_DIR_PARAM = "$P{SUBREPORT_DIR}";
	private static final String SUBREPORT_DIR_PARAM_REPLACE = "\\$P\\{SUBREPORT_DIR\\}";
	private static final String BASE_PATH_PARAM = "$P{BASE_PATH}";
	private static final String BASE_PATH_PARAM_REPLACE = "\\$P\\{BASE_PATH\\}";
	String inputJasperFilePath = "/tmp/jasper";
	String dir = "/home/martin/ACATS/AD_Process_1000128";
	
	int cnt = 0;

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		createDirectoryIfNotExists("/tmp/jrxml");
		createDirectoryIfNotExists("/tmp/jasper");

		
		Path path = Paths.get(dir);
		List<Path> paths = listFiles(path);
		paths.forEach(x -> {
			try {
				processFile(x);
			} catch (AdempiereException | JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return cnt + " files converted " + cnt;




	}


	private void processFile(Path pathToFile) throws AdempiereException, JRException {
		File file = pathToFile.toFile();
		if (!file.getName().endsWith(".jasper")) {
			return;
		}
		String outputPath = "";
		if (file.exists()) {
			cnt++;
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getAbsolutePath());
			String entryNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
			outputPath = dir + "/" + entryNameWithoutExtension + ".jrxml";
			JRXmlWriter.writeReport(jasperReport, outputPath, "UTF-8");
		} else {
			log.severe("file not found: " + file.getAbsolutePath());
			return;
		}
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







}
