package cn.yang37.util;

import org.junit.jupiter.api.Test;

class HashUtilsTest {

    @Test
    void sha256HexLower() throws Exception {
        String s = HashUtils.sha256HexLower("");
        System.out.println(s);
    }
}