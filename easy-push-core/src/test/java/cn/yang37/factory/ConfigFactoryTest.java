package cn.yang37.factory;

import cn.yang37.entity.config.DingTextConfigProperties;
import cn.yang37.entity.config.SmsTencentV3ConfigProperties;
import org.junit.jupiter.api.Test;

import java.util.Properties;
class ConfigFactoryTest {

    @Test
    void getProperties() {
        Properties properties = ConfigFactory.instance().getProperties(DingTextConfigProperties.PREFIX);
        Properties properties2 = ConfigFactory.instance().getProperties(SmsTencentV3ConfigProperties.PREFIX);
        System.out.println();
    }
}