package org.copy.oa.controller;

import org.copy.common.config.Constant;
import org.copy.common.controller.BaseController;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.copy.oa.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wykj-4
 */
@Controller
@RequestMapping("/oa/notify")
public class NotifyController extends BaseController {

    @Autowired
    NotifyService notifyService;

    @ResponseBody
    @GetMapping("/message")
    PageUtils message() {
        Map<String, Object> params = new HashMap<>(16);
        params.put("offset", 0);
        params.put("limit", 3);
        Query query = new Query(params);
        query.put("userId", getUserId());
        query.put("isRead",Constant.OA_NOTIFY_READ_NO);
        return notifyService.selfList(query);
    }

}
