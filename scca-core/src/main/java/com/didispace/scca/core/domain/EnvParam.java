package com.didispace.scca.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 环境属性
 *
 * @author 翟永超
 * @create 2018/4/24.
 * @blog http://blog.didispace.com
 */
@Data
@Entity
@NoArgsConstructor
public class EnvParam {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 配置key
     **/
    private String pKey;
    /**
     * 配置value
     **/
    private String pValue;

    /**
     * 所属环境
     **/
    @ManyToOne
    private Env env;

}
