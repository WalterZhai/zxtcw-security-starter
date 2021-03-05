package com.zxtcw.starter.wechat;

import com.alibaba.fastjson.JSONObject;
import com.zxtcw.starter.entity.SecurityResult;
import com.zxtcw.starter.session.SetSessionAttributeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("weChatAuthenticationSuccessHandler")
public class WeChatAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * @comment 认证成功后，需要添加的session属性
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Autowired
    private SetSessionAttributeImpl setSessionAttribute;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        setSessionAttribute.setArrtibute(request,authentication);
        response.getWriter().write(JSONObject.toJSONString(new SecurityResult()));
    }
}
