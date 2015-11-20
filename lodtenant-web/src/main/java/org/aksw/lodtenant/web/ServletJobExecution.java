package org.aksw.lodtenant.web;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.aksw.lodtenant.core.interfaces.JobManager;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

public class ServletJobExecution {
//    @Autowired
//    protected JobManager jobManager;
//
//    public void
//
//
//    @Path("{jobName}/{jobExecutionId}")
//    public void start(@PathParam("jobName") String jobName{
//
//        jobOperator.start(arg0, arg1)
//
//        jobExecution.
//
//
//        //batchConfig.jobExplorer().getJobInstances(null, 0, 0).get(0).get
//        //jobRepository.getLastJobExecution(jobName, jobParameters);
//    }
//
//    public void start(Long jobExecutionId) {
//        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
//        jobExecution.stop();
//    }
}
