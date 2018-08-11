package com.didispace.scca.rest.web;

import com.didispace.scca.rest.domain.User;
import com.didispace.scca.rest.dto.UserDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Anoyi on 2018/8/1.
 * <p>
 * Blog: https://anoyi.com/
 * Github: https://github.com/ChineseSilence
 */
@Api("Admin（管理员-用户管理）")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/admin")
//@Secured("hasRole('ADMIN')")
public class UserAdminController extends BaseController {

    @ApiOperation("Get User List / 获取用户列表")
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public WebResp<List<UserDto>> getUserList() {
        // 分页获取所有用户信息
        List<UserDto> users = userService.getUsers();
        return WebResp.success(users);
    }

    @ApiOperation("Save User / 添加新用户")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<String> saveUser(@RequestBody User user) {
        // 管理员添加新用户
        userService.createUser(user);
        return WebResp.success("save new user success");
    }

    @ApiOperation("Update User / 修改用户信息")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> updateUser(@RequestBody User user) {
        // 管理员修改用户信息
        userService.updateUser(user);
        return WebResp.success("update user success : " + user.getUsername());
    }

    @ApiOperation("Delete User / 删除用户")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> deleteUser(@RequestParam("username") String username) {
        // 管理员删除用户
        userService.deleteUserByUsername(username);
        return WebResp.success("save new user success");
    }

}
