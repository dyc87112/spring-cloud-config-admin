package com.didispace.scca.core.service;

import java.util.Properties;

/**
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface PropertiesService {

    /**
     * 根据一个配置模版，替换其中跟环境相关的部分
     *
     * @param template
     * @param envName
     * @return
     */
    Properties convertPropertiesForEnv(Properties template, String envName);

    /**
     * 用某个环境的配置中心，对某个配置集合加密
     *
     * @param template
     * @param envName
     * @return
     */
    Properties encrypt(Properties template, String envName);


}
