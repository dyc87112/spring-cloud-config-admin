package com.didispace.scca.core.service;

import java.util.Properties;

/**
 * 配置内容的持久化操作
 * <p>
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
public interface PersistenceService {

    /**
     * 更新某个配置文件
     *
     * @param name    - 通过访问配置中心返回的配置名称
     * @param profile - 环境名称
     * @param label   - 分支名称
     * @param update  - 更新的全量配置内容
     */
    void updateProperties(String name, String profile, String label, Properties update);

    /**
     * 获取某个应用代码库中的配置模版
     *
     * @param applicationName
     * @return
     */
    Properties readPropertiesTemplate(String applicationName);

}
