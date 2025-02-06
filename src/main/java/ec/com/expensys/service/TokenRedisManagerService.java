package ec.com.expensys.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenRedisManagerService {

    private final RedisTemplate<String,String> redisTemplate;

    public TokenRedisManagerService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveToken(String token, String username, Long TTLminutes){
        redisTemplate.opsForValue().set(token, username, TTLminutes, TimeUnit.MINUTES);
    }

    public void deleteToken(String token){
        redisTemplate.delete(token);
    }

    public boolean existToken(String token){
        return redisTemplate.hasKey(token);
    }
}
