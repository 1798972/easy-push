package cn.yang37.easypush.entity.config;

import lombok.*;

/**
 * @description:
 * @class: TencentSmsV3ConfigProperties
 * @author: yang37z@qq.com
 * @date: 2024/3/2 1:56
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SmsAliConfigProperties extends SceneConfigProperties {

    public static final String PREFIX = "cn.yang37.easy-push.sms.ali-v3";

    private String baseUrl;

    private String accessKeyId;

    private String accessKeySecret;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}