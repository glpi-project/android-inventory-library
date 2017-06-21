package com.flyvemdm.inventory;

import com.flyvemdm.inventory.categories.StringUtils;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class FunctionUnitTest {

    @Test
    public void createIP_correct() throws Exception {
        int ip = 19216801;
        String formatIp = StringUtils.intToIp(ip);
        assertTrue(formatIp.split("\\.").length==4);
    }
}