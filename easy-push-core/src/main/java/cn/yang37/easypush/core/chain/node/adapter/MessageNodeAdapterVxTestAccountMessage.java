package cn.yang37.easypush.core.chain.node.adapter;

import cn.yang37.easypush.core.factory.ConfigFactory;
import cn.yang37.easypush.entity.config.VxTestAccountMessageConfigProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @class: DingNodeAdapter
 * @author: yang37z@qq.com
 * @date: 2024/5/21 4:43
 * @version: 1.0
 */
@Slf4j
public abstract class MessageNodeAdapterVxTestAccountMessage extends MessageNodeAdapter {

    protected VxTestAccountMessageConfigProperties sceneConfig() {
        return ConfigFactory.instance().getConfigProperties(VxTestAccountMessageConfigProperties.class, VxTestAccountMessageConfigProperties.PREFIX);
    }
}