package com.didispace.scca.service.discovery.eureka.test;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.service.UrlMakerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UrlMaker4EurekaTest {

    @Autowired
    protected EnvRepo envRepo;
    @Autowired
    protected UrlMakerService urlMakerService;

    @Test
    @Rollback
    public void testEurekaUrlMaker() {
        // 新增一个环境数据
        Env stage = new Env();
        stage.setName("stage");
        stage.setConfigServerName("scca-repo");
        stage.setRegistryAddress("http://eureka.didispace.com/");
        stage.setContextPath("/yyy");
        envRepo.save(stage);

        // 获取stage环境的配置中心URL
        String configServerUrl = urlMakerService.configServerBaseUrl("stage");
        log.info(configServerUrl);
        assertThat(configServerUrl).startsWith("http://");
        assertThat(configServerUrl).endsWith("10020//yyy");

        // 获取yh-consul-admin项目、stage环境、develop分支的配置抓取链接
        String propertiesUrl = urlMakerService.propertiesLoadUrl("scca-repo", "stage", "develop");
        log.info(propertiesUrl);
        assertThat(propertiesUrl).startsWith("http://");
        assertThat(propertiesUrl).endsWith("10020//yyy/scca-repo/stage/develop");
    }

}
