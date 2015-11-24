package org.aksw.lodtenant.config;

import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.mapper.impl.engine.RdfMapperEngine;
import org.aksw.jena_sparql_api.mapper.impl.engine.RdfMapperEngineImpl;
import org.aksw.jena_sparql_api.mapper.impl.type.RdfTypeFactoryImpl;
import org.aksw.jena_sparql_api.mapper.jpa.core.EntityManagerJena;
import org.aksw.lodtenant.core.impl.JobManagerImpl;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;
import com.hp.hpl.jena.sparql.core.Prologue;

public class ConfigJob {
    @Bean RdfMapperEngine mapperEngine(@Qualifier("jobRepo") SparqlService sparqlService) {
        Prologue prologue = new Prologue();
        prologue.setPrefix("o", "http://example.org/");
        prologue.setPrefix("lodflow", "http://lodflow.aksw.org/ontology/");

        RdfTypeFactoryImpl rdfTypeFactory = RdfTypeFactoryImpl
                .createDefault(prologue);

        RdfMapperEngine result = new RdfMapperEngineImpl(sparqlService, rdfTypeFactory);
        return result;
    }

    @Bean
    @Autowired
    public EntityManagerJena entityManager(RdfMapperEngine engine) {

        // SparqlService ss =
        // FluentSparqlService.http("http://localhost:8890/sparql",
        // "http://rdfmap.org/").create();
        EntityManagerJena result = new EntityManagerJena(engine);

        return result;
    }
//
//
//    @Bean
//    @Autowired
//    public JobManager jobManager(ApplicationContext baseContext, AbstractBatchConfiguration batchConfig,
//            EntityManagerJena entityManager, Gson gson) {
//        JobManager result = new JobManagerImpl(baseContext, batchConfig, entityManager, gson);
//        return result;
//    }

}
