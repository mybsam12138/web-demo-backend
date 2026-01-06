package com.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthCallbackResponse {
    private boolean success;
    private OAuthUserDto user;
    private String redirectUrl;
    private String error;
}

