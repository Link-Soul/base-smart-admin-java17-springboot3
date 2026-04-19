package net.lab1024.sa.base.config;

import lombok.Data;
import net.lab1024.sa.base.constant.RedisKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义的redis配置
 *
 * @author FangCheng
 * @since 2024/12/12 13:45
 **/
@Data
@Component
@ConfigurationProperties(prefix = "redis.cache-manager")
public class CacheManagerProperties {
    /**
     * 默认过期时间
     * 配置文件配置
     * redis : cache-manager : key-expire
     */
    private long keyExpire = 60 * 60L;
    /**
     * 默认长过期时间
     * 配置文件配置
     * redis : cache-manager : key-long-expire
     */
    private long keyLongExpire = 30 * 24 * 60 * 60L;
    /**
     * 默认缓存分隔符
     * 配置文件配置
     * redis : cache-manager : key-separator
     */
    private String keySeparator = RedisKeyConst.SEPARATOR;

    public String toString(){
        return "{\t" +
                "expire : [" + keyExpire +
                "], separator : [" + keySeparator +
                "]\t}";
    }

}