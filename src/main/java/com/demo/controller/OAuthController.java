package com.demo.controller;

import com.demo.exception.ServiceException;
import com.demo.vo.OAuthCallbackResponse;
import com.demo.vo.OAuthUserDto;
import com.github.justauth.oauth.OAuthCommonService;
import com.github.justauth.oauth.enums.OAuthProvider;
import com.github.justauth.oauth.strategy.OAuthStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Slf4j
public class OAuthController {
    
    private final OAuthStrategyFactory oAuthStrategyFactory;

    @GetMapping("/callback/{provider}")
    public OAuthCallbackResponse handleCallback(
            @PathVariable OAuthProvider provider,
            @RequestParam AuthCallback callback) {
        try {
            log.info(provider.name()+" login");
            OAuthCommonService service = oAuthStrategyFactory.get(provider);
            AuthUser authUser = service.getOauthUser(callback);
            OAuthUserDto userDto = new OAuthUserDto(
                    authUser.getUuid(),
                    authUser.getUsername(),
                    authUser.getNickname(),
                    authUser.getAvatar(),
                    authUser.getEmail()
            );
            
            String redirectUrl = callback.getState() != null 
                    ? service.getOauthRedirectUrl(callback.getState()) 
                    : null;
            
            return new OAuthCallbackResponse(true, userDto, redirectUrl, null);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}

