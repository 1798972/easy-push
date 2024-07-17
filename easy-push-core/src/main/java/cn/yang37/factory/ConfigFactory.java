package cn.yang37.factory;

import cn.yang37.constant.ConfigConstant;
import cn.yang37.exception.ConfigException;
import cn.yang37.util.ConfigUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @description:
 * @class: ConfigFactory
 * @autho: yang37z@qq.com
 * @date: 2024/3/2 20:58
 * @version: 1.0
 */
public class ConfigFactory {

    private static final Logger log = LoggerFactory.getLogger(ConfigFactory.class);

    private volatile static ConfigFactory configFactory;

    public static final String DEFAULT_CONFIG_NAME = "easy-push";

    private final Properties configuration = init();

    private ConfigFactory() {
    }

    public static ConfigFactory instance() {
        if (configFactory == null) {
            synchronized (ConfigFactory.class) {
                if (configFactory == null) {
                    configFactory = new ConfigFactory();
                }
            }
        }
        return configFactory;
    }

    private static Properties init() {
        Properties config = new Properties();

        // 配置文件列表
        List<String> configList = initConfigNameList();
        int size = configList.size();

        // 查找文件
        for (int i = 0; i < size; i++) {
            String configPath = configList.get(i);
            log.debug("Attempting to load configuration from: {}", configPath);
            try {
                Properties tmpConfig = new Properties();
                InputStream inputStream = getConfigInputStream(configPath);
                if (inputStream != null) {
                    if (configPath.endsWith(ConfigConstant.PROPERTIES)) {
                        tmpConfig.load(inputStream);
                    } else if (configPath.endsWith(ConfigConstant.YML) || configPath.endsWith(ConfigConstant.YAML)) {
                        Yaml yaml = new Yaml();
                        Map<String, Object> yamlMap = yaml.load(inputStream);
                        tmpConfig.putAll(flattenYamlMap(yamlMap, null));
                    }
                    inputStream.close();
                }
                if (ObjectUtils.isNotEmpty(tmpConfig)) {
                    config = tmpConfig;
                    log.debug("Found the configuration file at: {}", configPath);
                    break;
                }
            } catch (Exception e) {
                log.error("Failed to load configuration from: {}", configPath, e);
            }
        }

        if (ObjectUtils.isEmpty(config)) {
            log.error("No configuration file found in the provided paths.");
            throw new ConfigException("Error initializing configuration file!");
        }

        return config;
    }

    private static InputStream getConfigInputStream(String configPath) {
        InputStream inputStream = ConfigFactory.class.getClassLoader().getResourceAsStream(configPath);
        if (inputStream == null) {
            try {
                inputStream = Files.newInputStream(Paths.get(configPath));
            } catch (Exception e) {
                log.debug("Failed to load configuration from filesystem path: {}", configPath, e);
            }
        }
        return inputStream;
    }

    private static List<String> initConfigNameList() {
        List<String> nameList = new LinkedList<>();

        final String classPath = "easy-push";
        final String currentDirectory = "./";

        nameList.add(classPath + ConfigConstant.PROPERTIES);
        nameList.add(classPath + ConfigConstant.YML);
        nameList.add(classPath + ConfigConstant.YAML);

        nameList.add(currentDirectory + ConfigFactory.DEFAULT_CONFIG_NAME + ConfigConstant.PROPERTIES);
        nameList.add(currentDirectory + ConfigFactory.DEFAULT_CONFIG_NAME + ConfigConstant.YML);
        nameList.add(currentDirectory + ConfigFactory.DEFAULT_CONFIG_NAME + ConfigConstant.YAML);

        return nameList;
    }

    private static Map<String, String> flattenYamlMap(Map<String, Object> map, String parentKey) {
        Map<String, String> flatMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = parentKey == null ? entry.getKey() : parentKey + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                flatMap.putAll(flattenYamlMap((Map<String, Object>) value, key));
            } else {
                flatMap.put(key, value.toString());
            }
        }
        return flatMap;
    }

    public String getDefaultConfigValue(String prefix, String key) {
        return instance().configuration.getProperty(prefix + key);
    }

    public String getDefaultConfigValue(String prefix, String key, String defValue) {
        String configValue = getDefaultConfigValue(prefix, key);
        return StringUtils.isAllEmpty(configValue) ? defValue : configValue;
    }

    public Properties getProperties(String prefix) {
        Properties props = new Properties();
        for (String key : configuration.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                String propertyKey = key.substring(prefix.length());
                propertyKey = StringUtils.stripStart(propertyKey, ".");
                props.put(propertyKey, configuration.getProperty(key));
            }
        }
        return props;
    }

    public <T> T getConfigProperties(Class<T> clazz, String prefix) {
        Properties properties = getProperties(prefix);
        try {
            return ConfigUtils.populate(clazz, properties, prefix);
        } catch (Exception e) {
            throw new RuntimeException("Failed to populate properties for " + clazz.getName(), e);
        }
    }
}
