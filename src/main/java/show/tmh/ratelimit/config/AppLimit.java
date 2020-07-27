package show.tmh.ratelimit.config;

import java.util.List;
import java.util.StringJoiner;

/**
 * App限流配置
 *
 * @author yuhao
 * @date 2020/7/27 11:20 上午
 */
public class AppLimit {

    private String appId;
    private List<ApiLimit> apiLimits;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<ApiLimit> getApiLimits() {
        return apiLimits;
    }

    public void setApiLimits(List<ApiLimit> apiLimits) {
        this.apiLimits = apiLimits;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AppLimit.class.getSimpleName() + "[", "]")
                .add("appId='" + appId + "'")
                .add("apiLimits=" + apiLimits)
                .toString();
    }
}
