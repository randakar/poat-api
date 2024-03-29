package org.kraaknet.poatapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApplicationConfig {

    @Bean
    public ApplicationConfigProperties applicationConfigurationProperties() {
        return new ApplicationConfigProperties();
    }
}
