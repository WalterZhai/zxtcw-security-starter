package com.zxtcw.starter.session;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @comment 添加session变量的接口
 * @author Walter(翟笑天)
 * @date 2021/3/4
 */
@Service
public interface SetSessionAttributeImpl {

    void setArrtibute(HttpServletRequest request, Authentication authentication) throws ServletException;

}
