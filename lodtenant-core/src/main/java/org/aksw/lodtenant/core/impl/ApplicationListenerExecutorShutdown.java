package org.aksw.lodtenant.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

//@Component
public class ApplicationListenerExecutorShutdown
    implements ApplicationListener<ContextClosedEvent>{
    @Autowired(required=false)
    //proteExecutor executor
    protected ThreadPoolTaskExecutor executor;


    @Autowired(required=false)
    protected ThreadPoolTaskScheduler scheduler;

    public ThreadPoolTaskExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public ThreadPoolTaskScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }


    public void onApplicationEvent(ContextClosedEvent event) {
        if(scheduler != null) {
            scheduler.shutdown();
        }

        if(executor != null) {
            executor.shutdown();
        }
    }
}
