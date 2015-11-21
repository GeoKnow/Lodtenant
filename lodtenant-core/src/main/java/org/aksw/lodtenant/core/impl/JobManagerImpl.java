package org.aksw.lodtenant.core.impl;

import org.aksw.jena_sparql_api.mapper.jpa.EntityManagerJena;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.aksw.lodtenant.repo.rdf.JobExecutionSpec;
import org.aksw.lodtenant.repo.rdf.JobInstanceSpec;
import org.aksw.lodtenant.repo.rdf.JobSpec;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;

import com.hp.hpl.jena.graph.Node;

public class JobManagerImpl
    implements JobManager
{
    protected AbstractBatchConfiguration batchConfig;

    protected EntityManagerJena entityManager;
    //protected ApplicationContext parentContext;


    public JobManagerImpl(AbstractBatchConfiguration batchConfig, EntityManagerJena entityManager) {
        this.batchConfig = batchConfig;
        this.entityManager = entityManager;
    }

    @Override
    public String registerJob(String jobSpecStr) {
        String jobName = "foo";
        JobSpec jobSpec = new JobSpec(jobSpecStr, "http://example.org/agent/defaultAgent", jobName);

        entityManager.persist(jobSpec);
        Node subject = entityManager.getRdfClassFactory().forJavaType(JobSpec.class).getRootNode(jobSpec);

        System.out.println(subject);

        String result = subject.getURI();
        return result;
//        RdfClass rdfClass = entityManager.getRdfClassFactory().create(JobSpec.class);
//        DatasetGraph dg = rdfClass.createDatasetGraph(jobSpec, Quad.defaultGraphIRI);
//        Graph graph = dg.getDefaultGraph();
//
//
//        UpdateExecutionUtils.executeInsert(uef, graph)
    }

    /**
     * Returns a JobInstance
     *
     * @param jobId
     * @param jobParams
     * @return
     */
    public String createJobInstance(String jobId, String jobParams) {

        JobInstanceSpec jis = new JobInstanceSpec(jobId, jobParams, -1l);

        entityManager.merge(jis);

        Node subject = entityManager.getRdfClassFactory().forJavaType(JobInstanceSpec.class).getRootNode(jis);

        String result = subject.getURI();
        return result;
    }

//
//    public String getLatestJobExecutionId(String jobInstanceId) {
//
//    }

    public String createJobExecution(String jobInstanceId) {
        // Get the next jobExecutionId
        Long jobExecutionId = null;
        JobExecutionSpec jes = new JobExecutionSpec(jobInstanceId, jobExecutionId);

        entityManager.merge(jes);

        Node subject = entityManager.getRdfClassFactory().forJavaType(JobExecutionSpec.class).getRootNode(jes);

        String result = subject.getURI();
        return result;

    }

    @Override
    public Job getJob(String iri) {
        JobSpec spec = entityManager.find(JobSpec.class, iri);
        String jobName = spec.getName();

        Job result;
        try {
            result = batchConfig.jobRegistry().getJob(jobName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public JobInstance getJobInstance(String iri) {
        JobInstanceSpec spec = entityManager.find(JobInstanceSpec.class, iri);
        Long batchId = spec.getJobInstanceId();

        JobInstance result;
        try {
            result = batchConfig.jobExplorer().getJobInstance(batchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public JobExecution getJobExecution(String iri) {
        JobExecutionSpec spec = entityManager.find(JobExecutionSpec.class, iri);
        Long batchId = spec.getExecutionId();

        JobExecution result;
        try {
            result = batchConfig.jobExplorer().getJobExecution(batchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

//    public void start(String jobExecutionId) {
//
//
//    }

//
//    public void pause(String jobExecutionId) {
//        JobExecution jobExecution = null;
//
//        jobExecution.stop();
//    }
//
//    public void resume(String jobExecutionId) {
//        JobExecution jobExecution = null;
//
//        jobExecution.stop();
//        JobOperator x = null;
//        x.sta
//    }
//
//    public void abort(String jobExecutionId) {
//        jobExecution.
//    }


}

