package com.zxtcw.starter;

import com.zxtcw.starter.defaults.DefaultAuthenticationFailureHandler;
import com.zxtcw.starter.defaults.DefaultAuthenticationSuccessHandler;
import com.zxtcw.starter.defaults.DefaultLoginProvider;
import com.zxtcw.starter.mobile.MobileAuthenticationFailureHandler;
import com.zxtcw.starter.mobile.MobileAuthenticationSuccessHandler;
import com.zxtcw.starter.mobile.MobileLoginFilter;
import com.zxtcw.starter.mobile.MobileLoginProvider;
import com.zxtcw.starter.username.UsernameAuthenticationFailureHandler;
import com.zxtcw.starter.username.UsernameAuthenticationSuccessHandler;
import com.zxtcw.starter.username.UsernameLoginFilter;
import com.zxtcw.starter.username.UsernameLoginProvider;
import com.zxtcw.starter.wechat.WeChatAuthenticationFailureHandler;
import com.zxtcw.starter.wechat.WeChatAuthenticationSuccessHandler;
import com.zxtcw.starter.wechat.WeChatLoginFilter;
import com.zxtcw.starter.wechat.WeChatLoginProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.annotation.Resource;

/**
 * @comment security自动配置类
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Configuration
public class SecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private DefaultLoginProvider defaultLoginProvider;

    @Resource
    private MobileLoginProvider mobileLoginProvider;

    @Resource
    private UsernameLoginProvider usernameLoginProvider;

    @Resource
    private WeChatLoginProvider weChatLoginProvider;

    @Resource
    private DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    @Resource
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

    @Resource
    private MobileAuthenticationSuccessHandler mobileAuthenticationSuccessHandler;

    @Resource
    private MobileAuthenticationFailureHandler mobileAuthenticationFailureHandler;

    @Resource
    private UsernameAuthenticationSuccessHandler usernameAuthenticationSuccessHandler;

    @Resource
    private UsernameAuthenticationFailureHandler usernameAuthenticationFailureHandler;

    @Resource
    private WeChatAuthenticationSuccessHandler weChatAuthenticationSuccessHandler;

    @Resource
    private WeChatAuthenticationFailureHandler weChatAuthenticationFailureHandler;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * @comment 注册提供程序
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(defaultLoginProvider);
        auth.authenticationProvider(mobileLoginProvider);
        auth.authenticationProvider(usernameLoginProvider);
        auth.authenticationProvider(weChatLoginProvider);
    }

    /**
     * @comment 核心http配置
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //注册所有需要加入验证序列的验证的拦截器
        http.addFilterBefore(usernamePasswordAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        http.addFilterBefore(mobileLoginFilter(), AbstractPreAuthenticatedProcessingFilter.class);

        http.cors()
                .and().authorizeRequests() // 通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。
                .antMatchers("/login","/mobileLogin","/usernameLogin","/weChatLogin","/invalid","/asset/**","/*.txt").permitAll() // 不需要任何认证就可以访问的地址
                .anyRequest().authenticated() // 其他的路径都必须通过身份认证
                .and().formLogin().loginPage("/invalid")// 当用户未登录时，访问需要身份认证的地址,转向invalid url
                .and().logout().permitAll();
        http.csrf().disable();//同源限制取消
        http.sessionManagement().maximumSessions(1).expiredUrl("/invalid");// 如果登录数量超过一个，旧的认证将会被踢出，旧用户转向invalid url
        http.headers().frameOptions().disable();//禁止iframe嵌套,关闭
    }

    /**
     * @comment 这个必须重写，才能使用AuthenticationManager，在成员变量注入进来，再注入过滤器中
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * @comment 默认登录认证过滤器
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(defaultAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(defaultAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    /**
     * @comment 手机登录认证过滤器
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Bean
    public MobileLoginFilter mobileLoginFilter() {
        MobileLoginFilter filter = new MobileLoginFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(mobileAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(mobileAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/mobileLogin");
        return filter;
    }


    /**
     * @comment 用户名登录认证过滤器
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Bean
    public UsernameLoginFilter usernameLoginFilter() {
        UsernameLoginFilter filter = new UsernameLoginFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(usernameAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(usernameAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/usernameLogin");
        return filter;
    }

    /**
     * @comment 微信登录认证过滤器
     * @author Walter(翟笑天)
     * @date 2021/3/4
     */
    @Bean
    public WeChatLoginFilter weChatLoginFilter() {
        WeChatLoginFilter filter = new WeChatLoginFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(weChatAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(weChatAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/weChatLogin");
        return filter;
    }

}
