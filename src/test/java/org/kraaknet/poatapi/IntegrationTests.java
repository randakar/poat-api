package org.kraaknet.poatapi;

import io.restassured.http.ContentType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.kraaknet.poatapi.integration.RestAssuredConfigurer;
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

    @Autowired
    @NonNull
    private ApplicationConfigProperties properties;

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
    public void whenRequestingTheAggregatedViewGetAnEmptyAggregatedView() {
        String responseBody = given()
                .when()
                .get("/power-of-attorney/view")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract().body().asString();
        log.debug("Response: {}", responseBody);
        assertEquals("[]", responseBody);
    }

    @Test
    public void whenRequestingTheAggregatedViewAsSuperDuperCompanyGetAnAggregatedView() {
        String responseBody = given()
                .when()
                .auth().basic("Super duper company", properties.getUserPassword())
                .get("/power-of-attorney/view")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract().body().asString();
        log.debug("Response: {}", responseBody);

        // TODO: read the expected value from a file in the resources section.
        assertEquals("[{\"id\":\"0003\",\"grantor\":\"Super duper company\",\"grantee\":\"Super duper employee\",\"account\":\"NL23RABO343434343\",\"direction\":\"GIVEN\",\"authorizations\":[\"VIEW\",\"PAYMENT\"],\"cards\":[]},{\"id\":\"0004\",\"grantor\":\"Super duper employee\",\"grantee\":\"Super duper company\",\"account\":\"NL23RABO123123123\",\"direction\":\"RECEIVED\",\"authorizations\":[\"VIEW\",\"PAYMENT\"],\"cards\":[]},{\"id\":\"0001\",\"grantor\":\"Super duper company\",\"grantee\":\"Fellowship of the ring\",\"account\":\"NL23RABO123456789\",\"direction\":\"GIVEN\",\"authorizations\":[\"DEBIT_CARD\",\"VIEW\",\"PAYMENT\"],\"cards\":[{\"id\":\"1111\",\"type\":\"DEBIT_CARD\",\"cardNumber\":1234,\"sequenceNumber\":5,\"cardHolder\":\"Frodo Basggins\",\"atmLimit\":{\"limit\":3000,\"periodUnit\":\"PER_WEEK\"},\"posLimit\":{\"limit\":50,\"periodUnit\":\"PER_MONTH\"},\"contactless\":true},{\"id\":\"2222\",\"type\":\"DEBIT_CARD\",\"cardNumber\":6527,\"sequenceNumber\":1,\"cardHolder\":\"Aragorn\",\"atmLimit\":{\"limit\":100,\"periodUnit\":\"PER_DAY\"},\"posLimit\":{\"limit\":10000,\"periodUnit\":\"PER_MONTH\"},\"contactless\":true},{\"id\":\"3333\",\"type\":\"CREDIT_CARD\",\"cardNumber\":5075,\"sequenceNumber\":1,\"cardHolder\":\"Boromir\",\"limit\":{\"limit\":3000,\"periodUnit\":\"PER_MONTH\"}}]},{\"id\":\"0002\",\"grantor\":\"Super duper company\",\"grantee\":\"Super duper employee\",\"account\":\"NL23RABO987654321\",\"direction\":\"GIVEN\",\"authorizations\":[\"DEBIT_CARD\",\"VIEW\",\"PAYMENT\"],\"cards\":[{\"id\":\"4444\",\"type\":\"DEBIT_CARD\",\"cardNumber\":1111,\"sequenceNumber\":32,\"cardHolder\":\"Super duper employee\",\"atmLimit\":{\"limit\":100,\"periodUnit\":\"PER_DAY\"},\"posLimit\":{\"limit\":10000,\"periodUnit\":\"PER_MONTH\"},\"contactless\":false}]}]", responseBody);
    }

    @Test
    public void whenRequestingTheAggregatedViewAsFellowShipGetAnotherAggregatedView() {
        String responseBody = given()
                .when()
                .auth().basic("Fellowship of the ring", properties.getUserPassword())
                .get("/power-of-attorney/view")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract().body().asString();
        log.debug("Response: {}", responseBody);

        // TODO: read the expected value from a file in the resources section.
        assertEquals("[{\"id\":\"0001\",\"grantor\":\"Super duper company\",\"grantee\":\"Fellowship of the ring\",\"account\":\"NL23RABO123456789\",\"direction\":\"GIVEN\",\"authorizations\":[\"DEBIT_CARD\",\"VIEW\",\"PAYMENT\"],\"cards\":[{\"id\":\"1111\",\"type\":\"DEBIT_CARD\",\"cardNumber\":1234,\"sequenceNumber\":5,\"cardHolder\":\"Frodo Basggins\",\"atmLimit\":{\"limit\":3000,\"periodUnit\":\"PER_WEEK\"},\"posLimit\":{\"limit\":50,\"periodUnit\":\"PER_MONTH\"},\"contactless\":true},{\"id\":\"2222\",\"type\":\"DEBIT_CARD\",\"cardNumber\":6527,\"sequenceNumber\":1,\"cardHolder\":\"Aragorn\",\"atmLimit\":{\"limit\":100,\"periodUnit\":\"PER_DAY\"},\"posLimit\":{\"limit\":10000,\"periodUnit\":\"PER_MONTH\"},\"contactless\":true},{\"id\":\"3333\",\"type\":\"CREDIT_CARD\",\"cardNumber\":5075,\"sequenceNumber\":1,\"cardHolder\":\"Boromir\",\"limit\":{\"limit\":3000,\"periodUnit\":\"PER_MONTH\"}}]}]", responseBody);
    }


}