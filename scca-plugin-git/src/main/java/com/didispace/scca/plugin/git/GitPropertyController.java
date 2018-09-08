package com.didispace.scca.plugin.git;

import com.didispace.easyutils.cmd.CmdRunner;
import com.didispace.easyutils.file.FileUtils;
import com.didispace.easyutils.file.PropertiesUtils;
import lombok.Cleanup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by 程序猿DD/翟永超 on 2018/8/2.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Slf4j
@RestController
@RequestMapping("${spring.cloud.config.server.prefix:}")
public class GitPropertyController {

    @Autowired
    private SccaGitProperties gitProperties;
    @Autowired
    private SccaGitPlusProperties gitPlusProperties;

    @RequestMapping(path = "/readProperties", method = RequestMethod.GET)
    public Properties readProperties(
            @RequestParam String application,
            @RequestParam String profile,
            @RequestParam String label) {
        // 访问Git上的存储的配置内容
        ProjectInfo projectInfo = new ProjectInfo(application, profile, label, this.gitProperties, this.gitPlusProperties);
        Properties properties = new Properties();
        try {
            // git clone properites from git
            CmdRunner.execute("git clone " + projectInfo.getProjectUrl() + " " + projectInfo.getDir());

            // git checkout branch(label)
            CmdRunner.execute("git checkout " + label, new File(projectInfo.getDir()));

            // load propertiesFile content
            for (int i = 0; i < projectInfo.getPath().size(); i++) {
                String path = projectInfo.getPath().get(i);
                if (new File(path).exists()) {
                    log.info("read properties : " + path);
                    if (path.endsWith(".properties")) {
                        properties = PropertiesUtils.loadProperties(path);
                    } else if (path.endsWith(".yaml") || path.endsWith(".yml")) {
                        properties = YamlUtils.yamlToProperties(new File(path));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteDirectory(new File(projectInfo.getDir()));
        }
        return properties;
    }

    @RequestMapping(path = "/deletePropertiesByProjectAndEnvAndLabel", method = RequestMethod.GET)
    public String deletePropertiesByProjectAndEnvAndLabel(@RequestParam String application,
                                                          @RequestParam String profile,
                                                          @RequestParam String label) {
        log.info("delete properties git : {}, {}, {}", application, profile, label);
        // 删除某个配置文件
        ProjectInfo projectInfo = new ProjectInfo(application, profile, label, this.gitProperties, this.gitPlusProperties);
        try {
            // git clone properites from git
            CmdRunner.execute("git clone " + projectInfo.getProjectUrl() + " " + projectInfo.getDir());

            // git checkout branch(label)
            CmdRunner.execute("git checkout " + label, new File(projectInfo.getDir()));

            // delete propertiesFile
            for (String path : projectInfo.getPath()) {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    log.info("delete file : " + file.getAbsolutePath());
                } else {
                    log.warn("delete file not exist: " + path);
                }
            }

            // commit & push
            CmdRunner.execute("git add .", new File(projectInfo.getDir()));
            CmdRunner.execute("git commit -m 'update'", new File(projectInfo.getDir()));
            CmdRunner.execute("git push", new File(projectInfo.getDir()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteDirectory(new File(projectInfo.getDir()));
        }
        return "SUCCESS";
    }

    @RequestMapping(path = "/saveProperties", method = RequestMethod.POST)
    public String saveProperties(@RequestParam String application,
                                 @RequestParam String profile,
                                 @RequestParam String label,
                                 @RequestBody Properties update) {
        ProjectInfo projectInfo = new ProjectInfo(application, profile, label, this.gitProperties, this.gitPlusProperties);
        try {
            // git clone properites from git
            CmdRunner.execute("git clone " + projectInfo.getProjectUrl() + " " + projectInfo.getDir());

            // git checkout branch(label)
            CmdRunner.execute("git checkout " + label, new File(projectInfo.getDir()));

            // 查询一下是否有匹配的文件
            String originPath = "";
            for (String path : projectInfo.getPath()) {
                if (new File(path).exists()) {
                    originPath = path;
                    break;
                }
            }

            if (originPath.isEmpty()) {
                // 文件不存在，按配置的第一种格式创建
                originPath = projectInfo.getPath().get(0);
                File file = new File(originPath);
                file.createNewFile();
            }

            if (originPath.endsWith(".properties")) {
                // read propertiesFile before upddate, write properties, read propertiesFile after update
                log.debug("---------------- properties before update ----------------");
                PropertiesUtils.printProperties(originPath, true);

                log.debug("---------------- properties after update ----------------");
                PropertiesUtils.printProperties(update, true);

                // store update properties
                PropertiesUtils.store(update, originPath, "Write by scca, more information see : https://github.com/dyc87112/spring-cloud-config-admin");
            } else if (originPath.endsWith(".yaml") || originPath.endsWith(".yml")) {
                // 把update内容写到originPath的YAML文件中
                Map<String, Object> map = YamlUtils.propertiesToYamlMap(update);
                StringBuffer sb = new StringBuffer();
                YamlUtils.convertYamlString(sb, map, 0);
                @Cleanup BufferedWriter out = new BufferedWriter(new FileWriter(originPath));
                out.write(sb.toString());
            }

            // commit & push
            CmdRunner.execute("git add .", new File(projectInfo.getDir()));
            CmdRunner.execute("git commit -m 'update'", new File(projectInfo.getDir()));
            CmdRunner.execute("git push", new File(projectInfo.getDir()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteDirectory(new File(projectInfo.getDir()));
        }
        return "SUCCESS";
    }

    /**
     * 用来处理Git配置项目相关的信息
     */
    @Data
    class ProjectInfo {
        /**
         * git clone的url
         */
        private String projectUrl;
        /**
         * 本地临时目录名
         */
        private String dir;
        /**
         * 要访问的文件相对路径
         */
        private List<String> path = new ArrayList<>();
        /**
         * 符合规则的文件列表
         */
        private List<String> propertiesFiles = new ArrayList<>();

        public ProjectInfo(String application, String profile, String label, SccaGitProperties gitProperties, SccaGitPlusProperties gitPlusProperties) {
            // 组织配置项目的git地址
            this.projectUrl = gitProperties.getRepoUri().replaceFirst("\\{application\\}", application);

            // projectUrl append username & password， git clone projectUrl
            this.projectUrl = this.projectUrl.replaceFirst("http://", "http://" + gitProperties.getUsername() + ":" + gitProperties.getPassword() + "@");
            log.info("project url : " + this.projectUrl);

            // 生成本地拉取配置用来修改使用的唯一目录名
            this.dir = UUID.randomUUID().toString();

            String searchPaths = gitProperties.getSearchPaths().replaceFirst("\\{application\\}", application);

            for (String pattern : gitPlusProperties.getFilePattern().split(",")) {
                String propertiesFile = pattern.replaceFirst("\\{application\\}", application).replaceFirst("\\{profile\\}", profile);
                this.propertiesFiles.add(propertiesFile);
                this.path.add(this.dir + searchPaths + "/" + propertiesFile);
            }
            log.info("properties file : " + this.path);
        }

    }

}
