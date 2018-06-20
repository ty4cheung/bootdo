package org.copy.common.quartz.utils;

import lombok.extern.slf4j.Slf4j;
import org.copy.common.domain.ScheduleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    public void addJob(ScheduleJob job){
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(job.getBeanClass()).newInstance();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(),job.getJobGroup()).build();
            // 定义调度触发规则
            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())// 触发器key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
