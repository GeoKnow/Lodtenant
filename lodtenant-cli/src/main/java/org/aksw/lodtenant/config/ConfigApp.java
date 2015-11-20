package org.aksw.lodtenant.config;

import org.aksw.jena_sparql_api.batch.config.ConfigBatchJobDynamic;
import org.aksw.jena_sparql_api.batch.config.ConfigParsersCore;
import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.core.SparqlServiceFactory;
import org.aksw.jena_sparql_api.core.SparqlServiceFactoryHttp;
import org.aksw.jena_sparql_api.mapper.jpa.EntityManagerJena;
import org.aksw.jena_sparql_api.mapper.model.RdfClassFactory;
import org.aksw.lodtenant.core.impl.JobManagerImpl;
import org.aksw.lodtenant.core.interfaces.JobManager;
import org.springframework.batch.core.configuration.annotation.AbstractBatchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.hpl.jena.sparql.core.Prologue;

//@Import(ConfigDataSource.class)
@Configuration
@Import({ ConfigParsersCore.class, ConfigBatchJobDynamic.class })
public class ConfigApp {

    // @Value("#{ @lodtenant.tracker.sparqlService }")
    // private SparqlService trackerSparqlService;

    @Bean
    public Gson gson() {
        Gson result = new GsonBuilder().setPrettyPrinting().create();
        return result;
    }

    @Bean
    @Qualifier("config")
    public SparqlServiceFactory defaultSparqlServiceFactory() {
        SparqlServiceFactory result = new SparqlServiceFactoryHttp();
        return result;
    }


    /**
     * To resolve ${} in @Value
     *
     * @return
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
