package cn.yang37.chain.node.def;

import cn.yang37.chain.node.adapter.MessageNodeAdapter;
import cn.yang37.entity.context.MessageContext;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description: 缓存
 * @class: DefaultRedisNode
 * @author: yang37z@qq.com
 * @date: 2023/4/15 14:43
 * @version: 1.0
 */
public class DefaultRedisNode extends MessageNodeAdapter {
    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }
}