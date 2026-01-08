package com.demo.service;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveOrUpdateUser(String uuid, String provider, String username, 
                                  String nickname, String avatar, String email) {
        User user = userRepository.findByUuidAndProvider(uuid, provider);
        
        if (user!=null) {
            user.setUsername(username);
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setEmail(email);
            user.updateTimestamp();
            userRepository.update(user);
            log.info("Updated user: {} from provider: {}", uuid, provider);
            return user;
        } else {
            // Create new user
            User newUser = new User();
            newUser.setUuid(uuid);
            newUser.setProvider(provider);
            newUser.setUsername(username);
            newUser.setNickname(nickname);
            newUser.setAvatar(avatar);
            newUser.setEmail(email);
            newUser.initializeTimestamps();
            userRepository.insert(newUser);
            log.info("Created new user: {} from provider: {}", uuid, provider);
            return newUser;
        }
    }
}

