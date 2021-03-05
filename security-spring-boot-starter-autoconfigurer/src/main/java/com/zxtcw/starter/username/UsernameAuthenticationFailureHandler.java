package com.zxtcw.starter.username;

import com.alibaba.fastjson.JSONObject;
import com.zxtcw.starter.entity.SecurityResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @comment 用户名登录认证，验证失败后的Handler处理
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Component("usernameAuthenticationFailureHandler")
public class UsernameAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.getWriter().write(JSONObject.toJSONString(new SecurityResult(new RuntimeException("security verify failure"))));
    }
}
