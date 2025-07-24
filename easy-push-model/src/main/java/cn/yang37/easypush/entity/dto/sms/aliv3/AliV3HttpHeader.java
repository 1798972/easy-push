package cn.yang37.easypush.entity.dto.sms.aliv3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @class: AliHttpHeader
 * @author: yang37z@qq.com
 * @date: 2024/5/21 16:08
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AliV3HttpHeader {

    private String xAcsAction;
    private String xAcsVersion;
    private String Authorization;
    private String xAcsSignatureNonce;
    private String xAcsDate;
    private String xAcsContentSha256;
    private String xAcsSecurityToken;

}