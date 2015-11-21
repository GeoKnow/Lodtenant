package org.aksw.lodtenant.config;

import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.mapper.impl.type.RdfTypeFactoryImpl;
import org.aksw.jena_sparql_api.mapper.jpa.EntityManagerJena;
import org.aksw.lodtenant.core.impl.JobManagerImpl;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import com.hp.hpl.jena.sparql.core.Prologue;

public class ConfigJob {
    @Bean
    public EntityManagerJena entityManager(@Qualifier("jobRepo") SparqlService ss) {
        Prologue prologue = new Prologue();
        prologue.setPrefix("o", "http://example.org/");
        prologue.setPrefix("lodflow", "http://lodflow.aksw.org/ontology/");

        RdfTypeFactoryImpl rdfClassFactory = RdfTypeFactoryImpl
                .createDefault(prologue);

        // SparqlService ss =
        // FluentSparqlService.http("http://localhost:8890/sparql",
        // "http://rdfmap.org/").create();
        EntityManagerJena result = new EntityManagerJena(rdfClassFactory, null,
                ss);

        return result;
    }

    @Bean
    @Autowired
    public JobManager jobManager(AbstractBatchConfiguration batchConfig,
            EntityManagerJena entityManager) {
        JobManager result = new JobManagerImpl(batchConfig, entityManager);
        return result;
    }

}
