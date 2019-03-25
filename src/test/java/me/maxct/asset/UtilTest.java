package me.maxct.asset;

import org.junit.Test;

import me.maxct.asset.util.StringUtil;

/**
 * @author imaxct
 * 2019-03-25 16:39
 */
public class UtilTest {
    @Test
    public void testRandom() {
        for (int i = 0; i < 10000; ++i) {
            String str = StringUtil.random4BitStr();
            if (str.length() < 4) {
                System.out.println(str);
            } else {
                System.out.println(str);
            }
        }
    }
}
