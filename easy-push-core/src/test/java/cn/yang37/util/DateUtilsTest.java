package cn.yang37.util;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void getDateTimeIso8601() {

        String dateTimeIso8601 = DateUtils.getDateTimeIso8601();
        System.out.println(dateTimeIso8601);
    }
}