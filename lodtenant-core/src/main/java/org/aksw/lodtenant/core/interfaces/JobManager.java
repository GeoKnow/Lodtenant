package org.aksw.lodtenant.core.interfaces;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

public interface JobManager {

    /**
     * Returns the id (should be a URI) under which the job was registered.
     *
     * @param jobId
     * @return
     */
    String registerJob(String jobId);

    /**
     * Returns the id of a job instance
     *
     * @param jobId
     * @param params
     * @return
     */
    String createJobInstance(String jobId, String params);


    String createJobExecution(String jobInstanceId);

    Job getJob(String jobId);
    JobInstance getJobInstance(String instanceId);
    JobExecution getJobExecution(String executionId);
}
