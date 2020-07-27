package show.tmh.ratelimit.config;

import java.util.List;
import java.util.StringJoiner;

/**
 * 限流配置
 *
 * @author yuhao
 * @date 2020/7/27 11:18 上午
 */
public class RuleConfig {

    private List<AppLimit> appLimits;

    public List<AppLimit> getAppLimits() {
        return appLimits;
    }

    public void setAppLimits(List<AppLimit> appLimits) {
        this.appLimits = appLimits;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RuleConfig.class.getSimpleName() + "[", "]")
                .add("appLimits=" + appLimits)
                .toString();
    }
}
