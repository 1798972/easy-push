package cn.yang37.annotation;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;

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
public @interface MessageChainNodes {

    Class<? extends MessageNodeAdapter>[] value();

}
