package za.ntier.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.adempiere.base.annotation.Parameter;
import org.compiere.model.MCity;
import org.compiere.model.MCountry;
import org.compiere.model.MRegion;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.CLogger;

/**
 * Import South African Cities from CSV
 * CSV columns:
 *   1) SUBURB (city name, often in double-quotes)
 *   2) Box-code
 *   3) Str-code
 *
 * Logic:
 * - postal = Str-code if not blank else Box-code
 * - province determined by postal range -> find C_Region by exact Name in ZA
 * - create C_City(Name, C_Region_ID, Postal) if not exists (case-insensitive by Name within Region)
 * - if exists and Postal empty, update Postal
 */
@org.adempiere.base.annotation.Process(name="za.ntier.process.ImportCityFromCSV")
public class ImportCityFromCSV extends SvrProcess {
	@Parameter(name="FileName")
	private String filePath;
	
    private static final CLogger log = CLogger.getCLogger(ImportCityFromCSV.class);

    private Integer zaCountryId; // cache South Africa country id
    private final Map<String, Integer> regionCache = new HashMap<>(); // Name -> C_Region_ID (ZA only)

    @Override
    protected void prepare() {
        for (ProcessInfoParameter p : getParameter()) {
            if ("FilePath".equalsIgnoreCase(p.getParameterName())) {
                filePath = p.getParameterAsString();
            }
        }
    }

    @Override
    protected String doIt() throws Exception {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new AdempiereUserError("No file path provided (FilePath parameter).");
        }

        zaCountryId = getSouthAfricaCountryId();
        if (zaCountryId == null) {
            throw new AdempiereUserError("Could not find South Africa in C_Country (CountryCode='ZA' or Name='South Africa').");
        }

        int created = 0;
        int updated = 0;
        int skippedHeader = 0;
        int skippedNoPostal = 0;
        int skippedNoRegion = 0;
        int total = 0;

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new AdempiereUserError("File not found: " + filePath);
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                total++;

                // Normalize BOM on first line
                if (firstLine) {
                    line = removeUTF8BOM(line);
                    firstLine = false;
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                // Parse CSV line safely (supports quoted fields and commas inside quotes)
                List<String> cols = parseCsvLine(line);
                if (cols.isEmpty()) {
                    continue;
                }

                // Expect at least first two columns (SUBURB, Box-code). Third (Str-code) is optional.
                String firstColRaw = cols.get(0);
                String firstCol = stripQuotes(firstColRaw).trim();

                // Skip header if first column is SUBURB (case-insensitive), even if quoted
                if ("SUBURB".equalsIgnoreCase(firstCol)) {
                    skippedHeader++;
                    continue;
                }

                String cityName = stripQuotes(firstColRaw).trim();
                String boxCode = cols.size() > 1 ? stripQuotes(cols.get(1)).trim() : "";
                String strCode = cols.size() > 2 ? stripQuotes(cols.get(2)).trim() : "";

                // Choose postal
                String postal = !isBlank(strCode) ? strCode : boxCode;
                if (isBlank(postal)) {
                    skippedNoPostal++;
                    continue;
                }

                Integer zip = safeParseInt(postal);
                if (zip == null) {
                    // Non-numeric postal; skip
                    skippedNoPostal++;
                    continue;
                }

                String provinceName = getProvinceFromZip(zip);
                if (provinceName == null) {
                    skippedNoRegion++;
                    continue;
                }

                Integer regionId = getRegionIdByNameZA(provinceName);
                if (regionId == null) {
                    skippedNoRegion++;
                    continue;
                }

                // Find existing city by Name (case-insensitive) within region
                MCity existing = new Query(getCtx(), MCity.Table_Name,
                        "C_Region_ID=? AND UPPER(Name)=?",
                        get_TrxName())
                        .setParameters(regionId, cityName.toUpperCase(Locale.ROOT))
                        .first();

                if (existing == null) {
                    MCity city = new MCity(getCtx(), 0, get_TrxName());
                    city.setC_Region_ID(regionId);
                    city.setName(cityName);
                    city.setPostal(postal);
                    city.saveEx();
                    created++;
                } else {
                    // Update Postal if empty and we have one
                    String existingPostal = existing.getPostal();
                    if (isBlank(existingPostal) && !isBlank(postal)) {
                        existing.setPostal(postal);
                        existing.saveEx();
                        updated++;
                    }
                }
            }
        } catch (IOException e) {
            throw new AdempiereUserError("Error reading file: " + e.getMessage());
        }

        String msg = "Processed lines=" + total
                + " | created=" + created
                + " | updated=" + updated
                + " | skippedHeader=" + skippedHeader
                + " | skippedNoPostal=" + skippedNoPostal
                + " | skippedNoRegion=" + skippedNoRegion;

        addLog(msg);
        return msg;
    }

    // ---------- Helpers ----------

    private static String removeUTF8BOM(String s) {
        if (s != null && s.startsWith("\uFEFF")) {
            return s.substring(1);
        }
        return s;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static String stripQuotes(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.length() >= 2 && t.startsWith("\"") && t.endsWith("\"")) {
            return t.substring(1, t.length() - 1);
        }
        return t;
    }

    private static Integer safeParseInt(String s) {
        try {
            return Integer.valueOf(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Minimal CSV parser for a single line.
     * Handles:
     * - Fields enclosed in double quotes
     * - Commas inside quoted fields
     * - Escaped double quotes ("") inside quoted fields
     */
    private static List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        if (line == null) return result;

        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                if (inQuotes) {
                    // Lookahead for escaped quote
                    if (i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                        sb.append('\"');
                        i++; // skip the escaped quote
                    } else {
                        inQuotes = false; // closing quote
                    }
                } else {
                    inQuotes = true; // opening quote
                }
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString()); // last field
        return result;
    }

    private Integer getSouthAfricaCountryId() {
        // First try by ISO code
        MCountry country = new Query(getCtx(), MCountry.Table_Name, "CountryCode=?", get_TrxName())
                .setParameters("ZA")
                .first();
        if (country != null) return country.getC_Country_ID();

        // Fallback by Name
        country = new Query(getCtx(), MCountry.Table_Name, "Name=?", get_TrxName())
                .setParameters("South Africa")
                .first();
        return country != null ? country.getC_Country_ID() : null;
    }

    private Integer getRegionIdByNameZA(String name) {
        if (regionCache.containsKey(name)) {
            return regionCache.get(name);
        }
        MRegion region = new Query(getCtx(), MRegion.Table_Name,
                "C_Country_ID=? AND Name=?",
                get_TrxName())
                .setParameters(zaCountryId, name)
                .first();
        Integer id = region != null ? region.getC_Region_ID() : null;
        regionCache.put(name, id);
        return id;
    }

    /**
     * Province mapping using EXACT C_Region.Name values:
     *   Eastern Cape, Free State, Gauteng, KwaZulu-Natal, Limpopo,
     *   Mpumalanga, Northern Cape, North West, Western Cape
     *
     * Uses Str-code/Box-code numeric ranges as provided.
     */
    private String getProvinceFromZip(int zip) {
        // Note: 9000–9299 was Namibia (not SA), intentionally not mapped.
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

    private static boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
