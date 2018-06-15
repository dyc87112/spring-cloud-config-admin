package com.didispace.scca.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 标签（灰度配置）
 *
 * @author 翟永超
 * @create 2018/4/24.
 * @blog http://blog.didispace.com
 */
@Data
@Entity
@NoArgsConstructor
public class Label {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 标签名
     **/
    private String name;

    @ManyToOne
    private Project project;

}
