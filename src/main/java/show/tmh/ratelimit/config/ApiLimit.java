package show.tmh.ratelimit.config;

import java.util.StringJoiner;

/**
 * URI的限流配置
 *
 * @author yuhao
 * @date 2020/7/27 11:21 上午
 */
public class ApiLimit {

    /**
     * 限流时间段(单位s)
     */
    private static final int DEFAULT_UNIT = 1;

    private String uri;
    private int limit;
    private int unit = DEFAULT_UNIT;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiLimit.class.getSimpleName() + "[", "]")
                .add("uri='" + uri + "'")
                .add("limit=" + limit)
                .add("unit=" + unit)
                .toString();
    }
}
