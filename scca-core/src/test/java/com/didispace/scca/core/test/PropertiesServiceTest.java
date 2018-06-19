package com.didispace.scca.core.test;

import com.didispace.easyutils.file.PropertiesUtils;
import com.didispace.scca.core.domain.*;
import com.didispace.scca.core.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PropertiesServiceTest {

    @Autowired
    protected EnvRepo envRepo;
    @Autowired
    protected EnvParamRepo envParamRepo;
    @Autowired
    protected EncryptKeyRepo encryptKeyRepo;

    @Autowired
    protected PropertiesService propertiesService;

    @Test
    @Rollback
    @Transactional
    public void testEnvironmentPropertiesAndEncrypt() {
        // 新增一个环境，使用默认的配置
        Env stage = new Env();
        stage.setName("stage");
        stage.setConfigServerName("http://localhost:10010");
        envRepo.save(stage);

        // 为stage环境新增两个参数配置
        EnvParam envParam;

        envParam = new EnvParam();
        envParam.setEnv(stage);
        envParam.setPKey("spring.redis.host");
        envParam.setPValue("2.2.2.2");
        envParamRepo.save(envParam);

        envParam = new EnvParam();
        envParam.setEnv(stage);
        envParam.setPKey("jdbc.password");
        envParam.setPValue("abc123");
        envParamRepo.save(envParam);

        // 设置需要加密的key
        EncryptKey encryptKey = new EncryptKey();
        encryptKey.setEKey("jdbc.password");
        encryptKeyRepo.save(encryptKey);


        // 根据某个模版 + 环境标示获得对应的配置
        Properties template = new Properties();
        template.setProperty("spring.redis.host", "1.1.1.1");
        template.setProperty("jdbc.username", "aaaaaa");
        template.setProperty("jdbc.password", "bbbbbb");

        String env = "stage";

        // 根据环境转换
        Properties stageProperties = propertiesService.convertPropertiesForEnv(template, env);
        PropertiesUtils.printProperties(stageProperties);
        assertThat(stageProperties.getProperty("jdbc.password")).doesNotContain("{cipher}");
        assertThat(stageProperties.getProperty("jdbc.password")).isEqualTo("abc123");
        assertThat(stageProperties.getProperty("spring.redis.host")).isEqualTo("2.2.2.2");

        // 加密
        Properties stageEncryptProperties = propertiesService.encrypt(stageProperties, env);
        PropertiesUtils.printProperties(stageEncryptProperties);
        assertThat(stageEncryptProperties.getProperty("jdbc.password")).contains("{cipher}");
        assertThat(stageEncryptProperties.getProperty("spring.redis.host")).isEqualTo("2.2.2.2");
    }

}
