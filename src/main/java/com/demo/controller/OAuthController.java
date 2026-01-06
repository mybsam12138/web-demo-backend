package com.demo.controller;

import com.demo.vo.OAuthCallbackResponse;
import com.demo.vo.OAuthUserDto;
import com.github.justauth.oauth.OAuthCommonService;
import com.github.justauth.oauth.enums.OAuthProvider;
import com.github.justauth.oauth.strategy.OAuthStrategyFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    
    private final OAuthStrategyFactory oAuthStrategyFactory;

    @GetMapping("/callback/{provider}")
    public OAuthCallbackResponse handleCallback(
            @PathVariable OAuthProvider provider,
            @RequestParam Map<String, String> params) {
        try {
            OAuthCommonService service = oAuthStrategyFactory.get(provider);
            AuthCallback callback = AuthCallback.builder()
                    .code(params.get("code"))
                    .state(params.get("state"))
                    .oauth_token(params.get("oauth_token"))
                    .oauth_verifier(params.get("oauth_verifier"))
                    .build();
            
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
            return new OAuthCallbackResponse(false, null, null, e.getMessage());
        }
    }
}

