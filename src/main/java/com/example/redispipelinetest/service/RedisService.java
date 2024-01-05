package com.example.redispipelinetest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void saveDataByPipeline() {
        stringRedisTemplate.executePipelined(
            (RedisCallback<Object>) connection -> {
                connection.openPipeline();

                for (int i = 0; i < 500; i++) {
                    StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                    String key = "key" + i;
                    String val = "test" + i; 

                    stringRedisConnection.set(key, val);
                }

                connection.closePipeline();
                return null;
            }
        );
    }

    @Transactional
    public void saveData() {
        for (int i = 0; i < 500; i++) {
            String key = "key" + i;
            String val = "value" + i;
            redisTemplate.opsForValue().set(key, val);
        }
    }

    public List<String> findDataByPipeline() {
        List<Object> values = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (int i = 0; i < 500; i++) {
                    StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                    String key = "key" + i;
                    stringRedisConnection.get(key);
                }
                return null;
            }
        );

        return values.stream()
            .map(value -> (String) value)
            .toList();
    }

    public List<String> findData() {
        List<String> results = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            String key = "key" + i;
            Object value = redisTemplate.opsForValue().get(key);
            results.add((String) value);
        }

        return results;
    }

}
