package cn.yang37.easypush.samples.javase;

import cn.yang37.easypush.core.service.impl.SendMessageServiceImpl;
import cn.yang37.easypush.model.MessageContext;
import cn.yang37.easypush.model.message.DingTextMessage;

public class JavaSeDemo {
    public static void main(String[] args) {
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(new DingTextMessage());
    }
}