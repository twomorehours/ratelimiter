package show.tmh.ratelimit.strategy.distribute.redis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import show.tmh.ratelimit.strategy.RateLimitStrategy;

/**
 * Redis令牌桶限流实现
 *
 * @author yuhao
 * @date 2020/7/27 2:38 下午
 */
public class RedisTokenBucketStrategy implements RateLimitStrategy {

    private StringRedisTemplate redisTemplate;
    private DefaultRedisScript<Long> script;
    private int unit;
    private int limit;
    private double rate;
    private String key;

    public RedisTokenBucketStrategy(
            StringRedisTemplate redisTemplate, int unit, int limit, String key) {
        this.redisTemplate = redisTemplate;
        this.unit = unit;
        this.limit = limit;
        this.key = key;
        this.rate = (double) limit / unit;
        initScript();
    }


    /**
     * 启动将脚本加载到redis中 后面使用sha1代理
     */
    private void initScript() {
        script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("redis/ratelimiter.lua"));
        this.redisTemplate.execute(
                (RedisCallback<Object>) connection -> connection.scriptLoad(
                        script.getScriptAsString().getBytes()));
    }

    @Override
    public boolean tryAcquire() {
        byte[] nowBytes = (System.currentTimeMillis() / 1000 + "").getBytes();
        Long pass = redisTemplate.execute(
                (RedisCallback<Long>) connection -> connection.evalSha(script.getSha1(),
                        ReturnType.INTEGER, 1,
                        key.getBytes(), (rate + "").getBytes(), (limit + "").getBytes(), nowBytes,
                        "1".getBytes()));
        return pass != null && pass == 1L;
    }
}
