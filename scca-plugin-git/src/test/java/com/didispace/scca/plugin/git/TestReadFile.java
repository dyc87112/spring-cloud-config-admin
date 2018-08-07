package com.didispace.scca.plugin.git;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by 程序猿DD/翟永超 on 2018/8/7.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public class TestReadFile {

    @Test
    public void testRead() {
        String yamlStr =
                "com.didispace: hello yaml\n" +
                        "aaa: \n" +
                        "    aaa: 1\n" +
                        "    bbb: 2\n" +
                        "    ccc: 3\n" +
                        "bbb: \n" +
                        "    - 1\n" +
                        "    - 2\n";
        Yaml yaml = new Yaml();
        Map<String, Object> ret = yaml.loadAs(yamlStr, Map.class);
        Properties props = new Properties();
        YamlUtils.yamlToProperties(props, ret, null);
        log.info(props.toString());
    }


    private void printMap(Map<String, Object> map, int count) {
        Set<String> set = map.keySet();
        for (Object key : set) {
            Object value = map.get(key);

            for (int i = 0; i < count; i++) {
                System.out.print("    ");
            }

            if (value instanceof Map) {
                System.out.println(key + ":");
                printMap((Map) value, count + 1);
            } else if (value instanceof List) {
                System.out.println(key + ":");
                for (Object obj : (List) value) {
                    for (int i = 0; i < count; i++) {
                        System.out.print("    ");
                    }
                    System.out.println("    - " + obj.toString());
                }
            } else {
                System.out.println(key + ": " + value);
            }
        }
    }


}
