package za.ntier.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.adempiere.base.annotation.Parameter;
import org.compiere.process.SvrProcess;

public class ImportEmployers extends SvrProcess {
	@Parameter(name="FileName")
	protected String pFileName;
	

	public ImportEmployers() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub

	}

	@Override
	protected String doIt() throws Exception {
		File file = new File("/mnt/data/test.csv");  // Adjust path as needed
	    return processCSV(file);
	}
	
	public String processCSV(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerParsed = false;

            // Column indexes
            int levyIndex = -1;
            int orgNameIndex = -1;
            int regNumberIndex = -1;
            int[] addressIndexes = new int[4];
            int postalCodeIndex = -1;
            int municipalityIndex = -1;
            int provinceIndex = -1;
            int employeesIndex = -1;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] columns = line.split(",", -1);

                if (!headerParsed) {
                    for (int i = 0; i < columns.length; i++) {
                        String col = columns[i].trim().toLowerCase();

                        if (col.contains("levy number")) levyIndex = i;
                        else if (col.equals("organization name")) orgNameIndex = i;
                        else if (col.equals("registration number")) regNumberIndex = i;
                        else if (col.equals("business address line 1")) addressIndexes[0] = i;
                        else if (col.equals("business address line 2")) addressIndexes[1] = i;
                        else if (col.equals("business address line 3")) addressIndexes[2] = i;
                        else if (col.equals("business address line 4")) addressIndexes[3] = i;
                        else if (col.equals("business postal code")) postalCodeIndex = i;
                        else if (col.contains("municipality")) municipalityIndex = i;
                        else if (col.equals("province")) provinceIndex = i;
                        else if (col.toLowerCase().startsWith("number of")) employeesIndex = i;
                    }

                    headerParsed = true;

                    // Validate column positions
                    if (levyIndex == -1 || orgNameIndex == -1 || regNumberIndex == -1 ||
                        Arrays.stream(addressIndexes).anyMatch(i -> i <= 0) ||
                        postalCodeIndex == -1 || municipalityIndex == -1 || provinceIndex == -1 ||
                        employeesIndex == -1) {

                        System.err.println("❌ One or more required columns not found.");
                        return "❌ One or more required columns not found.";
                    }

                    continue;
                }

                // Read values
                String levy = get(columns, levyIndex);
                String org = get(columns, orgNameIndex);
                String reg = get(columns, regNumberIndex);
                String address1 = get(columns, addressIndexes[0]);
                String address2 = get(columns, addressIndexes[1]);
                String address3 = get(columns, addressIndexes[2]);
                String address4 = get(columns, addressIndexes[3]);
                String postal = get(columns, postalCodeIndex);
                String muni = get(columns, municipalityIndex);
                String prov = get(columns, provinceIndex);
                String emp = get(columns, employeesIndex);

                // Print
                System.out.printf(
                        "Levy: %s | Org: %s | Reg: %s | Address1: %s | Address2: %s | Address3: %s | Address4: %s | Postal: %s | Muni: %s | Province: %s | Employees: %s%n",
                        levy, org, reg, address1, address2, address3, address4, postal, muni, prov, emp
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String get(String[] cols, int idx) {
        return idx < cols.length ? cols[idx].trim() : "";
    }

}
