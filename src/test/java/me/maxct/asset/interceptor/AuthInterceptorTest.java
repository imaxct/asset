package me.maxct.asset.interceptor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.PatternMatchUtils;

/**
 * @author imaxct
 * 2019-04-06 23:41
 */
public class AuthInterceptorTest {
    @Test
    public void testUrlMatch() {
        String pattern = "*";
        Assert.assertTrue(PatternMatchUtils.simpleMatch(pattern, "/User/add?id=1"));
    }
}
