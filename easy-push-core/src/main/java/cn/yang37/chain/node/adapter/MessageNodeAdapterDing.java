package cn.yang37.chain.node.adapter;

import cn.yang37.entity.config.DingTextConfigProperties;
import cn.yang37.factory.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @class: DingNodeAdapter
 * @author: yang37z@qq.com
 * @date: 2024/5/21 4:43
 * @version: 1.0
 */
@Slf4j
public abstract class MessageNodeAdapterDing extends MessageNodeAdapter {

    protected DingTextConfigProperties sceneConfig() {
        return ConfigFactory.instance().getConfigProperties(DingTextConfigProperties.class, DingTextConfigProperties.PREFIX);
    }
}