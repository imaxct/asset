package me.maxct.asset;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Assert;
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

    @Test
    public void streamTest() {
        List<String> strings = Lists.list("aaa", "bbb", "aaa", "ccc");
        long cnt = strings.stream().filter(s -> s.equals("aaa")).count();
        Assert.assertEquals(cnt, 2);
    }
}
