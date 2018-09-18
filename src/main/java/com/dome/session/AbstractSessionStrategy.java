package com.dome.session;

import com.dome.constant.ResultEnum;
import com.dome.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AbstractSessionStrategy {

    /**
     * 过期跳转的url
     */
    private String destinationUrl;

    /**
     * 重定向策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 跳转前是否创建新的session
     */
    private boolean createNewSession = true;

    private ObjectMapper objectMapper = new ObjectMapper();


    public AbstractSessionStrategy(String invalidSessionUrl) {
        this.destinationUrl = invalidSessionUrl;
    }


    protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("session失效");
        if (createNewSession) {
            request.getSession();
        }
        String targetUrl;
        targetUrl = destinationUrl;
        log.info("跳转到:" + targetUrl);
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//        session返回json
            Object result = buildResponseContent(request);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    protected void onSessionExpired(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("session过期");
        if (createNewSession) {
            request.getSession();
        }
        String targetUrl;
        targetUrl = destinationUrl;
        log.info("跳转到:" + targetUrl);
        Object result = buildResponseContent(request);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
    /**
     * @param request
     * @return
     */
    protected Object buildResponseContent(HttpServletRequest request) {
        String message = "session已失效";
        if (isConcurrency()) {
            message = message + "，有可能是并发登录导致的";
        }
        return ResultUtil.error(ResultEnum.SESSION_INVALID.getCode(), message);
    }

    /**
     * session失效是否是并发导致的
     *
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }

    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

}
