package com.didispace.scca.rest.web;

import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.dto.UserDto;
import com.didispace.scca.rest.dto.UserParamDto;
import com.didispace.scca.rest.dto.base.WebResp;
import com.didispace.scca.rest.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Api("User（用户管理）")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/user")
public class UserController extends BaseController {

    @ApiOperation("Get User / 获取个人信息")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<UserDto> getUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        // 获取个人信息
        String username = principal.getUsername();
        UserDto userDto = userService.getUserByUsername(username);
        return WebResp.success(userDto);
    }

    @ApiOperation("Update User / 修改昵称")
    @RequestMapping(path = "/nickname", method = RequestMethod.PUT)
    public WebResp<String> updateNickname(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
                                          @RequestBody UserParamDto userParam) {
        // 修改个人昵称
        String username = principal.getUsername();
        User user = userService.getByUsername(username);
        user.setNickname(userParam.getNickname());
        userService.updateUserWithoutCheck(user);
        return WebResp.success("update nickname success");
    }

    @ApiOperation("Update User / 修改密码")
    @RequestMapping(path = "/password", method = RequestMethod.PUT)
    public WebResp<String> updatePassword(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
                                          @RequestBody UserParamDto userParam) {
        // 修改个人密码
        String username = principal.getUsername();
        User user = userService.getByUsername(username);
        boolean match = userService.matchPassword(userParam.getOldPwd(), user.getPassword());
        if (!match) {
            throw new ServiceException("密码错误");
        }
        user.setPassword(userParam.getNewPwd());
        userService.updateUser(user);
        return WebResp.success("update password success");
    }

}
