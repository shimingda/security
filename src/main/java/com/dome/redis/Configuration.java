package com.dome.redis;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 描述: 注册配置类到容器
 **/

@Configuration
@Import({RedisConfig.class, CacheUtils.class})
class RedisAutoConfiguration {

}