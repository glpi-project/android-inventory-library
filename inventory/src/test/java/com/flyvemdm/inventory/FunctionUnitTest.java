package com.flyvemdm.inventory;

import com.flyvemdm.inventory.categories.StringUtils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FunctionUnitTest {

    @Test
    public void createIP_correct() throws Exception {
        int ip = 19216801;
        String formatIp = StringUtils.intToIp(ip);
        assertTrue(formatIp.split("\\.").length==4);
    }

    @Test
    public void intToByte_correct() throws Exception {
        int byteNumber = 19216801;

        try {
            byte[] byteFormat = StringUtils.intToByte(byteNumber);
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

    @Test
    public void join_correct() throws Exception {
        ArrayList<String> arr = new ArrayList<String>();
        for (int i =2; i <= 12; i++) {
            arr.add(String.valueOf(i*12345));
        }

        assertTrue(StringUtils.join(arr, "#").split("#").length==11);
    }
}