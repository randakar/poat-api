package org.kraaknet.poatapi;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan(basePackages = {"org.kraaknet.poatapi"})
@EnableAutoConfiguration
public class IntegrationTestConfig {
}
