package org.kraaknet.poatapi.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Store application properties in one place.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApplicationConfigProperties {

    @Value("${poat.backend.host:localhost}")
    private String backendHost;

    @Value("${poat.backend.port:8080}")
    private String backendPort;

    @Value("server.servlet.context-path:/poat")
    private String contextRoot;

}
