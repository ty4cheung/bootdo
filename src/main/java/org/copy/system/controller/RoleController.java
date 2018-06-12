package org.copy.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.copy.common.annotation.Log;
import org.copy.common.controller.BaseController;
import org.copy.system.domain.RoleDO;
import org.copy.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/sys/role")
@Controller
public class RoleController extends BaseController {
    String prefix = "system/role";

    @Autowired
    RoleService roleService;

    @RequiresPermissions("sys:role:role")
    @GetMapping("")
    String role(){
        return prefix + "/role";
    }

    @Log("添加角色")
    @GetMapping("/add")
    @RequiresPermissions("sys:role:add")
    String add(){
        return prefix+"/add";
    }

    @Log("编辑角色")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("sys:role:edit")
    String edit(@PathVariable("id")Long id, Model model){
        RoleDO roleDO = roleService.get(id);
        model.addAttribute("role",roleDO);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:role:role")
    @GetMapping("/list")
    @ResponseBody()
    List<RoleDO> list() {
        List<RoleDO> roles = roleService.list();
        return roles;
    }

}
