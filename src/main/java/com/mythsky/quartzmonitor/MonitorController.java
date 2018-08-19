package com.mythsky.quartzmonitor;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

@Controller
public class MonitorController {
    @GetMapping("/monitor")
    public String index(){
        return "index";
    }
    @GetMapping("/show")
    public String show(){
        return "show";
    }
}
