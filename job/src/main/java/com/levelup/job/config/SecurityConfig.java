package com.levelup.job.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.job.security.authentication.web.JwtAuthenticationFilter;
import com.levelup.job.security.authorization.AuthorizationAccessDeniedHandler;
import com.levelup.job.security.authorization.AuthorizationNoAuthenticationHandler;
import com.levelup.job.security.authorization.JwtAuthorizationFilter;
import com.levelup.job.util.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final AuthorizationNoAuthenticationHandler authorizationNoAuthenticationHandler;
    private final AuthorizationAccessDeniedHandler authorizationAccessDeniedHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
                .logout().disable()
                .httpBasic().disable()
                .formLogin().disable();

        http.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/api/*/jobs/crawling/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/*/jobs").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/*/jobs").hasRole("ADMIN")
                .anyRequest().permitAll();

        http.exceptionHandling()
                .authenticationEntryPoint(authorizationNoAuthenticationHandler)
                .accessDeniedHandler(authorizationAccessDeniedHandler);

        http
                .addFilterAfter(jwtAuthenticationFilter(), CorsFilter.class)
                .addFilterAfter(jwtAuthorizationFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(objectMapper, tokenProvider);

        authenticationFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        authenticationFilter.setFilterProcessesUrl("/api/*/login");
        return authenticationFilter;
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);

        return configurationSource;
    }
}
