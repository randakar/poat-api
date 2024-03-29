package org.kraaknet.poatapi.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        response.addHeader("WWW-Authenticate", String.format("Basic realm=\"%s\"", getRealmName()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + exception.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("kraaknet");
        super.afterPropertiesSet();
    }
}
