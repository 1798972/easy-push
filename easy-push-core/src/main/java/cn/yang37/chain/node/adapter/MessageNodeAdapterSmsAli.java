package cn.yang37.chain.node.adapter;

import cn.yang37.entity.config.SmsAliConfigProperties;
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
public abstract class MessageNodeAdapterSmsAli extends MessageNodeAdapter {
    protected SmsAliConfigProperties configProperties =
            ConfigFactory.instance().getConfigProperties(SmsAliConfigProperties.class, SmsAliConfigProperties.PREFIX);
}