package com.didispace.scca.rest.service;

import com.didispace.scca.rest.domain.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LogsService {

    /**
     * 添加用户日志
     */
    void addUserLog(UserLog userLog);

    /**
     * 查询用户登录日志
     */
    Page<UserLog> getLoginLogs(String username, Pageable pageable);

}
