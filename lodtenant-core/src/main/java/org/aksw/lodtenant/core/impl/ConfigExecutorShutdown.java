package org.aksw.lodtenant.core.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigExecutorShutdown {

    @Bean
    public ApplicationListenerExecutorShutdown applicationListenerExecutorShutdown() {
        ApplicationListenerExecutorShutdown result = new ApplicationListenerExecutorShutdown();
        return result;
    }
}
