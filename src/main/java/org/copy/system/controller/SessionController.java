package org.copy.system.controller;

import org.apache.shiro.session.Session;
import org.copy.common.utils.R;
import org.copy.system.domain.UserOnline;
import org.copy.system.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

@RequestMapping("/sys/online")
@Controller
public class SessionController {
    @Autowired
    SessionService sessionService;

    @GetMapping()
    public String online() {
        return "system/online/online";
    }

    @ResponseBody
    @RequestMapping("/list")
    public List<UserOnline> list() {
        return sessionService.list();
    }

    @ResponseBody
    @RequestMapping("/sessionList")
    public Collection<Session> sessionList(){
        return sessionService.sessionList();
    }

    @ResponseBody
    @RequestMapping("/forceLogout/{sessionId}")
    public R forceLogout(@PathVariable("sessionId")String sessionId, RedirectAttributes redirectAttributes) {
        try {
            sessionService.forceLogout(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok();
    }
}
