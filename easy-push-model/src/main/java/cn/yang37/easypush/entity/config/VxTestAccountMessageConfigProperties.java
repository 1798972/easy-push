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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VxTestAccountMessageConfigProperties extends SceneConfigProperties {

    public static final String PREFIX = "cn.yang37.easy-push.vx.test-account";

    private String baseUrl;

    private String appId;

    private String appSecret;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}