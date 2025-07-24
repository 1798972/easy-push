package cn.yang37.easypush.core.chain.node.adapter;

import cn.yang37.easypush.core.factory.ConfigFactory;
import cn.yang37.easypush.entity.config.SmsTencentV3ConfigProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @class: DingNodeAdapter
 * @author: yang37z@qq.com
 * @date: 2024/5/21 4:43
 * @version: 1.0
 */
@Slf4j
public abstract class MessageNodeAdapterSmsTencentV3 extends MessageNodeAdapter{

    protected SmsTencentV3ConfigProperties sceneConfig() {
        return ConfigFactory.instance().getConfigProperties(SmsTencentV3ConfigProperties.class, SmsTencentV3ConfigProperties.PREFIX);
    }

}