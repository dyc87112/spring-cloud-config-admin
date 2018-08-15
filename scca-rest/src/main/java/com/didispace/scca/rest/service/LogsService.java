package com.didispace.scca.rest.service;

import com.didispace.scca.rest.domain.UserLog;

import java.util.List;

public interface LogsService {

    /**
     * 添加用户日志
     */
    void addUserLog(UserLog userLog);

    /**
     * 查询用户登录日志
     */
    List<UserLog> getLoginLogs(String username);

}
