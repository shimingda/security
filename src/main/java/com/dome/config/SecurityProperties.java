package com.dome.config;

import com.dome.constant.LoginType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Created on 2018/1/10.
 *
 * @author zlf
 * @since 1.0
 */

@ConfigurationProperties(prefix = "merryyou.security")
public class SecurityProperties {

//    private String signOutUrl = "/merryyou-signOut.html";
    private String signOutUrl = "/merryyou-signOut.html";

    private LoginType loginType = LoginType.JSON;

    /**
     * 验证码配置
     */
//    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 记住我的有效时间秒
     */
    private int rememberMeSeconds = 60 * 60 * 24 * 7;

    /**
     * session配置过期和并发登录
     */
    private SessionProperties session = new SessionProperties();

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}
