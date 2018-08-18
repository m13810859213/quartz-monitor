package com.mythsky.quartzmonitor;

import org.quartz.*;
import org.quartz.core.QuartzScheduler;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

@RestController
public class JobController {
    @GetMapping("/triggers")
    public List<JobEntity> getTriggers(){
        List<JobEntity> list=new ArrayList<>();
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            for(String group: sched.getTriggerGroupNames()) {
                for(TriggerKey triggerKey : sched.getTriggerKeys(groupEquals(group))) {
                    JobEntity entity=new JobEntity();
                    Trigger trigger=sched.getTrigger(triggerKey);
                    entity.setTriggerName(triggerKey.getName());
                    entity.setTriggerGroup(triggerKey.getGroup());
                    entity.setJobDescription(trigger.getDescription());
                    entity.setPriority(trigger.getPriority());
                    entity.setStartTime(trigger.getStartTime());
                    entity.setEndTime(trigger.getEndTime());
                    entity.setPreviousFireTime(trigger.getPreviousFireTime());
                    entity.setNextFireTime(trigger.getNextFireTime());
                    entity.setFinalFireTime(trigger.getFinalFireTime());
                    entity.setExecuteCount(((SimpleTriggerImpl) trigger).getTimesTriggered());

                    Trigger.TriggerState triggerState= sched.getTriggerState(triggerKey);
                    entity.setStatus(triggerState.name());

                    JobKey jobKey= trigger.getJobKey();
                    JobDetail jobDetail=sched.getJobDetail(jobKey);
                    entity.setJobName(jobKey.getName());
                    entity.setJobGroup(jobKey.getGroup());
                    entity.setJobDescription(jobDetail.getDescription());
                    entity.setClassName(jobDetail.getJobClass().getSimpleName());

                    list.add(entity);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }


    @GetMapping("/executing")
    public List<JobEntity> getExecutingJobs(){
        List<JobEntity> list=new ArrayList<>();
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            List<JobExecutionContext> jobs= sched.getCurrentlyExecutingJobs();
            for(JobExecutionContext context:jobs){
                JobEntity entity=new JobEntity();
                entity.setJobName(context.getJobDetail().getKey().getName());
                entity.setJobGroup(context.getJobDetail().getKey().getGroup());
                entity.setJobDescription(context.getJobDetail().getDescription());
                entity.setPreviousFireTime(context.getPreviousFireTime());
                entity.setNextFireTime(context.getNextFireTime());
                entity.setScheduledFireTime(context.getScheduledFireTime());
                entity.setJobRunTime(context.getJobRunTime());
                entity.setFireTime(context.getFireTime());
                entity.setRefireCount(context.getRefireCount());
                list.add(entity);
            }
            sched.getPausedTriggerGroups();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }
    @GetMapping("/startJobs")
    public String startJobs(){
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job1 = newJob(FirstJob.class)
                    .withIdentity("myJob1", "group1") // name "myJob", group "group1"
                    .withDescription("description1")
                    .build();
            Trigger trigger1 = newTrigger()
                    .withIdentity("myTrigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();
            sched.scheduleJob(job1, trigger1);

            JobDetail job2 = newJob(SecondJob.class)
                    .withIdentity("myJob2", "group2") // name "myJob", group "group1"
                    .withDescription("description2")
                    .build();
            Trigger trigger2 = newTrigger()
                    .withIdentity("myTrigger2", "group2")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();
            sched.scheduleJob(job2, trigger2);

            JobDetail job3 = newJob(ThirdJob.class)
                    .withIdentity("myJob3", "group3") // name "myJob", group "group1"
                    .withDescription("description3")
                    .build();
            Trigger trigger3 = newTrigger()
                    .withIdentity("myTrigger3", "group3")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();
            sched.scheduleJob(job3, trigger3);

            sched.start();
            return "success";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @PostMapping("/stopJob")
    public boolean stopJob(String name,String group){
        boolean result=false;
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            JobKey key=new JobKey(name,group);
            if(sched.checkExists(key)){
                sched.pauseJob(key);
                result=true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return result;
    }
    @PostMapping("/resumeJob")
    public boolean resumeJob(String name,String group){
        boolean result=false;
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            JobKey key=new JobKey(name,group);
            if(sched.checkExists(key)){
                sched.resumeJob(key);
                result=true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return result;
    }
    @PostMapping("/execJob")
    public boolean executeJob(String name,String group){
        boolean result=false;
        try {
            Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
            JobKey key=new JobKey(name,group);
            if(sched.checkExists(key)){
                sched.triggerJob(key);
                result=true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return result;
    }
}
