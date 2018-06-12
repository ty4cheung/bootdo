package org.copy.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.copy.common.annotation.Log;
import org.copy.common.config.Constant;
import org.copy.common.controller.BaseController;
import org.copy.common.domain.Tree;
import org.copy.common.utils.R;
import org.copy.system.domain.MenuDO;
import org.copy.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    String prefix = "system/menu";

    @Autowired
    MenuService menuService;

    @RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String menu(Model model){
        return prefix + "/menu";
    }

    @RequiresPermissions("sys:menu:menu")
    @RequestMapping("/list")
    @ResponseBody
    List<MenuDO> list(@RequestParam Map<String,Object> params){
        List<MenuDO> menuDOList = menuService.list(params);
        return menuDOList;
    }

    @Log("添加菜单")
    @RequiresPermissions("sys:menu:add")
    @GetMapping("/add/{pid}")
    String add(Model model, @PathVariable("pid")Long pid){
        model.addAttribute("pId",pid);
        if (pid == 0) {
            model.addAttribute("pName","根目录");
        } else {
            model.addAttribute("pName",menuService.get(pid).getName());
        }
        return prefix + "/add";
    }

    @Log("编辑菜单")
    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model,@PathVariable("id")Long id) {
        MenuDO menuDO = menuService.get(id);
        Long pid = menuDO.getParentId();
        model.addAttribute("pId",pid);
        if (pid == 0) {
            model.addAttribute("pName","根目录");
        } else {
            model.addAttribute("pName",menuService.get(pid).getName());
        }
        return prefix + "/edit";
    }

    @Log("保存菜单")
    @RequiresPermissions("sys:menu:add")
    @PostMapping("/save")
    @ResponseBody
    R save(MenuDO menu) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (menuService.save(menu) > 0) {
            return R.ok();
        } else {
            return R.error(1, "保存失败");
        }
    }

    @Log("更新菜单")
    @RequiresPermissions("sys:menu:edit")
    @PostMapping("/update")
    @ResponseBody
    R update(MenuDO menu) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (menuService.update(menu) > 0) {
            return R.ok();
        } else {
            return R.error(1, "更新失败");
        }
    }

    @Log("删除菜单")
    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    @ResponseBody
    R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (menuService.remove(id) > 0) {
            return R.ok();
        } else {
            return R.error(1, "删除失败");
        }
    }

    /**
     * 获取菜单列表
     * @return
     */
    @GetMapping("/tree")
    @ResponseBody
    Tree<MenuDO> tree() {
        Tree<MenuDO> tree = new Tree<MenuDO>();
        tree = menuService.getTree();
        return tree;
    }

    /**
     * 根据角色id获取菜单列表
     * @param roleId
     * @return
     */
    @GetMapping("/tree/{roleId}")
    @ResponseBody
    Tree<MenuDO> tree(@PathVariable("roleId") Long roleId) {
        Tree<MenuDO> tree = new Tree<MenuDO>();
        tree = menuService.getTree(roleId);
        return tree;
    }
}
