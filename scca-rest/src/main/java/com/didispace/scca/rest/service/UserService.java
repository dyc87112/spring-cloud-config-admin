package com.didispace.scca.rest.service;

import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * 添加新用户
     */
    void createUser(User user);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 删除用户
     */
    void deleteUserByUsername(String username);

    /**
     * 查询用户信息
     */
    UserDto getUserByUsername(String username);

    /**
     * 获取用户
     */
    User getByUsername(String username);

    /**
     * 批量查询用户信息
     */
    List<UserDto> getUsers();

    /**
     * 校验密码和加密后的是否匹配
     */
    boolean matchPassword(String password, String encodedPassword);

}
