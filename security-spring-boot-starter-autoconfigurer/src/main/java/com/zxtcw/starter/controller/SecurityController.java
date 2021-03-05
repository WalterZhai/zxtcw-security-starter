package com.zxtcw.starter.controller;

import com.zxtcw.starter.entity.SecurityResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Walter(翟笑天)
 * @Date 2021/3/4
 */
@RestController
public class SecurityController {

    @GetMapping(value="/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    @GetMapping(value="/invalid")
    public SecurityResult invalid(HttpServletRequest request) {
        SecurityResult securityResult = new SecurityResult();
        securityResult.setState(-1);
        securityResult.setMessage("security invalid url");
        return securityResult;
    }
}
