package za.ntier.process;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.adempiere.base.annotation.Parameter;
import org.compiere.model.MCity;
import org.compiere.model.MRegion;
import org.compiere.model.Query;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;

public class ImportCityFromCSV extends SvrProcess {
	@Parameter(name="FileName")
	protected String pFileName;
    

    @Override
    protected String doIt() throws Exception {
        if (pFileName == null || pFileName.isEmpty()) {
            throw new AdempiereUserError("No file path provided");
        }

        List<String> lines = Files.readAllLines(Paths.get(pFileName));
        int count = 0;

        for (String line : lines) {
            if (line.trim().isEmpty() || line.toLowerCase().startsWith("city")) continue;

            String[] parts = line.split(",");
            if (parts.length < 2) continue;

            String cityName = parts[0].trim();
            String boxCode = parts.length > 1 ? parts[1].trim() : "";
            String strCode = parts.length > 2 ? parts[2].trim() : "";

            String postal = !strCode.isEmpty() ? strCode : boxCode;
            if (postal.isEmpty()) continue;

            int zip = Integer.parseInt(postal);
            String provinceName = getProvinceFromZip(zip);
            if (provinceName == null) continue;

            MRegion region = getRegionByName(provinceName);
            if (region == null) continue;

            // Check if city already exists
            MCity existingCity = new Query(getCtx(), MCity.Table_Name, "Name=? AND C_Region_ID=?", get_TrxName())
                    .setParameters(cityName, region.getC_Region_ID())
                    .first();

            if (existingCity == null) {
                MCity city = new MCity(getCtx(), 0, get_TrxName());
                city.setName(cityName);
                city.setC_Region_ID(region.getC_Region_ID());
                city.setPostal(postal);
                city.saveEx();
                count++;
            }
        }

        return "Cities created: " + count;
    }

    private MRegion getRegionByName(String name) {
        return new Query(getCtx(), MRegion.Table_Name, "Name=?", get_TrxName())
                .setParameters(name)
                .first();
    }

    private String getProvinceFromZip(int zip) {
        if (between(zip, 1, 299)) return "Gauteng";
        if (between(zip, 300, 499)) return "North West";
        if (between(zip, 500, 698)) return "Limpopo";
        if (between(zip, 699, 999)) return "Limpopo";
        if (between(zip, 1000, 1399)) return "Mpumalanga";
        if (between(zip, 1400, 1999)) return "Gauteng";
        if (between(zip, 2000, 2199)) return "Gauteng";
        if (between(zip, 2200, 2499)) return "Mpumalanga";
        if (between(zip, 2500, 2899)) return "North West";
        if (between(zip, 2900, 3999)) return "KwaZulu-Natal";
        if (between(zip, 4000, 4730)) return "KwaZulu-Natal";
        if (between(zip, 4731, 5999)) return "Eastern Cape";
        if (between(zip, 6000, 6499)) return "Eastern Cape";
        if (between(zip, 6500, 8099)) return "Western Cape";
        if (between(zip, 8100, 8999)) return "Northern Cape";
        if (between(zip, 9300, 9999)) return "Free State";
        return null;
    }


    private boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }

	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}
}
