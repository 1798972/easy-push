package cn.yang37.entity.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @class: Ding
 * @author: yang37z@qq.com
 * @date: 2023/1/13 23:26
 * @version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DingTextConfigProperties extends SceneConfigProperties {

    public static final String PREFIX = "cn.yang37.easy-push.ding";

    private String baseUrl;
    private String accessKey;
    private String secret;
}