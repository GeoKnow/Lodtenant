package org.aksw.lodtenant.core.interfaces;

public interface JobManager {

    /**
     * Returns the id (should be a URI) under which the job was registered.
     *
     * @param jobSpec
     * @return
     */
    String registerJob(String jobSpec);

    /**
     * Returns the id of a job instance
     *
     * @param jobId
     * @param params
     * @return
     */
    String createJobInstance(String jobId, String params);


    String createJobExecution(String jobInstanceId);
}
