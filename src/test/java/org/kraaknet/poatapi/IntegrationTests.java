package org.kraaknet.poatapi;

import io.restassured.http.ContentType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kraaknet.poatapi.integration.RestAssuredConfigurer;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyReferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
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

        ParameterizedTypeReference<List<PowerOfAttorneyReferenceDTO>> typeReference = new ParameterizedTypeReference<>() {
        };

        List<PowerOfAttorneyReferenceDTO> response = given()
                .when()
                .get("/power-of-attorney/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract().body().as(typeReference.getType());

        log.debug("Response: {}", response);

    }

}
