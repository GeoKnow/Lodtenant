package org.aksw.lodtenant.cli;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.aksw.jena_sparql_api.batch.BatchWorkflowManager;
import org.aksw.jena_sparql_api.batch.SparqlBatchUtils;
import org.aksw.jena_sparql_api.batch.cli.main.MainBatchWorkflow;
import org.aksw.jena_sparql_api.beans.json.JsonProcessorContext;
import org.aksw.lodtenant.config.ConfigApp;
import org.aksw.lodtenant.manager.domain.Workflow;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import joptsimple.ArgumentAcceptingOptionSpec;
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

    List<Workflow> testDeleteme = new ArrayList<Workflow>();

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


        OptionParser parser = new OptionParser();

        OptionSpec<File> configFileOs = parser
                .acceptsAll(Arrays.asList("c", "config"))
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("Configuration of the context in which workflows run").required();//.defaultsTo(null);

        OptionSpec<File> jobFileOs = parser
                .acceptsAll(Arrays.asList("j", "job"))
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("File containing the job definition");

        OptionSpec<String> jobIdOs = parser
                .acceptsAll(Arrays.asList("i", "id"))
                .withRequiredArg()
                .ofType(String.class)
                .describedAs("Job identifier");

        OptionSpec<File> jobParamsOs = parser
                .acceptsAll(Arrays.asList("p", "params"))
                .withRequiredArg()
                .ofType(File.class)
                .describedAs("Job parameter file");

        OptionSpec<File> registerOs = parser
                .acceptsAll(Arrays.asList("register"))
                    .withRequiredArg()
                    .ofType(File.class)
                .describedAs("Registers a new job template");

        OptionSpec<File> prepareOs = parser
                .acceptsAll(Arrays.asList("prepare"))
                .withRequiredArg()
                    .describedAs("job alias")
                    .ofType(File.class)
                .describedAs("Create a job instance");



        parser.accepts(PREPARE).withRequiredArg();
        parser.accepts(START).withRequiredArg();

        parser.printHelpOn(System.err);

        OptionSet options = parser.parse(args);


        JOptCommandLinePropertySource clps = new JOptCommandLinePropertySource(options);

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.getEnvironment().getPropertySources().addFirst(clps);
        appContext.register(ConfigApp.class);
        appContext.refresh();

        Gson gson = appContext.getBean(Gson.class);

        // new OptionSpecBuilder().
        if (options.has(configFileOs)) {
            File configFile = configFileOs.value(options);

            Resource configResource = new FileSystemResource(configFile);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(configResource.getInputStream()));
            jsonReader.setLenient(true);

            JsonElement json = gson.fromJson(jsonReader, JsonElement.class);
            json = MainBatchWorkflow.rewrite(json);

            String str = gson.toJson(json);
            System.out.println(str);


            GenericApplicationContext configContext = new GenericApplicationContext();
            configContext.setParent(appContext);
            JsonProcessorContext contextProcessor = new JsonProcessorContext(configContext);
            contextProcessor.process(json);
            configContext.refresh();
        }



        //List<Integer> x; x.getClass().getSimpleName();


        if (options.has(REGISTER)) {
            String workflow = (String) options.valueOf(REGISTER);

            ApplicationContext baseContext = MainBatchWorkflow.initBaseContext(appContext);
            MainBatchWorkflow.initJenaExtensions(baseContext);
            ApplicationContext batchContext = MainBatchWorkflow.initContext(baseContext);

            // SparqlService test =
            // (SparqlService)batchContext.getBean("sourceFile");
            // System.out.println("SourceFile: " + test);

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
