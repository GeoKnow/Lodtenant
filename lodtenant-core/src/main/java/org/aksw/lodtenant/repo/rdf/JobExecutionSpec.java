package org.aksw.lodtenant.repo.rdf;

import org.aksw.jena_sparql_api.mapper.annotation.DefaultIri;
import org.aksw.jena_sparql_api.mapper.annotation.Iri;
import org.aksw.jena_sparql_api.mapper.annotation.IriType;
import org.aksw.jena_sparql_api.mapper.annotation.RdfType;

@RdfType("lodflow:JobExecution")
@DefaultIri("#{jobInstanceId}-exec")
public class JobExecutionSpec {
    @Iri("lodflow:executionOf")
    @IriType
    protected String jobInstanceId;

    @Iri("lodflow:executionId")
    protected Long executionId;

    public JobExecutionSpec() {

    }

    public JobExecutionSpec(String jobInstanceId, Long executionId) {
        super();
        this.jobInstanceId = jobInstanceId;
        this.executionId = executionId;
    }

    public String getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(String jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }

}
