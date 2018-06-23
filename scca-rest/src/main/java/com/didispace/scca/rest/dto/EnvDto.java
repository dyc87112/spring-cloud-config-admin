package com.didispace.scca.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 环境的基础属性
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/5/16.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@NoArgsConstructor
public class EnvDto {

    private Long id;

    /**
     * 环境名
     **/
    private String name;

    /**
     * 注册中心地址，如果为空，配置中心的访问地址不使用服务名
     **/
    @JsonProperty("registryAddress")
    private String registryAddress;

    /**
     * 配置中心的访问地址（比如：http://localhost:8888/）或服务名（配置服务名的时候需要与服务发现组件配合使用）
     **/
    @JsonProperty("configServerName")
    private String configServerName;

    /**
     * 如果配置中心设置了contextPath，那么也需要维护进来
     */
    @JsonProperty("contextPath")
    private String contextPath;

}
