package net.lab1024.sa.base.module.support.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作
 *
 * @author Link
 * @since 2024/12/11 14:07
 **/
@Slf4j
@Component
public class RedisRepository {
    @Autowired
    public RedisTemplate<String, Object> redisTemplate;
    // 默认填充字符
    private static final char DEFAULT_PADDING = '0';

    /**
     * 获取链接工厂
     *
     * @return redisConnectionFactory
     */
    public RedisConnectionFactory getConnectionFactory() {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetConnectionFactory---->factory:{},", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        }
        return this.redisTemplate.getConnectionFactory();
    }

    /**
     * 获取 RedisTemplate对象
     *
     * @return redisTemplate
     */
    public RedisTemplate<String, Object> getRedisTemplate() {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetRedisTemplate---->{}", redisTemplate.toString());
        }
        return redisTemplate;
    }

    /**
     * 添加不失效缓存
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param value  缓存的value
     */
    public void setNoExpire(String key, Object value) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tset---->key:{},value:{}", key, value);
        }
        if (value == null) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 添加缓存，永不过期，谨慎使用
     *
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param value  缓存的value
     */
    public void set(String key, Object value) {
        set(key, value, 0);
    }

    /**
     * 添加缓存
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param value  缓存的value
     * @param second 失效时间
     */
    public void set(String key, Object value, long second) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tset---->key:{},value:{},second:{}", key, value, second);
        }
        if (value == null) {
            return;
        }
        if (second <= 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
        }
    }

    /**
     * 取缓存
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return Object
     */
    public Object get(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tget---->key:{}", key);
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 取String缓存
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return String
     */
    public String getString(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetString---->key:{}", key);
        }
        Object obj = get(key);
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 取int类型缓存
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return Integer
     */
    public Integer getInteger(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetInteger---->key:{}", key);
        }
        Object obj = get(key);
        if (obj == null) {
            return null;
        }
        return Integer.parseInt(obj.toString());
    }

    /**
     * 更新缓存有效期
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param second 缓存的失效时间
     */
    public boolean updateExpire(String key, long second) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tupdateExpire---->key:{},second:{}", key, second);
        }
        if (StrUtil.isNotEmpty(key) && get(key) != null) {
            return Boolean.TRUE.equals(redisTemplate.expire(key, second, TimeUnit.SECONDS));
        }
        return false;
    }

    /**
     * 获取缓存有效期
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return 缓存有效期
     */
    public Long getExpire(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetExpire---->key:{}", key);
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 当缓存中没有时存入，缓存中存在时不存入
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param value     缓存的value
     * @return 缓存中没有时返回true，缓存中有时返回false
     */
    public boolean putIfPresent(String key, Object value) {
        return putIfPresent(key, value, 0);
    }

    /**
     * 当缓存中没有时存入，缓存中存在时不存入
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param value     缓存的value
     * @param expireSeconds 有效时长（秒）
     * @return 缓存中没有时返回true，缓存中有时返回false
     */
    public boolean putIfPresent(String key, Object value, long expireSeconds) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tputIfExist---->key:{},value:{},expireSeconds{}", key, value, expireSeconds);
        }
        StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
        GenericFastJsonRedisSerializer valueSerializer = (GenericFastJsonRedisSerializer) redisTemplate.getValueSerializer();
        byte[] k = keySerializer.serialize(key);
        byte[] v = valueSerializer.serialize(value);

        return Boolean.TRUE.equals(redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Boolean rs = connection.setNX(k, v);
                if (expireSeconds > 0){
                    connection.expire(k, expireSeconds);
                }
                return rs;
            }
        }));
    }

    /**
     * 缓存中key对应的值增加数值incrValue
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param incrValue 增加的值
     * @return 返回增加后的值
     */
    public long incrBy(String key, long incrValue) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tincrBy---->key:{},incrValue:{}", key, incrValue);
        }
        //将key对应的数字加incrValue。如果key不存在，操作之前，key就会被置为0。
        //如果key的value类型错误或者是个不能表示成数字的字符串，
        //就返回错误。这个操作最多支持64位有符号的正型数字。
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
            byte[] k = keySerializer.serialize(key);
            return connection.incrBy(k, incrValue);
        });
        return result != null ? result : 0L;
    }

    /**
     * 缓存中key对应的值增加数值long
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param incrValue 增加的值
     * @param seconds 有效时长（秒）
     * @return 返回增加后的值
     */
    public long incrBy(String key, long incrValue, long seconds) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tincrBy---->key:{},incrValue:{},seconds:{}", key, incrValue, seconds);
        }
        //将key对应的数字加decrement。如果key不存在，操作之前，key就会被置为0。
        //如果key的value类型错误或者是个不能表示成数字的字符串，
        //就返回错误。这个操作最多支持64位有符号的正型数字。
        Long result = redisTemplate.execute((RedisCallback<Long>) connection -> {
            StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
            byte[] k = keySerializer.serialize(key);
            Long num = connection.incrBy(k, incrValue);
            connection.expire(k, seconds);
            return num;
        });
        return result != null ? result : 0L;
    }

    /**
     * 设置hash值，使用默认的有效时长<br>
     * 注意，默认的有效时长设置的是key的有效时长
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param hashKey hash的key
     * @param hashValue  hash的值
     */
    public void putHashValue(String key, String hashKey, Object hashValue) {
        putHashValue(key, hashKey, hashValue, 0);
    }

    public void putHashValue(String key, JSONObject jsonObj) {
        redisTemplate.opsForHash().putAll(key, BeanUtil.beanToMap(jsonObj));
    }

    public void putHashValue(String key, Map<String, Object> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 设置hash值，指定有效时长<br>
     * 注意，有效时长设置的是key的有效时长
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param hashKey hash的key
     * @param hashValue  hash的值
     * @param expireSeconds 有效时长（秒）
     */
    public void putHashValue(String key, String hashKey, Object hashValue, long expireSeconds) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tputHashValue---->key:{},hashKey:{},expireSeconds:{}", key, hashKey, expireSeconds);
        }
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
        if (expireSeconds > 0){
            redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取hash的值
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @param hashKey hash的key
     * @return hash的值
     */
    public Object getHashValue(String key, String hashKey) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetHashValue---->key:{},hashKey:{}", key, hashKey);
        }
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获取全部的hash值
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return 所有hash值
     */
    public Map<Object, Object> getAllHashValues(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetAllHashValues---->key:{}", key);
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取全部的hash值
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return 所有hash值
     */
    public <T> T getAllHashValues(String key, T clazz) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tgetAllHashValues---->key:{}", key);
        }
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);

        return BeanUtil.fillBeanWithMap(entries, clazz, false);
    }


    /**
     * set 中添加元素，支持一次增加多个元素，逗号分隔即可，结果返回添加的个数
     *
     * @author Link
     * @since 2024/12/20 17:12
     * @param key key
     * @param value value
     * @return java.lang.Long
     */
    public Long addSet(String key, Object... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * set 中添加元素，支持一次增加多个元素，逗号分隔即可，结果返回添加的个数
     *
     * @author Link
     * @since 2024/12/20 17:12
     * @param key key
     * @param value value
     * @return java.lang.Long
     */
    public Long addSet(String key, List<Object> value) {
        return redisTemplate.opsForSet().add(key, value.toArray());
    }

    /**
     * 移除set中的元素，支持一次删除多个元素，逗号分隔即可，结果返回删除的个数
     *
     * @author Link
     * @since 2024/12/20 17:13
     * @param key key
     * @param value value
     * @return java.lang.Long
     */
    public Long removeSet(String key, Object... value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 获取set中的所有元素，返回一个Set集合
     *
     * @author Link
     * @since 2024/12/20 17:15
     * @param key key
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取set的大小
     *
     * @author Link
     * @since 2024/12/20 17:14
     * @param key key
     * @return java.lang.Long
     */
    public Long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    /**
     * 删除指定的key
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     */
    public void del(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tdel---->key:{}", key);
        }
        redisTemplate.delete(key);
    }

    /**
     * 删除指定的hash中的key
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key     缓存的key
     * @param hashKey hash的key
     */
    public void delHash(String key, String hashKey) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tdelHash---->key:{},hashKey:{}", key, hashKey);
        }
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断缓存中有没有键为key的值
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param key 缓存的key
     * @return true if exists else false
     */
    public boolean exists(String key) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\texists---->key:{}", key);
        }
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 获取匹配pattern的key
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param pattern 匹配规则
     * @return 所有匹配的key
     */
    public Set<String> keys(String pattern) {
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tkeys---->pattern:{}", pattern);
        }
        return redisTemplate.keys(pattern);
    }

    /**
     * 根据传入的前缀删除匹配的key
     *
     * @author Link
     * @since 2024/12/11 15:41
     * @param names names
     */
    public void delKeysByNamesPrefix(String... names) {
        if (names == null || names.length < 1) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("RedisRepository:\tdelKeysByNamesPrefix---->names:{}", Arrays.toString(names));
        }
        Arrays.stream(names).forEach(key -> {
            this.keys(key.concat("*")).forEach(this::del);
        });
    }
}
