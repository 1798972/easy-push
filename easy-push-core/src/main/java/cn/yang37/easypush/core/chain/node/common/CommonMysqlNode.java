package cn.yang37.easypush.core.chain.node.common;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.easypush.entity.context.MessageContext;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description: mysql持久化
 * @class: DefaultMysqlNode
 * @author: yang37z@qq.com
 * @date: 2023/4/15 14:42
 * @version: 1.0
 */
public class CommonMysqlNode extends MessageNodeAdapter {
    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}