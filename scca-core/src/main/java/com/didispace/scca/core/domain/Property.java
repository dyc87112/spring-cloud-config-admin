package com.didispace.scca.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 详细配置表
 * <p>
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/6/20.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@Entity
@NoArgsConstructor
public class Property {

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
     */
    @OneToOne
    private Env env;

    /**
     * 所属项目
     */
    @OneToOne
    private Project project;

    /**
     * 所属版本
     */
    @OneToOne
    private Label label;

}
