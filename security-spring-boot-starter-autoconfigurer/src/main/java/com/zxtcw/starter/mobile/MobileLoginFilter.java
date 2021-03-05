package com.zxtcw.starter.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @comment 手机登录认证过滤器
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
public class MobileLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * @comment 参数名称
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private boolean postOnly = false;

    public MobileLoginFilter() {
        super(new AntPathRequestMatcher("/mobileLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }


        String mobile = request.getParameter(SPRING_SECURITY_FORM_MOBILE_KEY);
        String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);

        if (mobile == null) {
            mobile = "";
        }

        if (password == null) {
            password = "";
        }

        mobile = mobile.trim();

        // 该处对第一步的token进行包装，用于在AuthenticationProvider里面校验是否该AuthenticationProvider拦截校验
        MobileLoginAuthenticationToken authRequest = new MobileLoginAuthenticationToken(mobile, password);

        this.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * @comment 利用获得的token,buildDetails request
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    protected void setDetails(HttpServletRequest request, MobileLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
