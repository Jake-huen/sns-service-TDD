package com.heon.sns.repository;


import com.heon.sns.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

// Redis에다가 유저를 캐싱하고, 캐시해서 다시 유저를 가지고오는 역할을 하는 클래스.
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3); // 3일동안만 보관하기

    public void setUser(User user) {
        String key = getKey(user.getUsername());
        log.info("Set User to Redis {} , {}", key, user);
        userRedisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(String userName){
        String key = getKey(userName);
        User user = userRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {} , {}", key, user);
        return Optional.ofNullable(user);
    }

    // USER + admin
    private String getKey(String userName) {
        return "USER:" + userName;
    }
}
