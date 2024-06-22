package cn.yang37.entity.dto.sms.aliv3;

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
public class AliV3HttpBody {
    private String PhoneNumbers;
    private String SignName;
    private String TemplateCode;
    private String TemplateParam;
    private String SmsUpExtendCode;
    private String OutId;
}