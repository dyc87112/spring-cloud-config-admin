package com.didispace.scca.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 环境
 *
 * @author 翟永超
 * @create 2018/4/24.
 * @blog http://blog.didispace.com
 */
@Data
@Entity
@NoArgsConstructor
public class Env {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 环境名
     **/
    @Column(unique = true)
    private String name;
    /**
     * 注册中心地址，如果为空，配置中心的访问地址不使用服务名
     **/
    private String registryAddress;
    /**
     * 配置中心的访问地址（比如：http://localhost:8888/）或服务名（配置服务名的时候需要与服务发现组件配合使用）
     **/
    private String configServerName;
    /**
     * 如果配置中心设置了contextPath，那么也需要维护进来
     */
    private String contextPath = "";

    /**
     * 该环境下的配置内容
     **/
    @OneToMany(mappedBy = "env", cascade = CascadeType.REMOVE)
    private List<EnvParam> envParams = new ArrayList<>();

    /**
     * 该环境下的所有项目
     */
    @ManyToMany(mappedBy = "envs")
    private List<Project> projects = new ArrayList<>();

}
