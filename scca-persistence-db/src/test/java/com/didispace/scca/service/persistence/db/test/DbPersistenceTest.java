package com.didispace.scca.service.persistence.db.test;

import com.didispace.easyutils.file.OrderedProperties;
import com.didispace.scca.core.domain.*;
import com.didispace.scca.core.service.PersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbPersistenceTest {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private EnvRepo envRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private LabelRepo labelRepo;

    @Test
    public void testSaveProperties() {
        String application = "scca-repo";
        String profile = "stage";
        String version = "develop";

        Env env = new Env();
        env.setName(profile);
        env = envRepo.save(env);

        Project project = new Project();
        project.setName(application);
        project.getEnvs().add(env);
        project = projectRepo.save(project);

        Label label = new Label();
        label.setName(version);
        label.setProject(project);
        label = labelRepo.save(label);

        // 前端可以按顺序传上来，然后转换成这个OrderedProperties，就可以调用下面的写入并提交到Git的实现了
        Properties source = new OrderedProperties();

        source.put("spring.redis.host", "localhost");
        source.put("spring.redis.port", "6379");
        source.put("spring.redis.pool.max-wait", "1000");
        source.put("spring.redis.pool.min-idle", "8");
        source.put("spring.redis.pool.max-idle", "100");
        source.put("spring.redis.pool.max-active", "1000");

        source.put("xxx", "oooooooo");
        source.put("aaa", "mmmmmmmm");

        persistenceService.saveProperties(application, profile, version, source);
        log.info("save finish!");

        persistenceService.deleteProperties(application, profile, version);
        log.info("delete finish!");
        log.info("");
    }

    @Test
    public void testDeleteProperties() {
        // 先准备一个test分支
        String application = "scca-repo";
        String profile = "stage";
        String label = "test";
        persistenceService.deleteProperties(application, profile, label);
    }

}
