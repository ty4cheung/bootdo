package org.copy.blog.controller;

import org.copy.blog.domain.ContentDO;
import org.copy.blog.service.ContentService;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    ContentService bContentService;

    @GetMapping()
    String blog(){
        return "/blog/index/main";
    }

    @ResponseBody
    @GetMapping("/open/list")
    public PageUtils opentList(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<ContentDO> bContentList = bContentService.list(query);
        int total = bContentService.count(query);
        PageUtils pageUtils = new PageUtils(bContentList, total);
        return pageUtils;
    }
}
