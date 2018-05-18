package org.copy.common.controller;

import org.copy.common.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping()
    String dict(){
        return "common/dict/dict";
    }
}
