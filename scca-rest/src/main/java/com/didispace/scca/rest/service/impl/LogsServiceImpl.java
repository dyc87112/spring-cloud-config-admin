package com.didispace.scca.rest.service.impl;

import com.didispace.scca.rest.constant.UserLogTypeEnum;
import com.didispace.scca.rest.domain.UserLog;
import com.didispace.scca.rest.domain.UserLogRepo;
import com.didispace.scca.rest.service.LogsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Service
@AllArgsConstructor
public class LogsServiceImpl implements LogsService {

    private final UserLogRepo userLogRepo;

    @Override
    public void addUserLog(UserLog userLog) {
        userLogRepo.save(userLog);
    }

    @Override
    public List<UserLog> getLoginLogs(String username) {
        return userLogRepo.findTop10ByUsernameAndTypeOrderByIdDesc(username, UserLogTypeEnum.LOGIN.getCode());
    }

}