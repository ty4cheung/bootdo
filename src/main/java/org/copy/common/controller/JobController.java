package org.copy.common.controller;

import org.copy.common.domain.TaskDO;
import org.copy.common.service.JobService;
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

/**
 * @author zhang_xiaotai
 */
@Controller
@RequestMapping("/common/job")
public class JobController extends BaseController {

    @Autowired
    JobService taskScheduleJobService;

    @GetMapping()
    String taskScheduleJob() {
        return "common/job/job";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<TaskDO> taskScheduleJobList = taskScheduleJobService.list(query);
        int total = taskScheduleJobService.count(query);
        PageUtils pageUtils = new PageUtils(taskScheduleJobList, total);
        return pageUtils;
    }
}
