package com.didispace.scca.core.service;

import java.util.List;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface UrlMakerService {

    /**
     * 组织URL：获取某个环境可以访问的配置中心
     *
     * @param envName
     * @return
     */
    String configServerBaseUrl(String envName);

    /**
     * 获取某个环境下所有可以访问的配置中心基础地址清单
     *
     * @param envName
     * @return
     */
    List<String> allConfigServerBaseUrl(String envName);

    /**
     * 组织URL：获取某个应用、某个环境、某个分支的配置
     * <p>
     * http://config-server/{application}/{envName}/{label}
     *
     * @param application
     * @param envName
     * @param label
     * @return
     */
    String propertiesLoadUrl(String application, String envName, String label);

}
