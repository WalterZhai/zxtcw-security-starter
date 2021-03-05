package com.zxtcw.starter.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Walter(翟笑天)
 * @Date 2021/3/4
 */
public interface WeChatGetUserIdImpl {

    String getUserIdByToken(HttpServletRequest request, HttpServletResponse response);

}
