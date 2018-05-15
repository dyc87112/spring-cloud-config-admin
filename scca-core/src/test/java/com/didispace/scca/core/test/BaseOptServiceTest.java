package com.didispace.scca.core.test;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.service.BaseOptService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseOptServiceTest {

    @Autowired
    private EnvRepo envRepo;

    @Autowired
    private BaseOptService baseOptService;

    @Test
    @Rollback
    @Transactional
    public void testEncryptAndDecrypt() {
        // 新增一个环境，使用默认的配置
        Env stage = new Env();
        stage.setName("stage");
        stage.setConfigServerName("http://localhost:10010");
        envRepo.save(stage);

        // 加密解密测试
        Env env = envRepo.findByName("stage");

        String originValue = "aaa";
        String encryptValue = baseOptService.encrypt(originValue, env);

        log.info("encrypt : {} -> {}", originValue, encryptValue);

        String decryptValue = baseOptService.decrypt(encryptValue, env);
        log.info("decrypt : {} -> {}", encryptValue, decryptValue);

        assertThat(originValue).isEqualTo(decryptValue);

        // 访问配置中心获取配置内容
        String serviceName = "scca-repo";

        Environment environment = baseOptService.getProperties(serviceName, "stage", "develop");
        for (PropertySource propertySource : environment.getPropertySources()) {
            log.info(propertySource.toString());
        }

        environment = baseOptService.getProperties(serviceName, "stage", null);
        for (PropertySource propertySource : environment.getPropertySources()) {
            log.info(propertySource.toString());
        }
    }

}
