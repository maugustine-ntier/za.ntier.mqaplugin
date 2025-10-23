package za.ntier.utils;

public final class YearUtil {
    private YearUtil() {}
    public static Integer parseYear(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.length() != 4) return null;
        for (int i=0;i<4;i++) if (!Character.isDigit(t.charAt(i))) return null;
        return Integer.valueOf(t);
    }
    public static boolean isLegacy(int year) { return year <= 2024; }
}
