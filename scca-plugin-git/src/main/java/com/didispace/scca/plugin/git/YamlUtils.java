package com.didispace.scca.plugin.git;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by 程序猿DD/翟永超 on 2018/8/7.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class YamlUtils {

    /**
     * Yaml转Properties格式
     *
     * @param yamlStr
     * @return
     */
    public static Properties yamlToProperties(String yamlStr) {
        Properties props = new Properties();
        YamlUtils.yamlToProperties(props, new Yaml().loadAs(yamlStr, Map.class), null);
        return props;
    }

    /**
     * Yaml转Properties格式
     *
     * @param file
     * @return
     */
    @SneakyThrows
    public static Properties yamlToProperties(File file) {
        Properties props = new Properties();
        YamlUtils.yamlToProperties(props, new Yaml().loadAs(new FileInputStream(file), Map.class), null);
        return props;
    }


    public static void yamlToProperties(Properties props, Map<String, Object> map, String path) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.hasLength(path))
                key = path + "." + key;
            Object val = entry.getValue();
            if (val instanceof Map) {
                yamlToProperties(props, (Map<String, Object>) val, key);
            } else if (val instanceof List) {
                int index = 0;
                for (Object v : (List) val) {
                    props.put(key + "[" + index + "]", v == null ? "" : v.toString());
                    index++;
                }
            } else {
                props.put(key, val == null ? "" : val.toString());
            }
        }
    }

    /**
     * Properties转Yaml格式
     *
     * @param props
     * @return
     */
    public static Map<String, Object> propertiesToYamlMap(Properties props) {
        return props.entrySet().stream().collect(Collectors.toMap(
                e -> String.valueOf(e.getKey()),
                e -> String.valueOf(e.getValue())
        ));
    }

    public static void convertYamlString(StringBuffer sb, Map<String, Object> map) {
        if (map != null) sb.append(new YamlProcessor().dump(map));
    }

}

