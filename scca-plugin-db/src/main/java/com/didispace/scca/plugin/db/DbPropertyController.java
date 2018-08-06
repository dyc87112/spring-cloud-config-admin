package com.didispace.scca.plugin.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * Created by 程序猿DD/翟永超 on 2018/8/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
@RestController
@RequestMapping("${spring.cloud.config.server.prefix:}")
public class DbPropertyController {

    @Autowired
    private PropertyRepo propertyRepo;

    @RequestMapping(path = "/readProperties", method = RequestMethod.GET)
    public Properties readProperties(
            @RequestParam String application,
            @RequestParam String profile,
            @RequestParam String label) {
        Properties properties = new Properties();
        for (Property property : propertyRepo.findAllByApplicationAndProfileAndLabel(application, profile, label)) {
            properties.put(property.getPKey(), property.getPValue());
        }
        return properties;
    }

    @Transactional
    @RequestMapping(path = "/deletePropertiesByEnv", method = RequestMethod.GET)
    public int deletePropertiesByEnv(@RequestParam String profile) {
        int rows = propertyRepo.deleteAllByProfile(profile);
        log.info("delete env [{}] property rows {}", profile, rows);
        return rows;
    }

    @Transactional
    @RequestMapping(path = "/deletePropertiesByProject", method = RequestMethod.GET)
    public int deletePropertiesByProject(@RequestParam String application) {
        int rows = propertyRepo.deleteAllByApplication(application);
        log.info("delete project [{}] property rows {}", application, rows);
        return rows;
    }

    @Transactional
    @RequestMapping(path = "/deletePropertiesByProjectAndEnv", method = RequestMethod.GET)
    public int deletePropertiesByProjectAndEnv(@RequestParam String application,
                                               @RequestParam String profile) {
        int rows = propertyRepo.deleteAllByApplicationAndProfile(application, profile);
        log.info("delete project [{}] in env [{}] property rows {}", application, profile, rows);
        return rows;
    }

    @Transactional
    @RequestMapping(path = "/deletePropertiesByLabel", method = RequestMethod.GET)
    public int deletePropertiesByLabel(@RequestParam String label) {
        // TODO
        int rows = 0;
//        int rows = propertyRepo.deleteAllByLabel(label);
        return rows;
    }

    @Transactional
    @RequestMapping(path = "/deletePropertiesByProjectAndEnvAndLabel", method = RequestMethod.GET)
    public int deletePropertiesByProjectAndEnvAndLabel(@RequestParam String application,
                                                       @RequestParam String profile,
                                                       @RequestParam String label) {
        int rows = propertyRepo.deleteAllByApplicationAndProfileAndLabel(application, profile, label);
        log.info("delete {}-{}-{} property rows {}", application, profile, label, rows);
        return rows;
    }

    @Transactional
    @RequestMapping(path = "/saveProperties", method = RequestMethod.POST)
    public int saveProperties(@RequestParam String application,
                              @RequestParam String profile,
                              @RequestParam String label,
                              @RequestBody Properties update) {
        // 1. 删除原来的配置
        int rows = propertyRepo.deleteAllByApplicationAndProfileAndLabel(application, profile, label);
        log.info("delete {}-{}-{} property rows {}", application, profile, label, rows);

        // 2. 保存新的配置
        for (String name : update.stringPropertyNames()) {
            Property property = new Property();
            property.setPKey(name);
            property.setPValue(update.getProperty(name));
            property.setProfile(profile);
            property.setApplication(application);
            property.setLabel(label);
            propertyRepo.save(property);
        }

        log.info("add {}-{}-{} rows {}", application, profile, label, update.size());
        return rows;
    }

    @Transactional
    @RequestMapping(path = "/updateProfileName", method = RequestMethod.GET)
    public int updateProfileName(@RequestParam String oldName,
                                 @RequestParam String newName) {
        int rows = propertyRepo.updateProfileName(newName, oldName);
        log.info("update profile name {} -> {}, change property rows {}", oldName, newName, rows);
        return rows;
    }

}
