package com.didispace.scca.core.service;

import com.didispace.scca.core.domain.Env;
import org.springframework.cloud.config.environment.Environment;

import java.util.List;

/**
 * 与Spring Cloud Config Server的交互操作
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface BaseOptService {

    /**
     * 在某个环境加密
     *
     * @param originValue
     * @param env
     * @return
     */
    String encrypt(String originValue, Env env);

    /**
     * 在某个环境解密
     *
     * @param originValue
     * @param env
     * @return
     */
    String decrypt(String originValue, Env env);

    /**
     * 获取某个环境下配置中心的详细信息
     *
     * @param env
     * @return
     */
    List<ConfigServerInfo> configServerInfo(Env env);

    /**
     * 访问配置中心获取某个应用、某个环境、某个分支的配置
     *
     * @param application
     * @param envName
     * @param label
     * @return
     */
    Environment getProperties(String application, String envName, String label);

}
