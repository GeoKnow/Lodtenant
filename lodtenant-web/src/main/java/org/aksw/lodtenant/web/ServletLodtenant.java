package org.aksw.lodtenant.web;

import org.aksw.jena_sparql_api.mapper.jpa.EntityManagerJena;

import com.google.gson.JsonElement;




//@Path("/api")
public class ServletLodtenant {

    protected EntityManagerJena entityManager;


    public void register(JsonElement workflowJson) {

    }

    /**
     * Create an execution and return its id
     *
     * @param workflowId
     * @return
     */
    public String createJobExecution(String jobId, String params) {
        return null;
    }

    /*
     * Life Cycle Management of job executions
     */
    public String launch(String jobExecutionId) {
        return null;
    }

    public String pause(String jobExecutionId) {
        return null;
    }

    public String resume(String jobExecutionId) {
        return null;
    }

    public String abort(String jobExecutionId) {
        return null;
    }

}
