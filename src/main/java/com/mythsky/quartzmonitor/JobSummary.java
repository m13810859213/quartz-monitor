package com.mythsky.quartzmonitor;

public class JobSummary {
    private int threads;
    private int executingJobs;

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getExecutingJobs() {
        return executingJobs;
    }

    public void setExecutingJobs(int executingJobs) {
        this.executingJobs = executingJobs;
    }
}
