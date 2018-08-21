package com.mythsky.quartzmonitor;

public class ExecutorEntity {
    private   int activeCount      ;
    private   int poolSize         ;
    private   int maxPoolSize      ;
    private   int corePoolSize     ;
    private   int keepAliveSeconds ;
    private  int blockedSize;

    public int getBlockedSize() {
        return blockedSize;
    }

    public void setBlockedSize(int blockedSize) {
        this.blockedSize = blockedSize;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
