package org.copy.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.copy.common.controller.BaseController;
import org.copy.common.domain.FileDO;
import org.copy.common.domain.Tree;
import org.copy.common.service.FileService;
import org.copy.common.utils.MD5Utils;
import org.copy.common.utils.R;
import org.copy.system.domain.MenuDO;
import org.copy.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.copy.common.utils.ShiroUtils.getUser;
import static org.copy.common.utils.ShiroUtils.getUserId;

@Controller
@Slf4j
public class LoginController extends BaseController {
    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;

    @GetMapping({ "/", "" })
    String welcome(Model model) {

        return "redirect:/blog";
    }

    @GetMapping("/login")
    String login() {
        log.info("登录页面");
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password) {

        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @GetMapping({ "/index" })
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if(fileDO!=null&&fileDO.getUrl()!=null){
            if(fileService.isExist(fileDO.getUrl())){
                model.addAttribute("picUrl",fileDO.getUrl());
            }else {
                model.addAttribute("picUrl","/img/photo_s.jpg");
            }
        }else {
            model.addAttribute("picUrl","/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }
}
