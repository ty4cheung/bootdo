package org.copy.common.quartz.utils;

import lombok.extern.slf4j.Slf4j;
import org.copy.common.domain.ScheduleJob;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    public void addJob(ScheduleJob schedulerJob){

    }
}
