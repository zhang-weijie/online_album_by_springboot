package com.zhangweijie.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2020-12-27 8:56
 */
@Configuration
public class RedisConfig {

    //编写自定义的RedisTemplate
    //一般会封装成工具类
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 为了开发方便，直接使用<String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

// Json 配置序列化
// 使用 jackson 解析任意的对象
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
// 使用 objectMapper 进行转义
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
// String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

// key 采用 String 的序列化方式
        template.setKeySerializer(stringRedisSerializer);
// Hash 的 key 采用 String 的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
// value 采用 jackson 的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
// Hash 的 value 采用 jackson 的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
// 把所有的配置 set 进 template
        template.afterPropertiesSet();

        return template;
    }
}
