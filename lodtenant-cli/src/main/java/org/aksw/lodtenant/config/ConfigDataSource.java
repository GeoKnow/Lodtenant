package org.aksw.lodtenant.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class ConfigDataSource {

    @Value("${batch.db.driver}")
    private String driverClassName;

    @Value("${batch.db.url}")
    private String jdbcUrl;

    @Value("${batch.db.user}")
    private String username;

    @Value("${batch.db.pass}")
    private String password;



    public static void populateSpringBatchH2(DataSource dataSource) throws SQLException {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        //populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-drop-h2.sql"));
        populator.setContinueOnError(true);
        populator.addScript(new ClassPathResource("org/springframework/batch/core/schema-postgresql.sql"));

        Connection conn = dataSource.getConnection();
        try {
            populator.populate(conn);
        } finally {
            conn.close();
        }
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource result = new DriverManagerDataSource();
//        result.setDriverClassName("org.postgresql.Driver");
//        result.setUrl("jdbc:postgresql://localhost:5432/batch");
//        result.setUsername("postgres");
//        result.setPassword("postgres");
        result.setDriverClassName(driverClassName);
        result.setUrl(jdbcUrl);
        result.setUsername(username);
        result.setPassword(password);

        populateSpringBatchH2(result);


//      ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
//      rdp.addScript(new ClassPathResource("/org/aksw/jena_sparql_api/cache/cache-schema-pgsql.sql"));
//      rdp.populate(ds.getConnection());


        return result;
    }
}
