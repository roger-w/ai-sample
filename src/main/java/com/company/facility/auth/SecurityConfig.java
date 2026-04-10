package com.company.facility.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${app.security.oauth2-enabled:true}")
	private boolean oauth2Enabled;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> {
				auth.requestMatchers("/actuator/health", "/actuator/health/**").permitAll();
				auth.requestMatchers("/api/v1/health").permitAll();
				if (oauth2Enabled) {
					auth.anyRequest().authenticated();
				} else {
					auth.anyRequest().permitAll();
				}
			});

		if (oauth2Enabled) {
			http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		}

		return http.build();
	}
}
