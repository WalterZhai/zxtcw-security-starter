package com.zxtcw.starter.defaults;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @comment 默认登录认证过滤器
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
public class DefaultLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * @comment 参数名称
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = false;

    /**
     * @comment 设定过滤地址
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    protected DefaultLoginFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = request.getParameter(this.usernameParameter);
        String password = request.getParameter(this.passwordParameter);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        // 该处对第一步的token进行包装，用于在AuthenticationProvider里面校验是否该AuthenticationProvider拦截校验
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        this.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * @comment 利用获得的token,buildDetails request
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


}
