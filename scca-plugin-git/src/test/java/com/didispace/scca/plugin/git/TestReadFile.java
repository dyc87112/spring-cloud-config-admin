package com.didispace.scca.plugin.git;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/8/7.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public class TestReadFile {

    @Test
    public void testRead() throws Exception {
        String yamlStr =
                "com.didispace: hello yaml\n" +
                        "aaa: \n" +
                        "    aaa: 1\n" +
                        "    bbb: 2\n" +
                        "    ccc: 3\n" +
                        "bbb: \n" +
                        "    - 1\n" +
                        "    - 2\n";


        // YAML转Properties
        Properties props = YamlUtils.yamlToProperties(yamlStr);
        log.info(props.toString());

        log.info("\n============\n");

        // Properties转YAML
        Map<String, Object> map = YamlUtils.propertiesToYamlMap(props);
        StringBuffer sb = new StringBuffer();
        YamlUtils.convertYamlString(sb, map);
        System.out.println(sb.toString());
    }

}
