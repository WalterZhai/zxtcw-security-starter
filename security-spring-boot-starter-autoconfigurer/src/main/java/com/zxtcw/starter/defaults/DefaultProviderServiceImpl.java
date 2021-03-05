package com.zxtcw.starter.defaults;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @comment 默认登录认证的业务认证类接口
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Service
public interface DefaultProviderServiceImpl extends UserDetailsService {
}
