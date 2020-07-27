package show.tmh.ratelimit.strategy;

/**
 * 限流策略
 *
 * @author yuhao
 * @date 2020/7/27 11:48 上午
 */
@FunctionalInterface
public interface RateLimitStrategy {


    /**
     * 请求通过
     *
     * @return 是否通过
     */
    boolean tryAcquire();
}
