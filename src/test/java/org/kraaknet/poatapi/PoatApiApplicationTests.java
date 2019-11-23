package org.kraaknet.poatapi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest
class PoatApiApplicationTests {

    @Test
    void contextLoads() {
        log.info("If we got here, the application started successfully.");
        assertTrue(true);
    }

}
