package org.copy.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.copy.common.annotation.Log;
import org.copy.common.config.Constant;
import org.copy.common.controller.BaseController;
import org.copy.common.domain.Tree;
import org.copy.common.service.DictService;
import org.copy.common.utils.MD5Utils;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.copy.common.utils.R;
import org.copy.system.domain.DeptDO;
import org.copy.system.domain.RoleDO;
import org.copy.system.domain.UserDO;
import org.copy.system.service.RoleService;
import org.copy.system.service.UserService;
import org.copy.system.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    private static final String prefix = "/system/user";

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    DictService dictService;

    @GetMapping("")
    @RequiresPermissions("sys:user:user")
    public String user(){
        return prefix+"/user";
    }

    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String,Object> params){
        Query query = new Query(params);
        List<UserDO> sysUserList = userService.list(query);
        int total = userService.count(params);
        PageUtils pageUtils = new PageUtils(sysUserList,total);
        return pageUtils;
    }

    @RequiresPermissions("sys:user:add")
    @GetMapping("/add")
    public String add(Model model){
        List<RoleDO> roles = roleService.list();
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        UserDO userDO = userService.get(id);
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.list(id);
        model.addAttribute("roles", roles);
        return prefix+"/edit";
    }

    @Log("保存用户")
    @RequiresPermissions("sys:user:add")
    @PostMapping("/save")
    @ResponseBody
    public R save(UserDO user) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        if (userService.save(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/update")
    @ResponseBody
    public R update(UserDO user) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.update(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/updatePersonal")
    @ResponseBody
    public R updatePersonal(UserDO userDO){
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(-1,"演示系统不允许修改，完整体验请部署程序");
        }
        if (userService.updatePersonal(userDO) > 0){
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:remove")
    @Log("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    public R batchRemove(@RequestParam("ids[]") Long[] userIds){
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (userService.batchRemove(userIds) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/exit")
    @ResponseBody
    public boolean exit(@RequestParam Map<String,Object> params){
        // 存在，不通过，false
        return !userService.exit(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("请求更改用户密码")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id")Long userId,Model model){
        UserDO userDO =new UserDO();
        userDO.setUserId(userId);
        model.addAttribute("user",userDO);
        return prefix + "/reset_pwd";
    }

    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    R resetPwd(UserVO userVO){
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        try {
            userService.resetPwd(userVO,getUser());
            return R.ok();
        } catch (Exception e) {
            return R.error(1,e.getMessage());
        }
    }

    @Log("admin提交更改用户密码")
    @RequiresPermissions("sys:user:resetPwd")
    @PostMapping("/adminResetPwd")
    @ResponseBody
    R adminResetPwd(UserVO userVO){
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        try{
            userService.adminResetPwd(userVO);
            return R.ok();
        }catch (Exception e){
            return R.error(1,e.getMessage());
        }

    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree(){
        Tree<DeptDO> tree = new Tree<>();
        tree = userService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView(){
        return prefix + "/userTree";
    }

    @GetMapping("/personal")
    String personal(Model model) {
        UserDO userDO = userService.get(getUserId());
        model.addAttribute("user",userDO);
        model.addAttribute("hobbyList",dictService.getHobbyList(userDO));
        model.addAttribute("sexList",dictService.getSexList());
        return prefix + "/personal";
    }

    @ResponseBody
    @PostMapping("/uploadImg")
    R uploadImg(@RequestParam("avatar_file")MultipartFile file,String avatar_data) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        Map<String,Object> result = new HashMap<>();
        try {
            result = userService.updatePersonalImg(file,avatar_data,getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            R.error("更新图像失败!");
        }
        if (result != null && result.size() > 0){
            return R.ok(result);
        }
        return R.error("更新图像失败！");
    }
}
