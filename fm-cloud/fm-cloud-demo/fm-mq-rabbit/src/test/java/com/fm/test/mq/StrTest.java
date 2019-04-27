package com.fm.test.mq;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class StrTest {


    @Test
    public void test() throws UnsupportedEncodingException {
        String str = "0123456789ABCDEF";
        char[] hexs = {0x5D, 0xC1, 0x03, 0x59, 0x5C, 0x1B, 0x03, 0x60, 0xAD, 0x95, 0xE1, 0xAD, 0xD9, 0x86, 0xB9, 0x91};
        byte[] bytes = new byte[hexs.length / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        String s = new String(bytes, "GBK");
        System.out.println(s);

    }
}
