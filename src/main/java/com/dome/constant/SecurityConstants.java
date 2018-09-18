package com.dome.constant;

/**
 * Created on 2018/1/4.
 *
 * @author zlf
 * @since 1.0
 */
public interface SecurityConstants {

    /**
     * 默认的处理验证码的url前缀
     */
    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     * 当请求需要身份验证时跳转URL
     */
    String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户注册请求处理url
     */
    String DEFAULT_REGISTER_URL = "/authentication/register";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * session失效默认的跳转地址
     */
    String DEFAULT_SESSION_INVALID_URL = "/session/invalid";

    String DEFAULT_SESSION_EXPIRED_URL = "/session/expired";

    /**
     * 默认用户密码
     */
    String DEFAULT_PASSWORD="123456";


}
