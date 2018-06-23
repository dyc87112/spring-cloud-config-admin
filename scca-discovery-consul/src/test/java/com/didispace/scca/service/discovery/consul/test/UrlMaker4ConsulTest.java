package com.didispace.scca.service.discovery.consul.test;

import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvRepo;
import com.didispace.scca.core.service.UrlMakerService;
import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UrlMaker4ConsulTest {

    @Autowired
    protected EnvRepo envRepo;
    @Autowired
    protected UrlMakerService urlMakerService;


    private static ConsulProcess consul;

    @BeforeClass
    public static void setup() {
        consul = ConsulStarterBuilder.consulStarter().build().start();

        System.setProperty("spring.cloud.consul.enabled", "true");
        System.setProperty("spring.cloud.consul.host", "localhost");
        System.setProperty("spring.cloud.consul.port", String.valueOf(consul.getHttpPort()));
    }

    @Test
    @Rollback
    public void testConsulUrlMaker() {
        // 新增一个环境数据
        Env stage = new Env();
        stage.setName("stage");
        stage.setConfigServerName("scca-repo");
        stage.setRegistryAddress("localhost:" + consul.getHttpPort());
//        stage.setRegistryAddress("localhost:8500");
        stage.setContextPath("/xxx");
        envRepo.save(stage);

        // 获取stage环境的配置中心URL
        String configServerUrl = urlMakerService.configServerBaseUrl("stage");
        log.info(configServerUrl);
        assertThat(configServerUrl).startsWith("http://");
        assertThat(configServerUrl).endsWith("10020/xxx");

        // 获取yh-consul-admin项目、stage环境、develop分支的配置抓取链接
        String propertiesUrl = urlMakerService.propertiesLoadUrl("scca-repo", "stage", "develop");
        log.info(propertiesUrl);
        assertThat(propertiesUrl).startsWith("http://");
        assertThat(propertiesUrl).endsWith("10020/xxx/scca-repo/stage/develop");
    }

}
