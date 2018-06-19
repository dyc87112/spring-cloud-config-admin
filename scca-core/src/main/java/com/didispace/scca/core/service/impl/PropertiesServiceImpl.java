package com.didispace.scca.core.service.impl;

import com.didispace.scca.core.domain.*;
import com.didispace.scca.core.service.BaseOptService;
import com.didispace.scca.core.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public class PropertiesServiceImpl implements PropertiesService {

    private final String encryptPlaceholder = "{cipher}";

    @Autowired
    private EnvRepo envRepo;
    @Autowired
    private EnvParamRepo envParamRepo;
    @Autowired
    private EncryptKeyRepo encryptKeyRepo;

    @Autowired
    private BaseOptService baseOptService;

    @Override
    public Properties encrypt(Properties template, String envName) {
        Properties result = new Properties();
        Set<String> encryptKeys = getEncryptKeys();
        Env environment = envRepo.findByName(envName);

        for (String key : template.stringPropertyNames()) {
            String value = template.getProperty(key);
            if (encryptKeys.contains(key)) {
                value = encryptPlaceholder + baseOptService.encrypt(value, environment);
            }
            result.put(key, value);
        }
        return result;
    }

    @Override
    public Properties convertPropertiesForEnv(Properties template, String envName) {
        Map<String, String> envParams = getEnvironmentParams(envName);
        Properties result = new Properties();

        for (String key : template.stringPropertyNames()) {
            String value = envParams.get(key);
            if (value == null) {
                value = template.getProperty(key);
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 获取某个环境下的敏感配置
     *
     * @param envName
     * @return
     */
    private Map<String, String> getEnvironmentParams(String envName) {
        Map<String, String> result = new HashMap<>();
        List<EnvParam> envParams = envParamRepo.findAllByEnv_Name(envName);
        for (EnvParam envParam : envParams) {
            result.put(envParam.getPKey(), envParam.getPValue());
        }
        return result;
    }

    /**
     * 获取所有需要加密的key
     *
     * @return
     */
    private Set<String> getEncryptKeys() {
        Set<String> result = new HashSet<>();
        List<EncryptKey> encryptKeys = encryptKeyRepo.findAll();
        for (EncryptKey encryptKey : encryptKeys) {
            result.add(encryptKey.getEKey());
        }
        return result;
    }

}
