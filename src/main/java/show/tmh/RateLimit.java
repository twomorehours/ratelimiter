package show.tmh;

import show.tmh.ratelimit.config.ApiLimit;
import show.tmh.ratelimit.config.AppLimit;
import show.tmh.ratelimit.config.RuleConfig;
import show.tmh.ratelimit.loader.FileSourceConfigLoader;
import show.tmh.ratelimit.strategy.memory.FixedTimeWindowStrategy;
import show.tmh.ratelimit.strategy.RateLimitStrategy;
import show.tmh.ratelimit.strategy.StrategyFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 限流入口
 *
 * @author yuhao
 * @date 2020/7/27 10:58 上午
 */
public class RateLimit {

    private static final String DEFAULT_FILENAME = "ratelimit.yaml";
    private static final StrategyFactory DEFAULT_STRATEGY_FACTORY =
            (appId, uri, unit, limit) -> new FixedTimeWindowStrategy(unit, limit);


    private Map<String, RateLimitStrategy> map = new HashMap<>();
    private String fileName = DEFAULT_FILENAME;
    private StrategyFactory factory = DEFAULT_STRATEGY_FACTORY;

    public RateLimit() {
        init();
    }

    public RateLimit(String fileName, StrategyFactory factory) {
        this.fileName = fileName;
        this.factory = factory;
        init();
    }

    public RateLimit(StrategyFactory factory) {
        this.factory = factory;
        init();
    }

    public RateLimit(String fileName) {
        this.fileName = fileName;
        init();
    }

    public boolean limit(String appId, String uri) {
        RateLimitStrategy strategy = map.get(appId + "#" + uri);
        if (strategy == null) {
            return true;
        }
        return strategy.tryAcquire();
    }


    private void init() {
        FileSourceConfigLoader loader = new FileSourceConfigLoader(fileName);
        RuleConfig load = loader.load();
        List<AppLimit> appLimits = load.getAppLimits();
        if (appLimits != null && !appLimits.isEmpty()) {
            for (AppLimit appLimit : appLimits) {
                String appId = appLimit.getAppId();
                for (ApiLimit limit : appLimit.getApiLimits()) {
                    map.put(appId + "#" + limit.getUri(),
                            factory.create(appId, limit.getUri(), limit.getUnit(),
                                    limit.getLimit()));
                }
            }
        }
    }


}
