package com.didispace.scca.plugin.git;

import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Properties;

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
     * @param props
     * @param map
     * @param path
     */
    public static void yamlToProperties(Properties props, Map<String, Object> map, String path) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.hasLength(path))
                key = path + "." + key;
            Object val = entry.getValue();
            if (val instanceof Map) {
                yamlToProperties(props, (Map<String, Object>) val, key);
            } else {
                props.put(key, val.toString());
            }
        }
    }


}

