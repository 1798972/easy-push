package cn.yang37.constant;

/**
 * @description:
 * @class: VxTestAccountConstant
 * @author: yang37z@qq.com
 * @date: 2024/7/12 17:01
 * @version: 1.0
 */
public class VxTestAccountConstant {

    public static final String URL = "https://api.weixin.qq.com";

    public static final String PATH = "/cgi-bin/message/template/send";

    public static final class GetAccessToken {
        /**
         * 获取AccessToken
         */
        public static final String PATH = "/cgi-bin/token";

        public static final String GRANT_TYPE = "grant_type";

        public static final String CLIENT_CREDENTIAL = "client_credential";

        public static final String APP_ID = "appid";

        public static final String SECRET = "secret";

        public static final String ACCESS_TOKEN = "access_token";

        public static final String EXPIRES_IN = "expires_in";


    }

}