package com.demo.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id(keyType = KeyType.Auto)
    private Long id;

    private String uuid; // OAuth provider user ID

    private String provider; // OAUTH_GOOGLE, OAUTH_TWITTER, etc.

    private String username;

    private String nickname;

    private String avatar;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Initialize timestamps on create
     */
    public void initializeTimestamps() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update timestamp on update
     */
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}

