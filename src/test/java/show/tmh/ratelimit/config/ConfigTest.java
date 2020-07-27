package show.tmh.ratelimit.config;


import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;
import show.tmh.ratelimit.loader.FileSourceConfigLoader;

import java.io.InputStream;

public class ConfigTest {

    @Test
    public void testLoadConfig() {
        FileSourceConfigLoader loader = new FileSourceConfigLoader(
                "ratelimit.yaml");
        RuleConfig load = loader.load();
        System.out.println(load);
    }
}
