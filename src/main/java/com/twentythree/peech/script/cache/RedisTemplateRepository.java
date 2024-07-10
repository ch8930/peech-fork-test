package com.twentythree.peech.script.cache;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@NoArgsConstructor
public class RedisTemplateRepository {

    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // RedisTemplate 인스턴스 생성
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // RedisConnectionFactory 설정하여 Redis 서버와의 연결을 설정
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key-value 직렬화하는 방법 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
