package com.demo.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfig implements WebMvcConfigurer {

    /**
     * Register Sa-Token interceptor
     * OAuth endpoints are excluded from authentication check
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
            // Exclude OAuth endpoints from authentication check
            SaRouter.match("/**")
                    .notMatch("/oauth/**")
                    .notMatch("/user/info")
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}

