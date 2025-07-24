package cn.yang37.easypush.samples.javase;

import cn.yang37.easypush.app.impl.SendMessageServiceImpl;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.message.impl.DingTextMessage;

public class JavaSeDemo {
    public static void main(String[] args) {
        MessageContext messageContext = new SendMessageServiceImpl().singleSend(new DingTextMessage());
    }
}