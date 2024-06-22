package cn.yang37.entity.dto.ding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @class: DingTextBo
 * @author: yang37z@qq.com
 * @date: 2022/11/15 15:26
 * @version: 1.0
 */
public class DingTextRequestDTO {

    private String msgtype = "text";

    private Map<String, Object> text;

    private List<String> atMobiles = new ArrayList<String>() {
    };

    private String atUserIds;

    private boolean isAtAll = false;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Map<String, Object> getText() {
        return text;
    }

    public void setText(Map<String, Object> text) {
        this.text = text;
    }

    public List<String> getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }

    public String getAtUserIds() {
        return atUserIds;
    }

    public void setAtUserIds(String atUserIds) {
        this.atUserIds = atUserIds;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }
}
