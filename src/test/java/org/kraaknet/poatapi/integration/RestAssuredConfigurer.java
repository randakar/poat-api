package org.kraaknet.poatapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAssuredConfigurer {

    private static final String DEFAULT_USER = "test_user";

    @NonNull
    @Qualifier("objectMapper")
    private final ObjectMapper mapper;

    @NonNull
    private final ApplicationConfigProperties properties;

    public void init(int localServerPort) {
        RestAssured.port = localServerPort;
        RestAssured.baseURI = format("http://%s", properties.getBackendHost());
        RestAssured.basePath = properties.getContextRoot();
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                (type, s) -> mapper
        ));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

        String token = login(DEFAULT_USER);

        // Note: The order here matters a lot. When Restassured.config is set it overwrites the authentication config,
        // so this needs to happen in this exact order.
        RestAssured.authentication = RestAssured.oauth2(token);
    }

    private String login(String userName) {
        log.debug("Authentication not implemented.");
        return userName;
    }

}
