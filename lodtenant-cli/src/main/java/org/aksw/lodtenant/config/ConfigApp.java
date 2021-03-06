package org.aksw.lodtenant.config;

import org.aksw.jena_sparql_api.batch.config.ConfigParsersCore;
import org.aksw.jena_sparql_api.core.SparqlServiceFactory;
import org.aksw.jena_sparql_api.core.SparqlServiceFactoryHttp;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@Import(ConfigDataSource.class)
@Configuration
@Import({ ConfigParsersCore.class })
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
