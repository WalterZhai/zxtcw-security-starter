package com.zxtcw.starter.username;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @comment
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Component
public class UsernameLoginProvider implements AuthenticationProvider {

    @Autowired
    private UsernameProviderServiceImpl usernameProviderServiceImpl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String)authentication.getCredentials();// 这个是表单中输入的密码；

        UserDetails user = usernameProviderServiceImpl.loadUserByUsername(mobile);
        if(user == null){
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernameLoginAuthenticationToken(user, password, authorities);
    }

    /**
     * @comment 根据Token的类匹配，决定哪个Provider去验证登录
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernameLoginAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
