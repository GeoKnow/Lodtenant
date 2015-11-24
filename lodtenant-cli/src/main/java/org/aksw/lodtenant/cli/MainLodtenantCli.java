package org.aksw.lodtenant.cli;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.aksw.commons.util.StreamUtils;
import org.aksw.gson.utils.JsonWalker;
import org.aksw.jena_sparql_api.batch.BatchWorkflowManager;
import org.aksw.jena_sparql_api.batch.SparqlBatchUtils;
import org.aksw.jena_sparql_api.batch.cli.main.MainBatchWorkflow;
import org.aksw.jena_sparql_api.batch.config.ConfigSparqlServicesCore;
import org.aksw.jena_sparql_api.batch.json.domain.JsonVisitorRewriteBeans;
import org.aksw.jena_sparql_api.beans.json.JsonProcessorContext;
import org.aksw.lodtenant.config.ConfigApp;
import org.aksw.lodtenant.config.ConfigJob;
import org.aksw.lodtenant.core.impl.JobManagerImpl;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.aksw.lodtenant.repo.rdf.JobSpec;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class MainLodtenantCli {

    /**
     * Register a workflow
     *
     * register {workflowfile} [as] {alias}
     *
     * By default, the alias will be the workflow file without its extension
     */
    public static final String REGISTER = "register";


    /**
     * Prepare the execution of a workflow using a set of parameters
     *
     * prepare {alias}
     */
    public static final String PREPARE = "prepare";


    public static final String CONFIG = "c";

    List<JobSpec> testDeleteme = new ArrayList<JobSpec>();

    public void foobar() throws NoSuchFieldException, SecurityException {
        Class<?> clazz = testDeleteme.getClass();
        Field stringListField = clazz.getDeclaredField("stringList");
        //stringListField.getDeclaringClass()
        //stringListField.getg
        //stringListField.getGe
        //Type x;
        ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];    }

    /**
     * Life cycle management functions for job executions
     *
     */
    public static final String START = "start";
    public static final String ABORT = "abort";
    public static final String STOP = "stop";
    public static final String RESTART = "restart";


    public static void main(String[] args) throws Exception {

//        JobLauncher x;
//        JobExecution y = x.run(job, jobParameters);
//        y.
//        JobRepository x;
//        x.ge

        OptionParser parser = new OptionParser();

        OptionSpec<File> configFileOs = parser
                .acceptsAll(Arrays.asList("c", "config"), "Configuration of the context in which workflows run")
                .withRequiredArg()
                //.describedAs()
                .ofType(File.class)
                .required()
                ;//.defaultsTo(null);

        OptionSpec<File> jobFileOs = parser
                .acceptsAll(Arrays.asList("f", "file"), "File containing the job definition")
                .withRequiredArg()
                .ofType(File.class)
                ;
                //.describedAs();

        OptionSpec<String> jobIdOs = parser
                .acceptsAll(Arrays.asList("j", "job"))
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("jobId")
                ;

        OptionSpec<String> instanceIdOs = parser
                .acceptsAll(Arrays.asList("i", "instance"))
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("instanceId")
                ;

        OptionSpec<String> executionIdOs = parser
                .acceptsAll(Arrays.asList("e", "execution"))
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("executionId")
                ;

        OptionSpec<File> jobParamsOs = parser
                .acceptsAll(Arrays.asList("p", "params"), "Job parameter file")
                .withRequiredArg()
                .ofType(File.class)
                ;
                //.describedAs();

        OptionSpec<File> registerOs = parser
                .acceptsAll(Arrays.asList("r", "register"), "Registers a new job template")
                .withRequiredArg()
                .ofType(File.class)
                ;
                //.describedAs();

        OptionSpec<File> prepareOs = parser
                .acceptsAll(Arrays.asList("prepare"), "Create a job instance")
                .withRequiredArg()
                .describedAs("job alias")
                .ofType(File.class)
//                .describedAs("Create a job instance")
                ;

        OptionSpec<File> launchOs = parser
                .acceptsAll(Arrays.asList("launch"), "Create a new execution")
                .withRequiredArg()
                .describedAs("jobInstanceId")
                .ofType(File.class)
//                .describedAs("Create a job instance")
                ;


        parser.accepts(PREPARE).withRequiredArg();
        parser.accepts(START).withRequiredArg();

        parser.printHelpOn(System.err);

        OptionSet options = parser.parse(args);


        JOptCommandLinePropertySource clps = new JOptCommandLinePropertySource(options);

//        QualifierAnnotationAutowireCandidateResolver autowireCandidateResolver = new QualifierAnnotationAutowireCandidateResolver();

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        //((DefaultListableBeanFactory)appContext.getBeanFactory()).setAutowireCandidateResolver(autowireCandidateResolver);
        appContext.getEnvironment().getPropertySources().addFirst(clps);
        appContext.register(ConfigApp.class);
        appContext.refresh();

        Gson gson = appContext.getBean(Gson.class);

        // new OptionSpecBuilder().

//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        beanFactory.setAutowireCandidateResolver(candidateResolver);
        AnnotationConfigApplicationContext configContext = new AnnotationConfigApplicationContext();
        //((DefaultListableBeanFactory)configContext.getBeanFactory()).setAutowireCandidateResolver(autowireCandidateResolver);
        //System.out.println("FFS: " + ((DefaultListableBeanFactory)configContext.getBeanFactory()).getAutowireCandidateResolver().getClass());
        configContext.setParent(appContext);

        if (options.has(configFileOs)) {
            File configFile = configFileOs.value(options);

            Resource configResource = new FileSystemResource(configFile);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(configResource.getInputStream()));
            jsonReader.setLenient(true);

            JsonElement json = gson.fromJson(jsonReader, JsonElement.class);
            json = MainBatchWorkflow.rewrite(json);

            json = JsonWalker.rewriteUntilNoChange(json, new JsonVisitorRewriteBeans());


            String str = gson.toJson(json);
            System.out.println(str);


            JsonProcessorContext contextProcessor = new JsonProcessorContext(configContext);
            contextProcessor.process(json);
        }

        configContext.refresh();

