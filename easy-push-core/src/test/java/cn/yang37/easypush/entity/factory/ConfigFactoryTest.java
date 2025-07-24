package cn.yang37.easypush.entity.factory;

import cn.yang37.easypush.core.factory.ConfigFactory;
import cn.yang37.easypush.entity.config.DingTextConfigProperties;
import cn.yang37.easypush.entity.config.SmsTencentV3ConfigProperties;
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