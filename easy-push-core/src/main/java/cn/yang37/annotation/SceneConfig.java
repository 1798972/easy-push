package cn.yang37.annotation;

import cn.yang37.entity.config.SceneConfigProperties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @class: ChannelChain
 * @author: yang37z@qq.com
 * @date: 2025/7/19 2:51
 * @version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SceneConfig {
    /**
     * 配置类
     */
    Class<? extends SceneConfigProperties> value();

    /**
     * 新增前缀
     */
    String prefix();
}
