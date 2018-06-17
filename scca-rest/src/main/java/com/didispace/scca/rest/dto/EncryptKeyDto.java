package com.didispace.scca.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 需要加密的key
 * <p>
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@NoArgsConstructor
public class EncryptKeyDto {

    private Long id;

    /**
     * 需要加密的key
     **/
    @JsonProperty("eKey")
    private String eKey;

}
