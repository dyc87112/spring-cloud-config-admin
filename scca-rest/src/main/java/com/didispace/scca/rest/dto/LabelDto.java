package com.didispace.scca.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签（灰度配置）
 *
 * @author 翟永超
 * @create 2018/4/24.
 * @blog http://blog.didispace.com
 */
@Data
@NoArgsConstructor
public class LabelDto {

    private Long id;

    /**
     * 标签名
     **/
    private String name;

}
