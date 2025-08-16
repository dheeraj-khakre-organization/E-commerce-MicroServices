package com.ecommerce.sales_promotionservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired(required = true)
    private RedisTemplate redisTemplate;

    public void saveData(String key, Object o,Long ttl) {
       try{
           ObjectMapper obj = new ObjectMapper();
           String objStr = obj.writeValueAsString(o);
           redisTemplate.opsForValue().set(key, objStr,ttl, TimeUnit.SECONDS);
       }catch (JsonProcessingException ex){
           log.error(String.valueOf(ex));
           throw new RuntimeException(ex);
       }
    }

    public <T> T getData(String key,Class<T> entity)  {
       try{
           Object o= redisTemplate.opsForValue().get(key);
           ObjectMapper objectMapper = new ObjectMapper();
           assert o != null;
           return objectMapper.readValue(o.toString(),entity);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }

    }
}
