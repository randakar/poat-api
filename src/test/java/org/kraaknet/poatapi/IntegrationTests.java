package org.kraaknet.poatapi;

import io.restassured.http.ContentType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.integration.RestAssuredConfigurer;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = PoatApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = IntegrationTestConfig.class, loader = SpringBootContextLoader.class)
public class IntegrationTests {

    @LocalServerPort
    protected int localServerPort;

    @Autowired
    @NonNull
    private RestAssuredConfigurer configurer;

    @Before
    public void setup() {
        log.debug("Initializing .. local server port: {}", localServerPort);
        configurer.init(localServerPort);
    }

    @Test
    public void whenApplicationStartedSuccessfullyThisRunsToo() {
        log.info("If we got here, the application started successfully.");
        assertTrue(true);
    }

    @Test
    public void whenRequestingAllThePowerGetAllThePowers() {

        ParameterizedTypeReference<Set<PowerOfAttorneyReference>> typeReference = new ParameterizedTypeReference<>() {
        };

        Set<PowerOfAttorneyReference> response = given()
                .when()
                .get("/power-of-attorney/reference")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract().body().as(typeReference.getType());

        log.debug("Response: {}", response);
        Set<PowerOfAttorneyReference> expectedResponse = IntStream.rangeClosed(1, 4).boxed()
                .map(i -> PowerOfAttorneyReference.builder()
                        .id(format("%04d", i))
                        .build())
                .collect(Collectors.toSet());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void whenRequestingTheAggregatedViewGetAnAggregatedView() {
        ParameterizedTypeReference<Set<PowerOfAttorneyDTO>> typeReference = new ParameterizedTypeReference<>() {
        };

        Set<PowerOfAttorneyDTO> response = given()
                .when()
                .get("/power-of-attorney/view")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract().body().as(typeReference.getType());

        log.debug("Response: {}", response);

    }
}