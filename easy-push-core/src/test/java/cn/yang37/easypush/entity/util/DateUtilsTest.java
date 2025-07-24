package cn.yang37.easypush.entity.util;

import cn.yang37.easypush.core.util.DateUtils;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void getDateTimeIso8601() {

        String dateTimeIso8601 = DateUtils.getDateTimeIso8601();
        System.out.println(dateTimeIso8601);
    }
}