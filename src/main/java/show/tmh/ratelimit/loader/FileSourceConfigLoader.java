package show.tmh.ratelimit.loader;

import org.yaml.snakeyaml.Yaml;
import show.tmh.ratelimit.config.RuleConfig;
import java.io.InputStream;

/**
 * 加载配置文件
 *
 * @author yuhao
 * @date 2020/7/27 12:14 下午
 */
public class FileSourceConfigLoader {

    private String fileName;

    public FileSourceConfigLoader(String fileName) {
        this.fileName = fileName;
    }

    public RuleConfig load() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(
                fileName);
        Yaml yaml = new Yaml();
        return yaml.loadAs(resourceAsStream, RuleConfig.class);
    }
}
