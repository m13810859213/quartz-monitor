package com.mythsky.quartzmonitor;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Random;

public class FirstJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello!  FirstJob is executing.");
//        Random random=new Random();
//        int i= random.nextInt(100);
//        if(i%2==0){
//            int x=0;
//            int y=0;
//            int z=x/y;
//            throw new JobExecutionException("error");
//        }
    }
}
