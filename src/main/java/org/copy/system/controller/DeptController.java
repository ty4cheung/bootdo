package org.copy.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.copy.common.controller.BaseController;
import org.copy.common.domain.Tree;
import org.copy.system.domain.DeptDO;
import org.copy.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {

    private static final String prefix = "system/dept";

    @Autowired
    private DeptService sysDeptService;

    @GetMapping()
    @RequiresPermissions("system:sysDept:sysDept")
    public String dept(){
        return prefix + "/dept";
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<DeptDO>();
        tree = sysDeptService.getTree();
        return tree;
    }
}
