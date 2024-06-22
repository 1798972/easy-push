package cn.yang37.util;

import org.junit.jupiter.api.Test;

class OgnlUtilsTest {

    @Test
    void name1() {
        String res = "{\"Response\":{\"Error\":{\"Code\":\"AuthFailure.SecretIdNotFound\",\"Message\":\"The SecretId is not found, please ensure that your SecretId is correct.\"},\"RequestId\":\"eff6701b-c350-427e-a13a-9e60532a7f39\"}}";
        Object value = OgnlUtils.getValue("Response.Error.Code", GsonUtils.toMap(res));
        System.out.println(value);
    }

    @Test
    void name2() {
        String str = "{\"Message\":\"OK\",\"RequestId\":\"A89B4E3C-6EC9-5FA2-9844-850035F4CF84\",\"Code\":\"OK\",\"BizId\":\"585322316389060672^0\"}";

        Object code = OgnlUtils.getValue("Code", GsonUtils.toMap(str));
        System.out.println(code);
    }
}