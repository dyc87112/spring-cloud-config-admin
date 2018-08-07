package com.didispace.scca.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserParamDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    @JsonProperty("nickname")
    private String nickname;

    /**
     * 旧密码
     */
    @JsonProperty("oldPwd")
    private String oldPwd;

    /**
     * 新密码
     */
    @JsonProperty("newPwd")
    private String newPwd;

}
