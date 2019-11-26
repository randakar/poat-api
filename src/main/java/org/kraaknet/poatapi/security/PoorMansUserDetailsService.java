package org.kraaknet.poatapi.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
public class PoorMansUserDetailsService implements UserDetailsService {

    @NonNull
    private final ApplicationConfigProperties properties;

    @NonNull
    private final PasswordEncoder encoder;

    /**
     * We accept every single username here.
     * And they all share the same password.
     */
    @Override
    public User loadUserByUsername(String username) {
        return new User(username, getPassword(username),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private String getPassword(String username) {
        return encoder.encode(properties.getUserPassword());
    }
}
