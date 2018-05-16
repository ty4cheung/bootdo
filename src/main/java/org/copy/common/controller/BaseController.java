package org.copy.common.controller;

import org.copy.common.utils.ShiroUtils;
import org.copy.system.domain.UserDO;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    public UserDO getUser() {
        return ShiroUtils.getUser();
    }

    public Long getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }
}
