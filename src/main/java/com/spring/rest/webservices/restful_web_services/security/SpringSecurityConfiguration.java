package com.spring.rest.webservices.restful_web_services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    /**
     * Overriding the default filter chain to accept POST and PUT methods
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //All request should be authenticated
        httpSecurity.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

        //If a request is not authenticated, a pop up is shown
        httpSecurity.httpBasic(withDefaults());

        //CRSF -> POST, PUT
        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
