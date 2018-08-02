package com.didispace.scca.service.persistence.git;

import com.didispace.scca.core.domain.*;
import com.didispace.scca.core.service.PersistenceService;
import com.didispace.scca.core.service.UrlMakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
public class GitPersistenceService implements PersistenceService {

    @Autowired
    private EnvRepo envRepo;
    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UrlMakerService urlMakerService;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Properties readProperties(String application, String profile, String label) {
        String url = urlMakerService.configServerBaseUrl(profile) + "/readProperties?application={1}&profile={2}&label={3}";
        Properties properties = restTemplate.getForObject(url, Properties.class, application, profile, label);
        return properties;
    }

    @Override
    public void deletePropertiesByEnv(String profile) {
        Env env = envRepo.findByName(profile);
        for (Project project : env.getProjects()) {
            for (Label label : project.getLabels()) {
                // 删除单个配置文件
                deletePropertiesByProjectAndEnvAndLabel(project.getName(), env.getName(), label.getName());
            }
        }
    }

    @Override
    public void deletePropertiesByProject(String application) {
        Project project = projectRepo.findByName(application);
        for (Env env : project.getEnvs()) {
            for (Label label : project.getLabels()) {
                // 删除单个配置文件
                deletePropertiesByProjectAndEnvAndLabel(project.getName(), env.getName(), label.getName());
            }
        }
    }

    @Override
    public void deletePropertiesByProjectAndEnv(String application, String profile) {
        Project project = projectRepo.findByName(application);
        for (Label label : project.getLabels()) {
            deletePropertiesByProjectAndEnvAndLabel(project.getName(), profile, label.getName());
        }
    }

    @Override
    public void deletePropertiesByLabel(Label label) {
        String projectName = label.getProject().getName();
        for (Env env : label.getProject().getEnvs()) {
            // 删除单个配置文件
            deletePropertiesByProjectAndEnvAndLabel(projectName, env.getName(), label.getName());
        }
    }

    @Override
    public void deletePropertiesByProjectAndEnvAndLabel(String application, String profile, String label) {
        String url = urlMakerService.configServerBaseUrl(profile) + "/deletePropertiesByProjectAndEnvAndLabel?application={1}&profile={2}&label={3}";
        String result = restTemplate.getForObject(url, String.class, application, profile, label);
        log.info("delete {}-{}-{} property {}", application, profile, label, result);
    }


    @Override
    public void saveProperties(String application, String profile, String label, Properties update) {
        String url = urlMakerService.configServerBaseUrl(profile) + "/saveProperties?application={1}&profile={2}&label={3}";
        Integer rows = restTemplate.postForObject(url, update, Integer.class, application, profile, label);
        log.info("add {}-{}-{} rows {}", application, profile, label, rows);
    }

    @Override
    public void updateProfileName(String oldName, String newName) {
        // TODO 管理端修改环境名的时候，git存储需求修改存储的配置文件位置或者文件名

    }
}
