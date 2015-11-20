package org.aksw.lodtenant.core.impl;

import org.aksw.jena_sparql_api.mapper.jpa.EntityManagerJena;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.aksw.lodtenant.repo.rdf.JobExecutionSpec;
import org.aksw.lodtenant.repo.rdf.JobInstanceSpec;
import org.aksw.lodtenant.repo.rdf.JobSpec;
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
        JobSpec jobSpec = new JobSpec(jobSpecStr, "http://example.org/agent/defaultAgent", null);

        entityManager.persist(jobSpec);
        Node subject = entityManager.getRdfClassFactory().create(JobSpec.class).getSubject(jobSpec);

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

        JobInstanceSpec jis = new JobInstanceSpec(jobId, jobParams);

        entityManager.merge(jis);

        Node subject = entityManager.getRdfClassFactory().create(JobInstanceSpec.class).getSubject(jis);

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

        Node subject = entityManager.getRdfClassFactory().create(JobExecutionSpec.class).getSubject(jes);

        String result = subject.getURI();
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

