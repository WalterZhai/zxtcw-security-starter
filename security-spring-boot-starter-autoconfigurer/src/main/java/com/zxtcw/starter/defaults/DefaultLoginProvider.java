package com.zxtcw.starter.defaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @comment 默认登录的提供程序
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Component
public class DefaultLoginProvider implements AuthenticationProvider {

    @Autowired
    private DefaultProviderServiceImpl defaultProviderServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String)authentication.getCredentials();// 这个是表单中输入的密码；

        UserDetails user = defaultProviderServiceImpl.loadUserByUsername(userName);

        if(user == null){
            return null;
        }

        if(!password.equals(user.getPassword())){
            return null;
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }

    /**
     * @comment 根据Token的类匹配，决定哪个Provider去验证登录
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
