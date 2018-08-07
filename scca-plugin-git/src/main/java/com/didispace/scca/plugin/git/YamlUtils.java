package com.didispace.scca.plugin.git;

import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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
            } else {
                // TODO 对List的支持不对
                props.put(key, val.toString());
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
        Map<String, Object> map = new TreeMap<>();
        for (Object key : props.keySet()) {
            List<String> keyList = Arrays.asList(((String) key).split("\\."));
            Map<String, Object> valueMap = createTree(keyList, map);
            String value = props.getProperty((String) key);
            valueMap.put(keyList.get(keyList.size() - 1), value);
        }
        return map;
    }

    private static Map<String, Object> createTree(List<String> keys, Map<String, Object> map) {
        Map<String, Object> valueMap = (Map<String, Object>) map.get(keys.get(0));
        if (valueMap == null) {
            valueMap = new HashMap<>();
        }
        map.put(keys.get(0), valueMap);
        Map<String, Object> out = valueMap;
        if (keys.size() > 2) {
            out = createTree(keys.subList(1, keys.size()), valueMap);
        }
        return out;
    }

    public static void convertYamlString(StringBuffer sb, Map<String, Object> map, int count) {
        Set<String> set = map.keySet();
        for (Object key : set) {
            Object value = map.get(key);
            for (int i = 0; i < count; i++) {
                sb.append("    ");
            }
            if (value instanceof Map) {
                sb.append(key + ":\n");
                convertYamlString(sb, (Map) value, count + 1);
            } else if (value instanceof List) {
                for (Object obj : (List) value) {
                    for (int i = 0; i < count; i++) {
                        System.out.print("    ");
                        sb.append("    ");
                    }
                    sb.append("    - " + obj.toString() + "\n");
                }
            } else {
                sb.append(key + ": " + value + "\n");
            }
        }
    }

}

