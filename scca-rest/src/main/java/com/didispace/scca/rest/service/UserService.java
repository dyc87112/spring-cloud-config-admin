package com.didispace.scca.rest.service;

import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * 批量查询用户信息
     */
    Page<UserDto> getUsers(Pageable pageable);

}
