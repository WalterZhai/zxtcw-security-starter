package com.zxtcw.starter.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
public class WeChatLoginFilter extends AbstractAuthenticationProcessingFilter {


    @Autowired
    private WeChatGetUserIdImpl weChatGetUserIdImpl;

    public WeChatLoginFilter() {
        super(new AntPathRequestMatcher("/weChatLogin", "GET"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("GET")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }


        String username = "";

        /**
         * @comment 首先遍历cookie查看是否有username
         * @author Walter(翟笑天)
         * @date 2021/3/4
         */
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("username")){
                    username =  cookie.getValue();
                }
            }
        }


        if(username==null || "".equals(username)){
            String userid = weChatGetUserIdImpl.getUserIdByToken(request,response);
            if(userid!=null && !"".equals(userid)){
                //写个cookie给client，以便下次申请时用
                Cookie cookie  =  new Cookie("username",userid);
                cookie.setMaxAge(365*24*3600);
                response.addCookie(cookie);
                username = userid;
            }
        }

        String password = "";

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        // 该处对第一步的token进行包装，用于在AuthenticationProvider里面校验是否该AuthenticationProvider拦截校验
        WeChatLoginAuthenticationToken authRequest = new WeChatLoginAuthenticationToken(username, password);

        this.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, WeChatLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
