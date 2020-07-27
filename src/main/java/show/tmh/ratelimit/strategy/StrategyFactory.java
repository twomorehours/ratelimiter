package show.tmh.ratelimit.strategy;

/**
 * 自定义strategy工厂
 *
 * @author yuhao
 * @date 2020/7/27 1:57 下午
 */
@FunctionalInterface
public interface StrategyFactory {

    /**
     * 返回自定义的Stategy
     *
     * @param appId appId
     * @param uri   限流的uri
     * @param unit  几秒
     * @param limit 几次
     * @return 策略
     */
    RateLimitStrategy create(String appId, String uri, int unit, int limit);
}
