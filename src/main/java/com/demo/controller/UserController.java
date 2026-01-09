package com.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.demo.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    /**
     * Get current user information from token
     */
    @GetMapping("/info")
    public Map<String, Object> getCurrentUser() {
        // Check if user is logged in
        if (!StpUtil.isLogin()) {
            throw new ServiceException("User not logged in");
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", StpUtil.getSession().get("userId"));
        userInfo.put("username", StpUtil.getSession().get("username"));
        userInfo.put("provider", StpUtil.getSession().get("provider"));
        userInfo.put("nickname", StpUtil.getSession().get("nickname"));
        userInfo.put("avatar", StpUtil.getSession().get("avatar"));
        userInfo.put("email", StpUtil.getSession().get("email"));
        userInfo.put("token", StpUtil.getTokenValue());
        
        return userInfo;
    }

    /**
     * Logout
     */
    @GetMapping("/logout")
    public Map<String, String> logout() {
        StpUtil.logout();
        Map<String, String> result = new HashMap<>();
        result.put("message", "Logout successful");
        return result;
    }
}

