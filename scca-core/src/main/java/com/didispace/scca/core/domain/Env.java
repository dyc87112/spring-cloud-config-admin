package com.didispace.scca.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private String name;
    /**
     * 注册中心地址
     **/
    private String registryAddress;
    /**
     * 配置中心的注册服务名
     **/
    private String configServerName;

    /**
     * 该环境下的配置内容
     **/
    @OneToMany(mappedBy = "environment")
    private List<EnvParam> envParams = new ArrayList<>();

}
