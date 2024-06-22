package cn.yang37.entity.config;

/**
 * @description:
 * @class: MessageConfigEnabledProperties
 * @author: yang37z@qq.com
 * @date: 2023/1/28 14:22
 * @version: 1.0
 */
public class EnabledConfigProperties {

    public static final String PREFIX = "cn.yang37.easy-push.enabled";

    private Boolean ding;

    private Boolean email;

    private Boolean showChainNodeInfo;

    private Boolean showInitContextInfo;

    private Boolean showEndContextInfo;

    public Boolean getDing() {
        return ding;
    }

    public void setDing(Boolean ding) {
        this.ding = ding;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Boolean getShowChainNodeInfo() {
        return showChainNodeInfo;
    }

    public void setShowChainNodeInfo(Boolean showChainNodeInfo) {
        this.showChainNodeInfo = showChainNodeInfo;
    }

    public Boolean getShowInitContextInfo() {
        return showInitContextInfo;
    }

    public void setShowInitContextInfo(Boolean showInitContextInfo) {
        this.showInitContextInfo = showInitContextInfo;
    }

    public Boolean getShowEndContextInfo() {
        return showEndContextInfo;
    }

    public void setShowEndContextInfo(Boolean showEndContextInfo) {
        this.showEndContextInfo = showEndContextInfo;
    }
}