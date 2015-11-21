package org.aksw.lodtenant.repo.rdf;

import org.aksw.jena_sparql_api.mapper.annotation.DefaultIri;
import org.aksw.jena_sparql_api.mapper.annotation.Iri;
import org.aksw.jena_sparql_api.mapper.annotation.IriType;
import org.aksw.jena_sparql_api.mapper.annotation.RdfType;


@RdfType("lodflow:JobInstance")
@DefaultIri("#{jobId}-instance-#{#md5(params)}")
public class JobInstanceSpec {

    @Iri("lodflow:instanceOf")
    @IriType
    protected String jobId;

    @Iri("lodflow:params")
    protected String params;

    @Iri("lodflow:jobInstanceId")
    protected Long jobInstanceId;

    public JobInstanceSpec() {
    }

    public JobInstanceSpec(String jobId, String params, Long jobInstanceId) {
        this.jobId = jobId;
        this.params = params;
        this.jobInstanceId = jobInstanceId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }


}
