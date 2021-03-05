package com.zxtcw.starter.wechat;

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
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Component("weChatAuthenticationFailureHandler")
public class WeChatAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.getWriter().write(JSONObject.toJSONString(new SecurityResult(new RuntimeException("security verify failure"))));
    }
}
