package org.cn.gatewayservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecConfig {

    private final RsaConfig rsaConfig;

    public SecConfig(RsaConfig rsaConfig) {
        this.rsaConfig = rsaConfig;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers(
                                "/actuator/**",
                                "/USER-SERVICE/actuator/**",
                                "/USER-SERVICE/login",
                                "/MATERIELS-SERVICE/health",
                                "/PROJECT-SERVICE/health",
                                "/RESULTS-SERVICE/health"
                        ).permitAll()
                        .pathMatchers("/USER-SERVICE/**").hasAuthority("SCOPE_ADMIN")
                        .pathMatchers("/MATERIELS-SERVICE/**").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BIOLOGIST")
                        .pathMatchers(HttpMethod.POST,"/MATERIELS-SERVICE/**").hasAnyAuthority("SCOPE_ADMIN")
                        .pathMatchers("/PROJET-SERVICE/**").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BIOLOGIST")
                        .pathMatchers("/RESULTS-SERVICE/**").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_BIOLOGIST")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }




    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(rsaConfig.publicKey()).build();
    }


}
