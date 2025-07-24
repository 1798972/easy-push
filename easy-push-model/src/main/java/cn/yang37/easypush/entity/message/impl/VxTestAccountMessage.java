package cn.yang37.easypush.entity.message.impl;

import cn.yang37.easypush.entity.enums.MessageSceneType;
import cn.yang37.easypush.entity.message.Message;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 微信测试号
 * @class: VxTestAccountMessage
 * @author: yang37z@qq.com
 * @date: 2024/7/12 16:34
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder(builderClassName = "VxTestAccountMessageBuilder")
public class VxTestAccountMessage extends Message {

    private String touser;
    private String templateId;
    private String url;
    private Map<String, Object> miniProgram;
    private String appId;
    private String pagePath;
    private Map<String, Map<String, Object>> data;
    private String clientMsgId;

    @Override
    public MessageSceneType getMessageSceneType() {
        return MessageSceneType.VX_TEST_ACCOUNT;
    }

    public static class VxTestAccountMessageBuilder {
        private final Map<String, Map<String, Object>> data = new HashMap<>();

        public VxTestAccountMessageBuilder data(String key, String value) {
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("value", value);
            this.data.put(key, valueMap);
            return this;
        }
    }

}