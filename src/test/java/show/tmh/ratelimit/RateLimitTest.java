package show.tmh.ratelimit;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import show.tmh.RateLimit;
import show.tmh.ratelimit.strategy.RateLimitStrategy;
import show.tmh.ratelimit.strategy.StrategyFactory;
import show.tmh.ratelimit.strategy.distribute.redis.RedisTokenBucketStrategy;

/**
 * TODO:DOCUMENT ME!
 *
 * @author yuhao
 * @date 2020/7/27 2:11 下午
 */
public class RateLimitTest {

    @Test
    public void testMemoryRateLimit() throws InterruptedException {
        RateLimit rateLimit = new RateLimit();
        for (int i = 0; i < 100000; i++) {
            if (rateLimit.limit("order-service", "/a")) {
                System.out.println("pass order-service /a");
                System.out.println(System.currentTimeMillis());
            }
            if (rateLimit.limit("order-service", "/b")) {
                System.out.println("pass order-service /b");
                System.out.println(System.currentTimeMillis());
            }
            if (rateLimit.limit("payment-service", "/c")) {
                System.out.println("pass payment-service /c");
                System.out.println(System.currentTimeMillis());
            }
            Thread.sleep(100);
        }
    }

    @Test
    public void testRedisRateLimit() throws InterruptedException {
        RateLimit rateLimit = new RateLimit(
                (appId, uri, unit, limit) -> new RedisTokenBucketStrategy(redisTemplate(), unit, limit,
                        appId + "#" + uri));
        for (int i = 0; i < 100000; i++) {
//            if (rateLimit.limit("order-service", "/a")) {
//                System.out.println("pass order-service /a");
//                System.out.println(System.currentTimeMillis());
//            }
//            if (rateLimit.limit("order-service", "/b")) {
//                System.out.println("pass order-service /b");
//                System.out.println(System.currentTimeMillis());
//            }
            if (rateLimit.limit("payment-service", "/c")) {
                System.out.println("pass payment-service /c");
                System.out.println(System.currentTimeMillis());
            }
            Thread.sleep(100);
        }
    }


//    public JedisPoolConfig jedisPoolConfig() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        //当池内没有可用的连接时，最大等待时间
//        poolConfig.setMaxWaitMillis(10000);
//        //------其他属性根据需要自行添加-------------
//        return poolConfig;
//    }

    public RedisConnectionFactory redisConnectionFactory() {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName("127.0.0.1");
        //设置默认使用的数据库
        redisStandaloneConfiguration.setDatabase(1);
        //设置redis的服务的端口号
        redisStandaloneConfiguration.setPort(6379);
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
//        jpcb.poolConfig(jedisPoolConfig());
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
