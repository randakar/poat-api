package org.kraaknet.poatapi;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kraaknet.poatapi.integration.RestAssuredConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void contextLoads() {
        log.debug("contextLoads()");
        assertTrue(true);
    }

}
