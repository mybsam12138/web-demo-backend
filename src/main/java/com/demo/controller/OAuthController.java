package com.demo.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.demo.entity.User;
import com.demo.service.UserService;
import com.github.justauth.oauth.OAuthCommonService;
import com.github.justauth.oauth.enums.OAuthProvider;
import com.github.justauth.oauth.strategy.OAuthStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {
    
    private final OAuthStrategyFactory oAuthStrategyFactory;
    private final UserService userService;

    @Value("${app.frontend-url:http://niudiantask.cn}")
    private String frontendUrl;

    @GetMapping("/callback/{provider}")
    public RedirectView handleCallback(
            @PathVariable OAuthProvider provider,
            @ModelAttribute AuthCallback callback) {
        try {
            log.info("Processing {} OAuth callback", provider.name());
            OAuthCommonService service = oAuthStrategyFactory.get(provider);
            AuthUser authUser = service.getOauthUser(callback);
            
            // Save or update user in database
            User user = userService.saveOrUpdateUser(
                    authUser.getUuid(),
                    provider.name(),
                    authUser.getUsername(),
                    authUser.getNickname(),
                    authUser.getAvatar(),
                    authUser.getEmail()
            );
            
            // Login using Sa-Token (create token and store user info)
            StpUtil.login(user.getId());
            SaSession session = StpUtil.getSession();
            setIfNotNull(session, "userId",    user.getId());
            setIfNotNull(session, "username",  user.getUsername());
            setIfNotNull(session, "provider",  user.getProvider());
            setIfNotNull(session, "nickname",  user.getNickname());
            setIfNotNull(session, "avatar",    user.getAvatar());
            setIfNotNull(session, "email",     user.getEmail());

            log.info("OAuth login successful for user: {} (ID: {}), token: {}", 
                    user.getUsername(), user.getId(), StpUtil.getTokenValue());
            
            // Get redirect URL from state or use default frontend URL
            String redirectUrl = callback.getState() != null 
                    ? callback.getState() 
                    : frontendUrl;
            
            // Redirect to frontend success page
            return new RedirectView(redirectUrl + "/callback/success");
        } catch (Exception e) {
            log.error("OAuth callback failed for provider: {}", provider, e);
            // Redirect to frontend error page
            return new RedirectView(frontendUrl + "/callback/error?message=" + 
                    java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8));
        }
    }

    private void setIfNotNull(SaSession session, String key, Object value) {
        if (value != null) {
            session.set(key, value);
        }
    }
}

