package cn.yang37.chain.node.adapter;

import cn.yang37.entity.config.SmsTencentV3ConfigProperties;
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
public abstract class MessageNodeAdapterSmsTencentV3 extends MessageNodeAdapter {
    protected SmsTencentV3ConfigProperties configProperties =
            ConfigFactory.instance().getConfigProperties(SmsTencentV3ConfigProperties.class, SmsTencentV3ConfigProperties.PREFIX);
}