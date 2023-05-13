package cn.yang37.service.execute;


import cn.yang37.chain.example.MessageChain;
import cn.yang37.entity.MessageContext;
import org.apache.commons.configuration.ConfigurationException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description: 消息策略服务
 * @class: AbstractMessageService
 * @author: yang37z@qq.com
 * @date: 2023/1/12 15:11
 * @version: 1.0
 */
public abstract class AbstractMessageService {

    /**
     * 消息职责链
     */
    protected MessageChain messageChain;

    public MessageChain getMessageChain() {
        return messageChain;
    }

    public void setMessageChain(MessageChain messageChain) {
        this.messageChain = messageChain;
    }

    /**
     * 根据信息上下文对象发送信息
     *
     * @param messageContext .
     * @return .
     */
   public MessageContext singleSend(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, ConfigurationException{
       return getMessageChain().execute(messageContext);
   }

    /**
     * 根据信息上下文对象发送信息-批量
     *
     * @param messageContextList .34
     * @return .
     */
    public  List<MessageContext> strategyMultipleSend(List<MessageContext> messageContextList){
        return null;
    }

}

