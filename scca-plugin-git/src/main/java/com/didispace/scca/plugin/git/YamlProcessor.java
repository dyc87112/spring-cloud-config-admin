package com.didispace.scca.plugin.git;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.LineBreak;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.resolver.Resolver;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author innerpeacez
 * @since 2018/12/5
 * 处理yaml格式
 */
public class YamlProcessor {

    private Yaml yaml;

    public YamlProcessor() {
        this.yaml = buildYaml();
    }

    protected Yaml buildYaml() {
        Constructor constructor = new Constructor();
        Representer representer = new Representer();
        representer.setDefaultFlowStyle(FlowStyle.BLOCK);
        representer.setDefaultScalarStyle(ScalarStyle.PLAIN);

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setAllowReadOnlyProperties(true);
        dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
        dumperOptions.setDefaultScalarStyle(ScalarStyle.PLAIN);
        dumperOptions.setLineBreak(this.getLineBreak());

        LimitedResolver limitedResolver = new LimitedResolver();
        return new Yaml(constructor, representer, dumperOptions, limitedResolver);
    }

    private LineBreak getLineBreak() {
        String os = System.getProperty("os.name").toLowerCase();
        LineBreak lineBreak = LineBreak.UNIX;
        if (os.contains("windows")) lineBreak = LineBreak.WIN;
        if (os.contains("mac")) lineBreak = LineBreak.MAC;
        return lineBreak;
    }

    public String dump(Object data) {
        return yaml.dump(resolveData(data));
    }

    private Object resolveData(Object data) {
        if (data instanceof List<?>) {
            return ((List<?>) data).stream().map(this::resolveData).collect(Collectors.toList());
        } else if (data instanceof Set<?>) {
            return ((Set<?>) data).stream().map(this::resolveData).collect(Collectors.toSet());
        } else if (data instanceof Map<?, ?>) {
            Map<String, Object> map = new LinkedHashMap<>();
            ((Map<?, ?>) data).forEach((k, v) -> map.put(k.toString(), this.resolveData(v)));
            return convertMap(map);

        } else if (data.getClass().isArray()) {
            return Arrays.stream((Object[]) data).map(this::resolveData).toArray();
        } else if (data instanceof CharSequence) {
            return data.toString();
        } else if (!data.getClass().getPackage().getName().startsWith("java.")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(data);
                return mapper.readValue(json, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    private Object convertMap(Map<String, Object> map) {
        Map<String, Object> toMap = new LinkedHashMap<>();
        map.forEach((key, value) -> {
            if (!StringUtils.hasText(key)) return;

            int index = key.indexOf(".");
            if (index < 0) {
                toMap.put(key, value);
            } else {
                String first = key.substring(0, index);
                String tail = key.substring(index + 1, key.length());
                Map<String, Object> valueMap = (Map<String, Object>) toMap.getOrDefault(first, new LinkedHashMap<>());
                valueMap.put(tail, value);
                toMap.put(first, convertMap(valueMap));
            }
        });
        return toMap;
    }

    private static class LimitedResolver extends Resolver {
        @Override
        public void addImplicitResolver(Tag tag, Pattern regexp, String first) {
            if (tag == Tag.TIMESTAMP) return;
            super.addImplicitResolver(tag, regexp, first);
        }
    }

}
