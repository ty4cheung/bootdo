package org.copy.common.listenner;

import lombok.extern.slf4j.Slf4j;
import org.copy.common.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@Slf4j
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    JobService scheduleJobService;

    @Override
    public void run(String... strings) throws Exception {
        try {
            log.info("ScheduleJobInitListener .. run");
            scheduleJobService.initSchedule();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
