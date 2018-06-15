package org.copy.common.controller;

import org.copy.common.domain.LogDO;
import org.copy.common.domain.PageDO;
import org.copy.common.service.LogService;
import org.copy.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/common/log")
@Controller
public class LogController {
    @Autowired
    LogService logService;
    String prefix = "common/log";

    @GetMapping()
    public String log() {
        return prefix + "/log";
    }

    @ResponseBody
    @GetMapping("/list")
    PageDO<LogDO> list(@RequestParam Map<String,Object> params) {
        Query query = new Query(params);
        PageDO<LogDO> page = logService.queryList(query);
        return page;
    }
}
