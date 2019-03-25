package me.maxct.asset.util;

import java.security.SecureRandom;

/**
 * @author imaxct
 * 2019-03-25 18:28
 */
public class StringUtil {
    private static final long         TOP_LIMIT   = 0x19A100L;
    private static final long         LOWER_LIMIT = 0xB640L;
    private static final SecureRandom RANDOM      = new SecureRandom();

    public static String random4BitStr() {
        long number = Math.abs(RANDOM.nextLong()) % (TOP_LIMIT - LOWER_LIMIT) + LOWER_LIMIT;
        return Long.toUnsignedString(number, 36);
    }
}
