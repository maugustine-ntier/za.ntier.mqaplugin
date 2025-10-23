package za.ntier.utils;

import java.math.BigDecimal;
import java.util.*;

public final class MoneyMath {
    private MoneyMath() {}

    public static BigDecimal nz(BigDecimal b) { return b == null ? BigDecimal.ZERO : b; }

    @SuppressWarnings("unchecked")
    public static Map<Integer, BigDecimal> sumPerYear(
        Map<Integer, ? extends Iterable<?>> rowsByYear,
        java.util.function.Function<Object, BigDecimal> getter
    ) {
        Map<Integer, BigDecimal> out = new LinkedHashMap<>();
        for (Map.Entry<Integer, ? extends Iterable<?>> e : rowsByYear.entrySet()) {
            BigDecimal sum = BigDecimal.ZERO;
            for (Object o : e.getValue()) {
                sum = sum.add(nz(getter.apply(o)));
            }
            if (sum.compareTo(BigDecimal.ZERO) != 0) out.put(e.getKey(), sum);
        }
        return out;
    }

    public static BigDecimal sumAll(Collection<BigDecimal> values) {
        BigDecimal s = BigDecimal.ZERO;
        for (BigDecimal v : values) s = s.add(nz(v));
        return s;
    }

    public static Map<Integer, BigDecimal> negateMap(Map<Integer, BigDecimal> in) {
        Map<Integer, BigDecimal> out = new LinkedHashMap<>();
        for (Map.Entry<Integer, BigDecimal> e : in.entrySet()) out.put(e.getKey(), e.getValue().negate());
        return out;
    }

    public static boolean isZero(BigDecimal v) { return v == null || v.compareTo(BigDecimal.ZERO) == 0; }
}
