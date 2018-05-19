package org.copy.common.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.copy.common.domain.DictDO;
import org.copy.common.service.DictService;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("common:dict:dict")
    public String dict(){
        return "common/dict/dict";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:dict:dict")
    public PageUtils list(@RequestParam Map<String,Object> params){
        Query query = new Query(params);
        List<DictDO> dictDOList = dictService.list(query);
        int total = dictService.count(query);
        PageUtils pageUtils = new PageUtils(dictDOList, total);
        return pageUtils;
    }

    @ResponseBody
    @GetMapping("/type")
    public List<DictDO> type(){
        return dictService.listType();
    }

    @GetMapping("/add")
    @RequiresPermissions("common:dict:add")
    public String add() {
        return "common/dict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:dict:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        DictDO dict = dictService.get(id);
        model.addAttribute("dict", dict);
        return "common/dict/edit";
    }


}
