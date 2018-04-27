package com.didispace.scca.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 需要加密的key
 *
 * Created by 程序猿DD/翟永超 on 2018/4/24.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Data
@Entity
@NoArgsConstructor
public class EncryptKey {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 需要加密的key
     **/
    private String eKey;

}
