package cn.yang37.easypush.core.factory;

import cn.yang37.easypush.core.util.ConfigUtils;
import cn.yang37.easypush.entity.enums.ErrorCodeEnum;
import cn.yang37.easypush.entity.exception.EpConfigException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ConfigFactory {

    private static final String ENV_CONFIG_PATH = "easy.push.config.path";
    private static final String DEFAULT_BASE = "easy-push";
    private static final String[] SUFFIXES = {".properties", ".yml", ".yaml"};

    private final Properties config;

    private final Map<String, Object> configBeanCache = new ConcurrentHashMap<>();

    private ConfigFactory() {
        this.config = loadConfig();
    }

    private static class Holder {
        private static final ConfigFactory INSTANCE = new ConfigFactory();
    }

    public static ConfigFactory instance() {
        return Holder.INSTANCE;
    }

    private Properties loadConfig() {
        List<String> candidatePaths = buildCandidateList();
        for (String path : candidatePaths) {
            if (StringUtils.isBlank(path)) {
                continue;
            }
            try (InputStream is = getInputStream(path)) {
                if (is == null) {
                    continue;
                }
                Properties prop = new Properties();
                if (path.endsWith(".properties")) {
                    prop.load(is);
                } else if (path.endsWith(".yml") || path.endsWith(".yaml")) {
                    Map<String, Object> yamlMap = new Yaml().load(is);
                    if (yamlMap != null) {
                        prop.putAll(flattenYamlMap(yamlMap, null));
                    }
                }
                if (!prop.isEmpty()) {
                    log.info("Loaded configuration from: {}", path);
                    return prop;
                }
            } catch (Exception e) {
                log.warn("Failed to load config from {}: {}", path, e.getMessage());
            }
        }
        log.error("No configuration file found in candidate paths: {}", candidatePaths);
        throw ErrorCodeEnum.CONFIG_NULL.toException(EpConfigException.class);
    }

    private List<String> buildCandidateList() {
        List<String> list = new ArrayList<>();
        // 1. 环境变量指定优先
        String sysPath = System.getProperty(ENV_CONFIG_PATH);
        if (StringUtils.isNotBlank(sysPath)) {
            list.add(sysPath);
        }
        // 2. classpath 和 3. 当前目录
        for (String suffix : SUFFIXES) {
            // classpath
            list.add(DEFAULT_BASE + suffix);
            // 当前目录
            list.add("./" + DEFAULT_BASE + suffix);
        }
        return list;
    }

    /**
     * 文件系统与 classpath 加载
     */
    private InputStream getInputStream(String path) {
        // 先尝试文件系统
        try {
            if (Files.exists(Paths.get(path))) {
                return Files.newInputStream(Paths.get(path));
            }
        } catch (Exception ignore) {
        }
        // 再尝试 classpath
        try {
            return ConfigFactory.class.getClassLoader().getResourceAsStream(path.startsWith("./") ? path.substring(2) : path);
        } catch (Exception ignore) {
        }
        return null;
    }

    // YAML 扁平化
    private Map<String, String> flattenYamlMap(Map<String, Object> map, String parentKey) {
        Map<String, String> flat = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = parentKey == null ? entry.getKey() : parentKey + "." + entry.getKey();
            Object val = entry.getValue();
            if (val instanceof Map) {
                flat.putAll(flattenYamlMap((Map<String, Object>) val, key));
            } else if (val != null) {
                flat.put(key, val.toString());
            }
        }
        return flat;
    }


    public String getConfigValue(String prefix, String key) {
        return config.getProperty(prefix + key);
    }

    public String getConfigValue(String prefix, String key, String defValue) {
        String val = getConfigValue(prefix, key);
        return StringUtils.isBlank(val) ? defValue : val;
    }

    public Properties getProperties(String prefix) {
        Properties props = new Properties();
        for (String key : config.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                String pkey = key.substring(prefix.length());
                pkey = StringUtils.stripStart(pkey, ".");
                props.put(pkey, config.getProperty(key));
            }
        }
        return props;
    }

    public <T> T getConfigProperties(Class<T> clazz, String prefix) {
        String cacheKey = clazz.getName() + "::" + prefix;
        Object cached = configBeanCache.get(cacheKey);
        if (cached != null) {
            return (T) cached;
        }
        Properties properties = getProperties(prefix);
        try {
            T bean = ConfigUtils.populate(clazz, properties, prefix);
            configBeanCache.put(cacheKey, bean);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate properties for " + clazz.getName(), e);
        }
    }
}
