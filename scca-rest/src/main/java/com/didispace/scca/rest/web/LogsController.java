package com.didispace.scca.rest.web;

import com.didispace.scca.rest.domain.UserLog;
import com.didispace.scca.rest.dto.base.WebResp;
import com.didispace.scca.rest.service.LogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Anoyi on 2018/8/14.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Api("Logs（日志查询）")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("${scca.rest.context-path:}/logs")
public class LogsController extends BaseController {

    private final LogsService logsService;

    @ApiOperation("Get User Login Logs / 获取用户登录日志")
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public WebResp<List<UserLog>> getUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        // 获取个人信息
        String username = principal.getUsername();
        List<UserLog> logs = logsService.getLoginLogs(username);

        return WebResp.success(logs);
    }

}
