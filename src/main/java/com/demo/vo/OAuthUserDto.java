package com.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthUserDto {
    private String uuid;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
}

