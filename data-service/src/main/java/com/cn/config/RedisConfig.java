package com.cn.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.List;

@SpringBootConfiguration
// @ConditionalOnProperty注解来控制@Configuration是否生效.
@ConditionalOnProperty(name = "spring.redis.sentinel.master")
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisConnectionFactory redisConnectionFactory() {
        if (redisProperties.getSentinel() != null) {
            RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
            redisSentinelConfiguration.master(redisProperties.getSentinel().getMaster());
            List<String> nodes = redisProperties.getSentinel().getNodes();
            for (String node : nodes) {
                String[] hostAndPort = node.split(":");
                    redisSentinelConfiguration.sentinel(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
            }
            return new LettuceConnectionFactory(redisSentinelConfiguration);
        }
        else {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(redisProperties.getHost());
            redisStandaloneConfiguration.setPort(redisProperties.getPort());
            redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
            return new LettuceConnectionFactory(redisStandaloneConfiguration);
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 创建序列化器
        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        serializer.setObjectMapper(objectMapper);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 自定义value的序列化器，应付负载的类型转换
        redisTemplate.setValueSerializer(serializer);
        // afterPropertiesSet 完成该bean属性填充之后调用该方法，某个属性赋值需要通过外界获取数据来赋值时，可以使用该方法
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
