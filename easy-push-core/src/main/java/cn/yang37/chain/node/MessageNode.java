package cn.yang37.chain.node;

import cn.yang37.entity.MessageContext;
import org.apache.commons.configuration.ConfigurationException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description:
 * @class: MessageNode
 * @author: yang37z@qq.com
 * @date: 2023/4/13 1:18
 * @version: 1.0
 */
public interface MessageNode {

    /**
     * 根据信息上下文对象发送信息
     *
     * @param messageContext .
     * @return .
     */
    MessageContext nodeSingleSend(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ConfigurationException;

    /**
     * 根据信息上下文对象发送信息-批量
     *
     * @param messageContextList .34
     * @return .
     */
    List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList);

}
