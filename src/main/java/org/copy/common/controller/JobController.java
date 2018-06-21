package org.copy.common.controller;

import org.copy.common.config.Constant;
import org.copy.common.domain.TaskDO;
import org.copy.common.service.JobService;
import org.copy.common.utils.PageUtils;
import org.copy.common.utils.Query;
import org.copy.common.utils.R;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhang_xiaotai
 */
@Controller
@RequestMapping("/common/job")
public class JobController extends BaseController {

    String prefix = "common/job";
    @Autowired
    JobService taskScheduleJobService;

    @GetMapping()
    String taskScheduleJob() {
        return prefix + "/job";
    }

    @GetMapping("/add")
    String add() {
        return prefix+"/add";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model){
        TaskDO job =  taskScheduleJobService.get(id);
        model.addAttribute("job",job);
        return prefix + "/edit";
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

    @ResponseBody
    @RequestMapping("/info/{id}")
    public R info(@PathVariable Long id){
        TaskDO taskDO = taskScheduleJobService.get(id);
        return R.ok().put("taskScheduleJob",taskDO);
    }

    @ResponseBody
    @PostMapping("/save")
    public R save(TaskDO taskScheduleJob) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1,"演示系统不允许修改,完整体验请部署程序");
        }
        if (taskScheduleJobService.save(taskScheduleJob) > 0){
            return R.ok();
        }
        return R.error();
    }

    @ResponseBody
    @PostMapping("/update")
    public R update(TaskDO taskScheduleJob) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1,"演示系统不允许修改,完整体验请部署程序");
        }
        if (taskScheduleJobService.update(taskScheduleJob) > 0) {
            return R.ok();
        }
        return R.error();

    }

    /**
     * 删除单个
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/remove")
    public R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (taskScheduleJobService.remove(id) > 0){
            return R.ok();
        }
        return R.error();
    }

    /**
     * 合并删除
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/batchRemove")
    public R remove(@RequestParam("ids[]")Long[] ids) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (taskScheduleJobService.batchRemove(ids) > 0){
            return R.ok();
        }
        return R.error();
    }

    @ResponseBody
    @PostMapping("/changeJobStatus")
    public R changeJobStatus(Long id,String cmd) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        String label = "停止";
        if ("start".equals(cmd)) {
            label = "启动";
        } else {
            label = "停止";
        }
        try {
            taskScheduleJobService.changeSataus(id, cmd);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return R.ok("任务" + label + "失败");
    }
}
