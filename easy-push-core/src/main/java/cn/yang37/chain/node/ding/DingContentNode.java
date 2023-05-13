package cn.yang37.chain.node.ding;

import cn.yang37.constant.DingConstant;
import cn.yang37.constant.LogConstant;
import cn.yang37.entity.MessageContext;
import cn.yang37.entity.sence.ding.DingTextMessage;
import cn.yang37.entity.vo.DingTextRequestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description: 构建请求钉钉的报文数据
 * @class: DingContentNode
 * @author: yang37z@qq.com
 * @date: 2023/1/30 10:06
 * @version: 1.0
 */
public class DingContentNode extends DingMessageNodeAdapter {

    private static final Logger log = LoggerFactory.getLogger(DingContentNode.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    @Override
    public MessageContext nodeSingleSend(MessageContext messageContext) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        messageContext.extMapPut(DingConstant.DING_TEXT_CONTENT, formatUserContent(messageContext));
        messageContext.extMapPut(DingConstant.DING_REQUEST_BODY, parseRequestVo(messageContext));
        return messageContext;
    }

    @Override
    public List<MessageContext> nodeMultipleSend(List<MessageContext> messageContextList) {
        return null;
    }


    /**
     * 格式化发送数据
     *
     * @param messageContext
     * @return
     */
    private String formatUserContent(MessageContext messageContext) {
        final String target = "[{{time}}]\n{{content}}";
        String tmp;
        if (false) {
            // 内容中是否自动加入时间
            tmp = target.replace("{{time}}", DATE_FORMAT.format(new Date(System.currentTimeMillis())));
        } else {
            // 只保留{{content}}
            tmp = target.replace("[{{time}}]\n", "");
        }

        String replace = tmp.replace("{{content}}", (((DingTextMessage) messageContext.getMessage()).getText()));

        log.info("{}content pushed by users:\n{}", LogConstant.Ding.PRE, replace);
        return replace;
    }

    /**
     * 计算发送钉钉的json报文
     *
     * @param messageContext .
     * @return .
     */
    private static DingTextRequestVo parseRequestVo(MessageContext messageContext) {
        DingTextRequestVo dingTextRequestVo = new DingTextRequestVo();

        // 钉钉报文体
        dingTextRequestVo.setText(new HashMap<String, Object>() {{
            put("content", messageContext.extMapGet(DingConstant.DING_TEXT_CONTENT));
        }});

        return dingTextRequestVo;
    }


}