//        System.out.println("CONFIG CONTEXT: " + Arrays.asList(configContext.getBeanDefinitionNames()));
//        DefaultListableBeanFactory bf = (DefaultListableBeanFactory)configContext.getBeanFactory();
//        bf.getBean

        //System.out.println("GOT BEAN: " + BeanFactoryAnnotationUtils.qualifiedBeanOfType(configContext.getBeanFactory(), SparqlService.class, "logging"));

        AnnotationConfigApplicationContext jobContext = new AnnotationConfigApplicationContext();
        jobContext.setParent(configContext);
        jobContext.register(ConfigSparqlServicesCore.class);
        jobContext.register(ConfigJob.class);
        jobContext.refresh();


        JobManager jobManager = (JobManager)jobContext.getAutowireCapableBeanFactory().autowire(JobManagerImpl.class, AutowireCapableBeanFactory.AUTOWIRE_CONSTRUCTOR, true);


        String jobId = options.has(jobIdOs)
                ? jobIdOs.value(options)
                : null
                ;

        String instanceId = options.has(instanceIdOs)
                ? instanceIdOs.value(options)
                : null
                ;

        String executionId = options.has(executionIdOs)
                ? executionIdOs.value(options)
                : null
                ;


        if(options.has(registerOs)) {
            File file = registerOs.value(options);
            String jobSpecStr = Files.toString(file, Charsets.UTF_8);//StreamUtils.toString(new FileInputStream)

            //JobManager jobManager = jobContext.getBean(JobManager.class);
            jobId = jobManager.registerJob(jobSpecStr);
        }
        System.out.println("jobId: " + jobId);


        if(options.has(prepareOs)) {
            File file = prepareOs.value(options);
            String paramsStr = Files.toString(file, Charsets.UTF_8);//StreamUtils.toString(new FileInputStream)

            instanceId = jobManager.createJobInstance(jobId, paramsStr);
        }
        System.out.println("jobInstanceId: " + instanceId);



        if(options.has(launchOs)) {
            //executionId = launchOs.value(options);
            //String paramsStr = Files.toString(file, Charsets.UTF_8);//StreamUtils.toString(new FileInputStream)

            executionId = jobManager.createJobExecution(instanceId);
        }
        System.out.println("jobExecutionId: " + executionId);


//        Job jobX = jobManager.getJob(jobId);
//        System.out.println(jobX);



//        SparqlServiceFactory defaultSparqlServiceFactory = workflowContext.getBean(SparqlServiceFactory.class);
//        System.out.println("DefaultSparqlServiceFactory: " + defaultSparqlServiceFactory);
//        System.exit(0);

        //List<Integer> x; x.getClass().getSimpleName();




        if (false && options.has(registerOs)) {
            // Init parsers and spring batch
            ApplicationContext baseContext = MainBatchWorkflow.initBaseContext(jobContext);

            // Init jena extensions
            MainBatchWorkflow.initJenaExtensions(baseContext);



            File workflowFile = registerOs.value(options);


            Resource resource = new FileSystemResource(workflowFile);
            String str = StreamUtils.toString(resource.getInputStream());


            //Reader reader = new InputStreamReader(resource.getInputStream());
            Reader reader = new StringReader(str);
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);
            JsonElement jobJson = gson.fromJson(jsonReader, JsonElement.class);



            // Finally, init the job
            ApplicationContext batchContext = MainBatchWorkflow.initContext(baseContext, jobJson);


            JobOperator jobOperator = batchContext.getBean(JobOperator.class);
            // JobLauncher jobLauncher = batchContext.getBean(JobLauncher.class);
            Job job = batchContext.getBean(Job.class);


            System.out.println("STEP: " + ((SimpleJob) job).getStepNames());

            Collection<String> allBeans = Arrays
                    .asList(batchContext.getBeanDefinitionNames());
            System.out.println("Got " + allBeans.size() + " beans: " + allBeans);

            System.out.println("Job: " + job);

            SparqlBatchUtils.cleanUp(batchContext);

            BatchWorkflowManager manager = batchContext
                    .getBean(BatchWorkflowManager.class);// new
                                                         // BatchWorkflowManager(config);

            manager.launch(job, new JobParameters());

            Thread.sleep(3000);
            System.out.println("Waited for 3 sec");
            // Object foo = batchContext.getBean("steps"

            // OptionSet options = parser.
        } else {
            parser.printHelpOn(System.err);
        }
    }
}
