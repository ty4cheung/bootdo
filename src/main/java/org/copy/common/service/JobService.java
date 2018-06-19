package org.copy.common.service;

import org.copy.common.dao.TaskDao;
import org.copy.common.domain.TaskDO;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    private TaskDao taskScheduleJobMapper;

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
}
