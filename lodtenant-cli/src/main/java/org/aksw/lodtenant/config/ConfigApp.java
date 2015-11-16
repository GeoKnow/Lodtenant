package org.aksw.lodtenant.config;

import org.aksw.jena_sparql_api.batch.config.ConfigBatchJobDynamic;
import org.aksw.jena_sparql_api.batch.config.ConfigServicesCore;
import org.aksw.jena_sparql_api.core.SparqlService;
import org.aksw.jena_sparql_api.core.SparqlServiceFactory;
import org.aksw.jena_sparql_api.core.SparqlServiceFactoryHttp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//@Import(ConfigDataSource.class)
@Import({ConfigServicesCore.class, ConfigBatchJobDynamic.class})
public class ConfigApp {

    //@Value("#{ @lodtenant.tracker.sparqlService }")
    private SparqlService trackerSparqlService;

    @Bean
    public static Gson gson() {
        Gson result = new GsonBuilder().setPrettyPrinting().create();
        return result;
    }

    @Bean
    public static SparqlServiceFactory defaultSparqlServiceFactory() {
        SparqlServiceFactory result = new SparqlServiceFactoryHttp();
        return result;
    }



    /**
     * To resolve ${} in @Value
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
