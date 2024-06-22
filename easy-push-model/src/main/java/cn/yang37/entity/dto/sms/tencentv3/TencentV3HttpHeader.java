package cn.yang37.entity.dto.sms.tencentv3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @class: TencentHttpHeader
 * @author: yang37z@qq.com
 * @date: 2024/3/2 1:17
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TencentV3HttpHeader {
    private String action;
    private String region;
    private Integer timestamp;
    private String version;
    private String authorization;
    private String token;
    private String language;
}