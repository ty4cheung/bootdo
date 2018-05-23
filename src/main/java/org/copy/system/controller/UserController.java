package org.copy.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.copy.common.controller.BaseController;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.copy.system.domain.UserDO;
import org.copy.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    private static final String prefix = "/system/user";

    @Autowired
    UserService userService;

    @GetMapping("")
    @RequiresPermissions("sys:user:user")
    public String user(){
        return prefix+"/user";
    }

    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String,Object> params){
        Query query = new Query(params);
        List<UserDO> sysUserList = userService.list(query);
    }

}
