package org.copy.common.service;

import org.copy.common.config.Constant;
import org.copy.common.dao.TaskDao;
import org.copy.common.domain.ScheduleJob;
import org.copy.common.domain.TaskDO;
import org.copy.common.quartz.utils.QuartzManager;
import org.copy.common.utils.ScheduleJobUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    private TaskDao taskScheduleJobMapper;

    @Autowired
    QuartzManager quartzManager;

    public TaskDO get(Long id) {
        return taskScheduleJobMapper.get(id);
    }

    public List<TaskDO> list(Map<String, Object> map) {
        return taskScheduleJobMapper.list(map);
    }

    public int count(Map<String, Object> map) {
        return taskScheduleJobMapper.count(map);
    }

    public int save(TaskDO taskScheduleJob) {
        return taskScheduleJobMapper.save(taskScheduleJob);
    }

    public int update(TaskDO taskScheduleJob) {
        return taskScheduleJobMapper.update(taskScheduleJob);
    }

    public int remove(Long id) {
        try {
            TaskDO scheduleJob = get(id);
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            return taskScheduleJobMapper.remove(id);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int batchRemove(Long[] ids) {
        for (Long id : ids) {
            try {
                TaskDO scheduleJob = get(id);
                quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            } catch (SchedulerException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return taskScheduleJobMapper.batchRemove(ids);
    }

    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<TaskDO> jobList = taskScheduleJobMapper.list(new HashMap<>(16));
        for (TaskDO scheduleJob : jobList) {
            if ("1".equals(scheduleJob.getJobStatus())) {
                ScheduleJob job =ScheduleJobUtils.entityToData(scheduleJob);
                quartzManager.addJob(job);
            }
        }
    }

    public void changeSataus(Long jobId,String cmd) throws SchedulerException {
        TaskDO scheduleJob = get(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (Constant.STATUS_RUNNING_STOP.equals(cmd)) {
            quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
            scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
        } else {
            if (Constant.STATUS_RUNNING_START.equals(cmd)) {
            } else {
                scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
            }
        }
        update(scheduleJob);
    }

    public void updateCron(Long jobId) {
        TaskDO scheduleJob = get(jobId);
        if (scheduleJob == null) {
            return;
        }
        if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
            quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJob));
        }
        update(scheduleJob);
    }
}
