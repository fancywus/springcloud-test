package com.cn.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * https://blog.51cto.com/u_12195/6896810
 * 这个序列化器是基于fastjson框架
 * @param <T>
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer {

    private ObjectMapper objectMapper = new ObjectMapper();

    //配置序列化与反序列化字符集
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    //开启 fastjson 序列化库的自动类型支持功能，开启后，会在序列化或反序列化过程中自动添加类型信息，确保序列化与反序列化成功
    static
    {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    public FastJson2JsonRedisSerializer(Class<T> clazz)
    {
        super();
        this.clazz = clazz;
    }

    //序列化过程，使用fastjson将对象转为字节数组
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        //添加SerializerFeature.WriteClassName可以在序列化时，添加类型信息
        return JSON.toJSONString(o, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    //反序列化过程，将字节数组转为Java对象
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        String string = new String(bytes, DEFAULT_CHARSET);
        //解析为clazz类型的对象
        return JSON.parseObject(string, clazz);
    }

    public void setObjectMapper(ObjectMapper objectMapper)
    {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz)
    {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
