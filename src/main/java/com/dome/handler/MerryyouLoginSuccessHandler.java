package com.dome.handler;


import com.dome.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 登录成功处理器
 */
@Component("merryyouLoginSuccessHandler")
public class MerryyouLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登录成功处理器
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        boolean flag=validateRe(savedRequest);

        if (flag) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.success(authentication.getName())));

        } else {
            super.onAuthenticationSuccess(request, response, authentication);

        }
    }
    private boolean validateRe(SavedRequest savedRequest){
        String url=savedRequest.getRedirectUrl();
        String[] urlArr=url.split("/");

        int count=urlArr.length;
        String methodName=urlArr[count-1];
        List list=Arrays.asList( "favicon.ico","login2");

        return list.contains(methodName);
    }
}
