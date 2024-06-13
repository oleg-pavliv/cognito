package com.example.springboot;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CognitoAuthConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Allow all requests without authentication
	/*
        http.authorizeRequests()
            .anyRequest().permitAll();
	return http.build();
	*/

        http.csrf().disable();

        http.authorizeRequests()
            .requestMatchers("/admin/**").hasAuthority("SCOPE_ns_shield/nagra_admin")
            .requestMatchers(HttpMethod.GET,    "/api/**").hasAnyAuthority("SCOPE_ns_shield/nagra_admin", "SCOPE_ns_shield/op_admin", "SCOPE_ns_shield/op_viewer")
            .requestMatchers(HttpMethod.POST,   "/api/**").hasAnyAuthority("SCOPE_ns_shield/nagra_admin", "SCOPE_ns_shield/op_admin")
            .requestMatchers(HttpMethod.PUT,    "/api/**").hasAnyAuthority("SCOPE_ns_shield/nagra_admin", "SCOPE_ns_shield/op_admin")
            .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("SCOPE_ns_shield/nagra_admin", "SCOPE_ns_shield/op_admin")
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(jwtAuthenticationConverter());

        return http.build();
    }

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new LoggingConverter(grantedAuthoritiesConverter));
	
        return jwtAuthenticationConverter;
    }


    private static class LoggingConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        private final JwtGrantedAuthoritiesConverter delegate;

        LoggingConverter(JwtGrantedAuthoritiesConverter delegate) {
            this.delegate = delegate;
        }

        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Collection<GrantedAuthority> authorities = delegate.convert(jwt);
            System.out.println("JWT Claims: " + jwt.getClaims());
            System.out.println("Granted authorities: " + authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
            return authorities;
        }
    }
}

