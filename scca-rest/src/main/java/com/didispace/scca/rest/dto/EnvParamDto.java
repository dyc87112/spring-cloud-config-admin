package com.didispace.scca.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 环境的所属参数
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/6/16.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@NoArgsConstructor
public class EnvParamDto {

    private Long id;

    /**
     * 配置key
     **/
    private String pKey;
    /**
     * 配置value
     **/
    private String pValue;

}
