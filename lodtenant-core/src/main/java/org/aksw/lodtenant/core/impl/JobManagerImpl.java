package org.aksw.lodtenant.core.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.aksw.gson.utils.JsonTransformerRewrite;
import org.aksw.gson.utils.JsonWalker;
import org.aksw.jena_sparql_api.batch.cli.main.JobParametersJsonUtils;
import org.aksw.jena_sparql_api.batch.cli.main.JsonVisitorRewriteJobParameters;
import org.aksw.jena_sparql_api.batch.cli.main.JsonVisitorRewriteKeys;
import org.aksw.jena_sparql_api.batch.cli.main.MainBatchWorkflow;
import org.aksw.jena_sparql_api.mapper.jpa.core.EntityManagerJena;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.aksw.lodtenant.repo.rdf.JobExecutionSpec;
import org.aksw.lodtenant.repo.rdf.JobInstanceSpec;
import org.aksw.lodtenant.repo.rdf.JobSpec;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.hp.hpl.jena.graph.Node;

//class JobFactoryLodtenant
//    implements JobFactory
//{
//
//}

public class JobManagerImpl
    implements JobManager
{
    protected ApplicationContext baseContext;
    protected AbstractBatchConfiguration batchConfig;
    protected EntityManagerJena entityManager;

    protected Gson gson;


    public JobManagerImpl(ApplicationContext baseContext, AbstractBatchConfiguration batchConfig, EntityManagerJena entityManager, Gson gson) {
        this.baseContext = baseContext;
        this.batchConfig = batchConfig;
        this.entityManager = entityManager;
        this.gson = gson;
    }

    public JsonElement parseJson(String str) {
        //Reader reader = new InputStreamReader(resource.getInputStream());
        Reader reader = new StringReader(str);
        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(true);

        JsonElement result = gson.fromJson(jsonReader, JsonElement.class);
        return result;
    }

    public Job buildJob(String jobSpecStr) {
        JsonElement jobSpecJson = parseJson(jobSpecStr);

        // Finally, init the job
        ApplicationContext batchContext;
        try {
            batchContext = MainBatchWorkflow.initContext(baseContext, jobSpecJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Job result = batchContext.getBean(Job.class);

        return result;
    }


    @Override
    public String registerJob(String jobSpecStr) {
        final Job job = buildJob(jobSpecStr);

        String jobName = job.getName();
        JobSpec jobSpec = new JobSpec(jobSpecStr, "http://example.org/agent/defaultAgent", jobName);

        entityManager.persist(jobSpec);
        Node subject = entityManager.getRdfTypeFactory().forJavaType(JobSpec.class).getRootNode(jobSpec);

        try {
            batchConfig.jobRegistry().register(new JobFactory() {
                @Override
                public String getJobName() {
                    return job.getName();
                }

                @Override
                public Job createJob() {
                    return job;
                }
            });
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(subject);

        String result = subject.getURI();
        return result;
    }

    public JsonElement parseJobParams(String jobParamsStr) {
        JsonElement jobParamsJson = parseJson(jobParamsStr);
        JsonVisitorRewriteJobParameters subVisitor = new JsonVisitorRewriteJobParameters();
        JsonVisitorRewriteKeys visitor = JsonVisitorRewriteKeys.create(JsonTransformerRewrite.create(subVisitor, false));

        JsonElement result = JsonWalker.visit(jobParamsJson, visitor);

        return result;
    }

//    public JobParameters parseJobParams(String jobParamsStr) {
//        //String nomalizedJobParamsStr = gson.toJson(effectiveJobParamsJson);
//        //System.out.println("Job params: " + );
//        JobParameters result = JobParametersJsonUtils.toJobParameters(effectiveJobParamsJson, null);
//
//
//        return result;
//    }

    /**
     * Returns a JobInstance
     *
     * @param jobId
     * @param jobParams
     * @return
     */
    public String createJobInstance(String jobId, String jobParamsStr) {


        JobSpec jobSpec = entityManager.find(JobSpec.class, jobId);
        if(jobSpec == null) {
            throw new RuntimeException("No job found for " + jobId);
        }

        String jobName = jobSpec.getName();

        JsonElement jobParamsJson = parseJobParams(jobParamsStr);
        String nomalizedJobParamsStr = gson.toJson(jobParamsJson);
        JobParameters jobParams = JobParametersJsonUtils.toJobParameters(jobParamsJson, null);

        JobInstance jobInstance;
        try {
            boolean isJobInstanceExists = batchConfig.jobRepository().isJobInstanceExists(jobName, jobParams);
            if(isJobInstanceExists) {
                List<JobInstance> jobInstances = batchConfig.jobExplorer().findJobInstancesByJobName(jobName, 0, 1);
                jobInstance = jobInstances.get(0);
            } else {
                jobInstance = batchConfig.jobRepository().createJobInstance(jobName, jobParams);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Long instanceId = jobInstance.getId();


        JobInstanceSpec jis = new JobInstanceSpec(jobId, nomalizedJobParamsStr, instanceId);
        entityManager.merge(jis);
        Node subject = entityManager.getRdfTypeFactory().forJavaType(JobInstanceSpec.class).getRootNode(jis);

        String result = subject.getURI();
        return result;
    }

//
//    public String getLatestJobExecutionId(String jobInstanceId) {
//
//    }

    public String createJobExecution(String jobInstanceId) {
        String result;
        try {
            result = _createJobExecution(jobInstanceId);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String _createJobExecution(String jobInstanceId)
            throws Exception
    {
        JobInstanceSpec spec = entityManager.find(JobInstanceSpec.class, jobInstanceId);


        if(spec == null) {
            throw new RuntimeException("No job instance with id " + jobInstanceId + " found");
        }

        String jobId = spec.getJobId();
        String jobParamsStr = spec.getParams();

        Job job = getJob(jobId);
        String jobName = job.getName();
        JsonElement jobParamsJson = parseJobParams(jobParamsStr);
        JobParameters jobParams = JobParametersJsonUtils.toJobParameters(jobParamsJson, null);

        //JobExecution jobExecution;


//        try {
//            jobExecution = batchConfig.jobRepository().getLastJobExecution(job.getName(), jobParams);
//        } catch (Exception e1) {
//            throw new RuntimeException(e1);
//        }//.get .getLastJobExecution(job.getName(), jobParams);

        // If there was a prior job, return its execution context
//        BatchStatus status = jobExecution == null ? null : jobExecution.getStatus();
//        if(status != null) {
//            if(status.isRunning() || status.equals(BatchStatus.COMPLETED)) {
//                return result;
//            }
//        }

        //spec.getJobInstanceId();


        JobExecution tmp = batchConfig.jobRepository().getLastJobExecution(jobName, jobParams);

        if(tmp == null) {
            JobInstance jobInstance = new JobInstance(spec.getJobInstanceId(), jobName);
            tmp = batchConfig.jobRepository().createJobExecution(jobInstance, jobParams, null);
        }

        JobExecution jobExecution = batchConfig.jobLauncher().run(job, jobParams);

        //jobExecution = batchConfig.jobRepository().createJobExecution(ji, jobParams, null);
            //jobExecution.stop();

            //jobExecution = batchConfig.jobLauncher().run(job, jobParams);


            //batchConfig.jobRepository().createJobExecution(jobInstance, jobParameters, jobConfigurationLocation)

            //JobLauncher jobLauncher = batchConfig.jobLauncher();
//            JobRepository jobRepository = batchConfig.jobRepository();
//            JobInstance ji = new JobInstance(spec.getJobInstanceId(), jobName);
//            jobExecution = jobRepository.createJobExecution(ji, jobParams, null);
            //jobExecution = jobRepository.createJobExecution(jobName, jobParams);
            //jobExecution = jobLauncher.run(job, jobParams);

        // Get the next jobExecutionId
        Long jobExecutionId = jobExecution.getId();
        JobExecutionSpec jes = new JobExecutionSpec(jobInstanceId, jobExecutionId);

        entityManager.merge(jes);

        Node subject = entityManager.getRdfTypeFactory().forJavaType(JobExecutionSpec.class).getRootNode(jes);

        String result = subject.getURI();



        //job.execute(jobExecution);

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

