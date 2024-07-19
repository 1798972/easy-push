![easy-push](images/logo.png)

> 多场景信息推送解决方案，只需一行代码即可发送消息到钉钉群、腾讯云短信、阿里云短信等。

一行代码实现发送消息到钉钉群

```java
	MessageContext messageContext = new SendMessageServiceImpl().singleSend(new DingTextMessage("abcdefghijk"));
```



## 0.项目背景

在日常开发中，我们经常需要向多个渠道进行数据推送，例如：

- 发送xx消息到某个钉钉群
- 发送xx邮件到某个邮箱
- 发送xx短信给某个客户
- ...

作为开发者，我们不可避免的需要去查阅渠道方的技术文档，再根据文档去书写对应的代码，这是一件十分枯燥且繁琐的事情。

`easy-push`项目的初衷，就是为了解决这个令人烦恼的问题。



## 1.修改历史

### 1.1 支持的渠道

| 序号 | 渠道             | 实体类               | 说明                                                         | 测试类                                                       | 说明文档                                |
| ---- | ---------------- | -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | --------------------------------------- |
| 1    | 钉钉群文本消息   | DingTextMessage      | 钉钉文本类消息                                               | [MainTest.sendDingTextMessage](easy-push-test/src/test/java/MainTest.java) | -                                       |
| 2    | 腾讯云短信（V3） | SmsTencentV3Message  | [腾讯云短信](https://cloud.tencent.com/document/product/382/55981) | [MainTest.sendTencentSmsV3Message](easy-push-test/src/test/java/MainTest.java) | -                                       |
| 3    | 阿里云短信（V3） | SmsAliV3Message      | [阿里云短信](https://help.aliyun.com/zh/sms/?spm=a2c4g.11186623.0.0.24735ee7SZGzIE) | [MainTest.sendSmsAliV3Message](easy-push-test/src/test/java/MainTest.java) | -                                       |
| 4    | 微信测试号       | VxTestAccountMessage | [微信测试号](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) | [MainTest.sendVxTestAccountMessage](easy-push-test/src/test/java/MainTest.java) | [4-微信测试号.md](docs/4-微信测试号.md) |



### 1.2 更新记录

参见：[CHANGELOG](CHANGELOG.md)



## 2.支持渠道

> 在classpath下新增加`easy-push.properties`文件

所有配置项目，都以`cn.yang37.easy-push`开头，下方是一个示例的配置文件：

```properties
############### 钉钉消息
cn.yang37.easy-push.ding.base-url=https://oapi.dingtalk.com
cn.yang37.easy-push.ding.access-key=xxx
cn.yang37.easy-push.ding.secret=xxx

############### 腾讯云短信V3
cn.yang37.easy-push.sms.tencent-v3.base-url=https://sms.tencentcloudapi.com
cn.yang37.easy-push.sms.tencent-v3.region=ap-guangzhou
cn.yang37.easy-push.sms.tencent-v3.secret-id=xxx
cn.yang37.easy-push.sms.tencent-v3.secret-key=xxx

############### 阿里云短信V3
cn.yang37.easy-push.sms.ali-v3.base-url=https://dysmsapi.aliyuncs.com
cn.yang37.easy-push.sms.ali-v3.access-key-id=xxx
cn.yang37.easy-push.sms.ali-v3.access-key-secret=xxx

############### 微信测试号消息
cn.yang37.easy-push.vx.test-account.base-url=https://api.weixin.qq.com
cn.yang37.easy-push.vx.test-account.app-id=xxx
cn.yang37.easy-push.vx.test-account.app-secret=xxx
```



### 2.1 钉钉群消息（文本类）

#### 2.1.1 配置项

| 前缀 | 项         | 类型   | 说明                                                         |
| ---- | ---------- | ------ | ------------------------------------------------------------ |
| ding | base-url   | String | 钉钉推送服务的默认url，内网时可以是代理机地址。默认值为: https://oapi.dingtalk.com |
| ding | access-key | String | 推送钉钉时的ak                                               |
| ding | secret     | String | 推送钉钉时的sk                                               |

```properties
############### 钉钉消息
cn.yang37.easy-push.ding.base-url=https://oapi.dingtalk.com
cn.yang37.easy-push.ding.access-key=xxx
cn.yang37.easy-push.ding.secret=xxx
```

#### 2.1.2 实体类

**实体类：DingTextMessage**

| 字段名 | 含义     | 类型   | 说明                   |
| ------ | -------- | ------ | ---------------------- |
| text   | 文本内容 | string | 发送到钉钉群的文本内容 |

构建实体类：

```java
      DingTextMessage message = new DingTextMessage("123456789");
```



### 2.2 腾讯云短信（V3）

#### 2.2.1 配置项

| 前缀           | 项         | 类型   | 说明                                                         |
| -------------- | ---------- | ------ | ------------------------------------------------------------ |
| sms.tencent-v3 | base-url   | String | 推送url，内网时可以是代理机地址。默认值为: https://sms.tencentcloudapi.com |
| sms.tencent-v3 | region     | String | 地域参数，用来标识希望操作哪个地域的数据。接口接受的地域取值参考接口文档中输入参数公共参数 Region 的说明。**注意：某些接口不需要传递该参数，接口文档中会对此特别说明，此时即使传递该参数也不会生效。**<br />参考：[region字段](https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8) |
| sms.tencent-v3 | secret-id  | String | 在 [云API密钥](https://console.cloud.tencent.com/capi) 上申请的标识身份的 SecretId，一个 SecretId 对应唯一的 SecretKey ，而 SecretKey 会用来生成请求签名 Signature。 |
| sms.tencent-v3 | secret-key | String | 在 [云API密钥](https://console.cloud.tencent.com/capi) 上申请的标识身份的 SecretId，一个 SecretId 对应唯一的 SecretKey ，而 SecretKey 会用来生成请求签名 Signature。 |

```properties
cn.yang37.easy-push.sms.tencent-v3.base-url=https://sms.tencentcloudapi.com
cn.yang37.easy-push.sms.tencent-v3.region=ap-guangzhou
cn.yang37.easy-push.sms.tencent-v3.secret-id=xxx
cn.yang37.easy-push.sms.tencent-v3.secret-key=xxx
```

#### 2.2.2 实体类

**实体类：SmsTencentV3Message**

详情请参考腾讯云：[发送短信](https://cloud.tencent.com/document/api/382/55981)

| 字段名           | 含义                 | 类型            | 说明                                                         |
| ---------------- | -------------------- | --------------- | ------------------------------------------------------------ |
| phoneNumberSet   | 下发手机号码         | Array of String | 下发手机号码，采用 E.164 标准，格式为+[国家或地区码][手机号]，单次请求最多支持200个手机号且要求全为境内手机号或全为境外手机号。<br/>例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号。<br/>注：发送国内短信格式还支持0086、86或无任何国家或地区码的11位手机号码，前缀默认为+86。<br/>示例值：+8618511122233 |
| smsSdkAppId      | 短信 SdkAppId        | String          | 在 [短信控制台](https://console.cloud.tencent.com/smsv2/app-manage) 添加应用后生成的实际 SdkAppId，示例如1400006666。<br/>示例值：1400006666 |
| templateId       | 模板 ID              | String          | 必须填写已审核通过的模板 ID。模板 ID 可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-template) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-template) 的正文模板管理查看，若向境外手机号发送短信，仅支持使用国际/港澳台短信模板。<br/>示例值：1110 |
| signName         | 短信签名内容         | String          | 使用 UTF-8 编码，必须填写已审核通过的签名，例如：腾讯云，签名信息可前往 [国内短信](https://console.cloud.tencent.com/smsv2/csms-sign) 或 [国际/港澳台短信](https://console.cloud.tencent.com/smsv2/isms-sign) 的签名管理查看。 |
| templateParamSet | 模板参数             | Array of String | 若无模板参数，则设置为空。                                   |
| extendCode       | 短信码号扩展号       | String          | 默认未开通，如需开通请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。<br/>示例值：12 |
| sessionContext   | 用户的 session 内容  | String          | 可以携带用户侧 ID 等上下文信息，server 会原样返回。注意长度需小于512字节。<br/>示例值：test |
| senderId         | 国内短信无需填写该项 | String          | 国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。 |

构建实体类：

```java
    SmsTencentV3Message smsTencentV3Message = SmsTencentV3Message.builder()
            .phoneNumberSet(new String[]{"18712341234"})
            .smsSdkAppId("1400xxxx")
            .signName("yang37")
            .templateId("xxxx")
            .templateParamSet(new String[]{"123456"})
            .sessionContext("SessionContext")
            .build();
```


### 2.3 阿里云短信（V3）

#### 2.3.1 配置项

| 前缀       | 项                | 类型   | 说明                                                         |
| ---------- | ----------------- | ------ | ------------------------------------------------------------ |
| sms.ali-v3 | base-url          | String | 推送url，内网时可以是代理机地址。默认值为: https://dysmsapi.aliyuncs.com |
| sms.ali-v3 | access-key-id     | String | 访问控制台获取：[AccessKey](https://ram.console.aliyun.com/manage/ak?spm=5176.25163407.top-nav.dak.545abb6e3i6J7A) |
| sms.ali-v3 | access-key-secret | String | 访问控制台获取：[AccessKey](https://ram.console.aliyun.com/manage/ak?spm=5176.25163407.top-nav.dak.545abb6e3i6J7A) |

```properties
cn.yang37.easy-push.sms.ali-v3.base-url=https://dysmsapi.aliyuncs.com
cn.yang37.easy-push.sms.ali-v3.access-key-id=xxx
cn.yang37.easy-push.sms.ali-v3.access-key-secret=xxx
```

#### 2.3.2 实体类

**实体类：SmsAliV3Message**

详情请参考阿里云：[发送短信](https://help.aliyun.com/zh/sms/developer-reference/api-dysmsapi-2017-05-25-sendsms?spm=a2c4g.11186623.0.0.8fd818dbZEQiC6)

| 字段名          | 含义                     | 类型   | 说明                                                         |
| --------------- | ------------------------ | ------ | ------------------------------------------------------------ |
| phoneNumbers    | 接收短信的手机号码       | string | 支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为 1000 个手机号码。批量调用相对于单条调用及时性稍有延迟。验证码类型短信，建议使用单独发送的方式。 |
| SignName        | 短信签名名称             | string | 您可以通过 [AddSmsSign](https://help.aliyun.com/zh/sms/developer-reference/api-dysmsapi-2017-05-25-addsmssign?spm=a2c4g.11186623.0.0.4b1334e5uB0hxV) 接口添加签名或在[短信服务控制台](https://dysms.console.aliyun.com/dysms.htm?spm=5176.12818093.categories-n-products.ddysms.3b2816d0xml2NA#/overview)添加签名，签名通过审核后，才可使用签名名称。 |
| TemplateCode    | 短信模板 Code            | string | 您可以通过 [AddSmsTemplate](https://help.aliyun.com/zh/sms/developer-reference/api-dysmsapi-2017-05-25-addsmstemplate?spm=a2c4g.11186623.0.0.4b864201n9RljR) 接口添加模板或在[短信服务控制台](https://dysms.console.aliyun.com/dysms.htm?spm=5176.12818093.categories-n-products.ddysms.3b2816d0xml2NA#/overview)添加模板，模板通过审核后，才可使用模板 Code。 |
| TemplateParam   | 短信模板变量对应的实际值 | string | 支持传入多个参数。{"name":"张三","number":"1390000****"}     |
| SmsUpExtendCode | 上行短信扩展码           | string | 上行短信指发送给通信服务提供商的短信，用于定制某种服务、完成查询，或是办理某种业务等，需要收费，按运营商普通短信资费进行扣费。 |
| OutId           | 外部流水扩展字段         | string | 无特殊需要可忽略此字段。                                     |

构建实体类：

```java
        SmsAliV3Message smsAliV3Message = SmsAliV3Message.builder()
                .PhoneNumbers("18712341234")
                .SignName("阿里云短信测试")
                .TemplateCode("SMS_154950909")
                .TemplateParam("{\"code\":\"1234\"}")
                .build();
```



### 2.4 微信测试号

#### 2.3.1 配置项

| 前缀            | 项         | 类型   | 说明                                                         |
| --------------- | ---------- | ------ | ------------------------------------------------------------ |
| vx.test-account | base-url   | String | 推送url，内网时可以是代理机地址。默认值为: https://api.weixin.qq.com<br />请求时完整的路径为：{base-url}/cgi-bin/message/template/send<br />即为空或者不修改时默认完整url为：https://api.weixin.qq.com/cgi-bin/message/template/send |
| vx.test-account | app-id     | String | 测试号管理-测试号信息：[appID](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) |
| vx.test-account | app-secret | String | 测试号管理-测试号信息：[appsecret](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) |

```properties
cn.yang37.easy-push.vx.test-account.base-url=https://api.weixin.qq.com
cn.yang37.easy-push.vx.test-account.app-id=xxx
cn.yang37.easy-push.vx.test-account.app-secret=xxx
```

#### 2.3.2 实体类

**实体类：VxTestAccountMessage**

详情请参考微信官方文档-公众号-模版消息接口：[发送模板消息](https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html#5)

| 字段名        | 含义                                                         | 类型   | 说明 |
| ------------- | ------------------------------------------------------------ | ------ | ---- |
| touser        | 接收者openid                                                 | string |      |
| template_id   | 模板ID                                                       | string |      |
| url           | 模板跳转链接（海外账号没有跳转能力）                         | string |      |
| miniprogram   | 跳小程序所需数据，不需跳小程序可不用传该数据                 | string |      |
| appid         | 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏 | string |      |
| pagepath      | 外部流水扩展字段                                             | string |      |
| data          | 模板数据                                                     | string |      |
| client_msg_id | 防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填 | string |      |

构建实体类：

```java
        VxTestAccountMessage vxTestAccountMessage = VxTestAccountMessage.builder()
                .touser("xxx")
                .templateId("xxxx")
                .url("https://baidu.com")
                .data("title", "123")
                .data("time", "456")
                .data("content", "789")
                .build();
```



## 3.接入指南

easy-push的理念，是在发送时传入不同的消息对象，通过内部自动映射逻辑，完成这个发送。

```java
public interface SendMessageService {

    /**
     * 发送单条消息
     *
     * @param message .
     * @return .
     */
    MessageContext singleSend(Message message);

    /**
     * 发送多条消息 .
     *
     * @param messageList .
     * @return .
     */
    List<MessageContext> multipleSend(List<Message> messageList);
}

```

显而易见，这里的`Message`是一个父类，在实际使用时，我们需要什么便传递进什么。

![image-20240521222136716](https://markdown-1258124344.cos.ap-guangzhou.myqcloud.com/images/202405212221795.png)

所以，现在我们需要关注的点只在于：

- 写好配置文件
- 构建好我们要发送的消息对象

之后，一切逻辑，都会由easy-push来帮你完成。



### 3.1 Java

一行代码实现发送消息到钉钉群

```java
	MessageContext messageContext = new SendMessageServiceImpl().singleSend(new DingTextMessage("abcdefghijk"));
```

#### 3.1.1 Java SE

##### 1.导入pom

在mvnrepository查询最新版本：[cn.yang37 » easy-push-app](https://mvnrepository.com/artifact/cn.yang37/easy-push-app)

或者在 sonatype 查询最新版本：[cn.yang37 » easy-push-app](https://central.sonatype.com/search?q=easy-push-app)

```xml
    	<dependency>
            <groupId>cn.yang37</groupId>
            <artifactId>easy-push-app</artifactId>
            <version>${latest.version}</version>
        </dependency>
```



##### 2.编写配置文件

```properties
# 推送钉钉的URL,eg: https://oapi.dingtalk.com
cn.yang37.easy-push.ding.base-url=https://oapi.dingtalk.com
# 从钉钉获取
cn.yang37.easy-push.ding.access-key=xx
# 从钉钉获取
cn.yang37.easy-push.ding.secret=xx
```

##### 3.构建信息实体

> 根据场景构建对应的信息实体

```java
// 钉钉-文本类型消息
Message message = new DingTextMessage("123");
```

##### 4.创建信息发送对象

```java
SendMessageService sendMessageService = new SendMessageServiceImpl();
```

##### 5.执行发送方法

```java
// 传入message实体 
MessageContext messageContext = sendMessageService.singleSend(message);
```

##### 6.从返回中获取信息

```java
Boolean state = messageContext.getState();
```



#### 3.1.2 Spring Boot

##### 1.注入Bean

```java
@Component
public class BeanConfig {

    @Bean
    public SendMessageService sendMessageService() {
        return new SendMessageServiceImpl();
    }

}
```

##### 2.使用

```java
@Service
public class MessageSenderService {

    @Autowired
    private SendMessageService sendMessageService;

    public void sendDingMessage(String content) {
        DingTextMessage message = new DingTextMessage(content);
        MessageContext messageContext = sendMessageService.singleSend(message);
        // state显示发送结果
        Boolean state = messageContext.getState();
    }
}
```



## 4.配置加载

加载优先级

- 环境变量`easy.push.config.path`

  ```bash
  # eg: java启动脚本中传入
  -Deasy.push.config.path=/path/to/easy-push.properties
  ```

- 当前classpath
- 当前路径./



## 5.写在最后

easy-push是我临时起意的一个项目，但是我觉得这个思路是不错的。

发送的逻辑总是千变万化的，实际业务开发中，看文档真的佷很很头疼。

如果有一群为爱发电的我们来写好这些烦人的逻辑，这样，大家实际开发时只用再关注于业务代码，能提高不少效率。

不过精力有限，总是搁置很久才来更新一次，项目目前并不是很强大，也缺失了很多功能。

如果你有什么想法，或者想参与进来，可以提`issue`，也可以发邮件联系我`yang37z@qq.com`。

嗯，也许好些年后，咱们的项目真的能帮助到许多人呢^ ^。

