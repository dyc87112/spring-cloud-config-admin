package com.didispace.scca.service.persistence.db;

import com.didispace.scca.core.domain.*;
import com.didispace.scca.core.service.PersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public class DbPersistenceService implements PersistenceService {

    @Autowired
    private EnvRepo envRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private LabelRepo labelRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    @Override
    public Properties readProperties(String application, String profile, String label) {
        // 查询要保存配置的坐标
        Env e = envRepo.findByName(profile);
        Project p = projectRepo.findByName(application);
        Label l = labelRepo.findFirstByNameAndProject(label, p);

        Properties properties = new Properties();
        for (Property property : propertyRepo.findByEnvAndAndProjectAndLabel(e, p, l)) {
            properties.put(property.getPKey(), property.getPValue());
        }

        return properties;
    }

    @Override
    public void deletePropertiesByEnv(Env env) {
        // 删除某个环境下的所有配置
        int rows = propertyRepo.deleteAllByEnv(env);
        log.info("delete env [{}] property rows {}", env.getName(), rows);
    }

    @Override
    public void deletePropertiesByProject(Project project) {
        int rows = propertyRepo.deleteAllByProject(project);
        log.info("delete project [{}] property rows {}", project.getName(), rows);
    }

    @Override
    public void deletePropertiesByProjectAndEnv(Project project, Env env) {
        int rows = propertyRepo.deleteAllByProject_IdAndEnv_Id(project.getId(), env.getId());
        log.info("delete project [{}] in env [{}] property rows {}", project.getName(),
                env.getName(), rows);
    }

    @Override
    public void deletePropertiesByLabel(Label label) {
        int rows = propertyRepo.deleteAllByLabel(label);
        log.info("delete project [{}] label [{}] property rows {}",
                label.getProject().getName(), label.getName(), rows);
    }

    @Override
    @Transactional
    public void deleteProperties(String application, String profile, String label) {
        // 查询要保存配置的坐标
        Env e = envRepo.findByName(profile);
        Project p = projectRepo.findByName(application);
        Label l = labelRepo.findFirstByNameAndProject(label, p);

        // 1. 删除原来的配置
        int rows = propertyRepo.deleteAllByEnvAndAndProjectAndLabel(e, p, l);

        log.info("delete {}-{}-{} property rows {}", application, profile, label, rows);
    }

    @Override
    @Transactional
    public void saveProperties(String application, String profile, String label, Properties update) {
        // 查询要保存配置的坐标
        Env e = envRepo.findByName(profile);
        Project p = projectRepo.findByName(application);
        Label l = labelRepo.findFirstByNameAndProject(label, p);

        // 1. 删除原来的配置
        int rows = propertyRepo.deleteAllByEnvAndAndProjectAndLabel(e, p, l);

        log.info("delete {}-{}-{} property rows {}", application, profile, label, rows);

        // 2. 保存新的配置
        for (String name : update.stringPropertyNames()) {
            Property property = new Property();
            property.setPKey(name);
            property.setPValue(update.getProperty(name));
            property.setEnv(e);
            property.setProject(p);
            property.setLabel(l);
            propertyRepo.save(property);
        }

        log.info("add {}-{}-{} rows {}", application, profile, label, update.size());
    }

    @Override
    public void updateProfileName(String oldName, String newName) {
        // do nothing
    }

}
