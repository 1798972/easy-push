package cn.yang37.easypush.core.chain.node.ding;

import cn.yang37.easypush.core.chain.node.adapter.MessageNodeAdapterDing;
import cn.yang37.easypush.core.util.DateUtils;
import cn.yang37.easypush.entity.constant.DingConstant;
import cn.yang37.easypush.entity.context.MessageContext;
import cn.yang37.easypush.entity.context.ThreadContext;
import cn.yang37.easypush.entity.dto.ding.DingTextRequestDTO;
import cn.yang37.easypush.entity.message.impl.DingTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @description: 构建请求钉钉的报文数据
 * @class: DingContentNode
 * @author: yang37z@qq.com
 * @date: 2023/1/30 10:06
 * @version: 1.0
 */
public class DingTextContentNode extends MessageNodeAdapterDing {

    private static final Logger log = LoggerFactory.getLogger(DingTextContentNode.class);

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) {
        ThreadContext.putContext(DingConstant.DING_TEXT_CONTENT, formatUserContent(messageContext));
        ThreadContext.putContext(DingConstant.DING_REQUEST_BODY, parseRequestVo(messageContext));
        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }


    /**
     * 格式化发送数据
     */
    private String formatUserContent(MessageContext messageContext) {
        final String target = "[{{time}}]\n{{content}}";
        String tmp;
        if (false) {
            // 内容中是否自动加入时间
            tmp = target.replace("{{time}}", DateUtils.nowDateTimeMillis());
        } else {
            // 只保留{{content}}
            tmp = target.replace("[{{time}}]\n", "");
        }

        String replace = tmp.replace("{{content}}", (((DingTextMessage) messageContext.getMessage()).getText()));

        log.info("content pushed:\n{}", replace);
        return replace;
    }

    /**
     * 计算发送钉钉的json报文
     *
     * @param messageContext .
     * @return .
     */
    private static DingTextRequestDTO parseRequestVo(MessageContext messageContext) {
        DingTextRequestDTO dingTextRequestDTO = new DingTextRequestDTO();

        // 钉钉报文体
        dingTextRequestDTO.setText(new HashMap<String, Object>() {{
            put("content", ThreadContext.getContext(DingConstant.DING_TEXT_CONTENT));
        }});

        return dingTextRequestDTO;
    }

}