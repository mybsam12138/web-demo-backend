package com.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.demo.entity.User;
import com.demo.exception.ServiceException;
import com.demo.repository.UserRepository;
import com.demo.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    private final UserRepository userRepository;


    /**
     * Get current user information from token, ignore for demo
     */
    @GetMapping("/info")
    public UserVo getCurrentUser() {
        UserVo userVo = new UserVo();
        User user = userRepository.selectOneById(StpUtil.getLoginIdAsLong());
        BeanUtils.copyProperties(user,userVo);
        return userVo;
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

