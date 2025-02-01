package com.cscb869_medical_records.config;

import com.cscb869_medical_records.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
@AllArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "http://0.0.0.0:8080/realms/medical-records-realm";
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakAuthorityConverter());
        return jwtAuthenticationConverter;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests
//                        (
//                                authz -> authz
//                                        .requestMatchers("/doctors/**").hasAuthority("doctor")
//                                        .requestMatchers("/api/doctors/**").hasAuthority("doctor")
//                                        .requestMatchers("/patients/**").hasAuthority("doctor")
//                                        .requestMatchers("/api/patients/**").hasAuthority("doctor")
//                                        .anyRequest().authenticated()
//                        )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwtCustomizer -> jwtCustomizer
//                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
//                .oauth2Login(Customizer.withDefaults());
//        return http.build();
//    }
}
