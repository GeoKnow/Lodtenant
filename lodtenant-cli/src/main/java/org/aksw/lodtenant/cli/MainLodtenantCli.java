package org.aksw.lodtenant.cli;

import java.util.Arrays;
import java.util.Collection;

import org.aksw.jena_sparql_api.batch.BatchWorkflowManager;
import org.aksw.jena_sparql_api.batch.cli.main.MainBatchWorkflow;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.context.ApplicationContext;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

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
        parser.accepts(REGISTER).withRequiredArg();
        parser.accepts(PREPARE).withRequiredArg();
        parser.accepts(START).withRequiredArg();

        OptionSet options = parser.parse(args);
        // new OptionSpecBuilder().
        if (options.has(REGISTER)) {
            String workflow = (String) options.valueOf(REGISTER);

            ApplicationContext baseContext = MainBatchWorkflow.initBaseContext();
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
