package cn.yang37.easypush.entity.util;

import cn.yang37.easypush.core.util.HashUtils;
import org.junit.jupiter.api.Test;

class HashUtilsTest {

    @Test
    void sha256HexLower() throws Exception {
        String s = HashUtils.sha256HexLower("");
        System.out.println(s);
    }
}