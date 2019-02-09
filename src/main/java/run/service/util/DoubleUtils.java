package run.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DoubleUtils {

    private DoubleUtils() {}

    public static Double roundDouble(Double d, int scale) {
        return new BigDecimal(d).setScale(scale, RoundingMode.UP).doubleValue();
    }
}
