package cn.yang37.config;

/**
 * @description:
 * @class: Ding
 * @author: yangl1@csxbank.com
 * @date: 2023/1/13 23:26
 * @version: 1.0
 */

public class DingTextConfigProperties {

    public static final String PREFIX = "cn.yang37.easy-push.ding.";

    private String baseUrl;

    private String accessToken;

    private String secret;

    /**
     * 展示发送钉钉的具体信息
     */
    private Boolean showContent;

    /**
     * 消息内容前自动追加时间信息
     */
    private Boolean contentAddTime;

    /**
     * 展示发送钉钉的扩展信息
     */
    private Boolean showExtInfo;

    /**
     * 展示发送钉钉的结果
     */
    private Boolean showState;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean getShowContent() {
        return showContent;
    }

    public void setShowContent(Boolean showContent) {
        this.showContent = showContent;
    }

    public Boolean getContentAddTime() {
        return contentAddTime;
    }

    public void setContentAddTime(Boolean contentAddTime) {
        this.contentAddTime = contentAddTime;
    }

    public Boolean getShowExtInfo() {
        return showExtInfo;
    }

    public void setShowExtInfo(Boolean showExtInfo) {
        this.showExtInfo = showExtInfo;
    }

    public Boolean getShowState() {
        return showState;
    }

    public void setShowState(Boolean showState) {
        this.showState = showState;
    }
}