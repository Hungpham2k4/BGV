package com.bagalaxy.BGV.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Điều chỉnh domain nếu cần
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                            .requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/movies").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/movies/genre").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/movies/filter").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/genres").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/favorites").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/comments/movie/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/ratings/movie/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/movies/search").permitAll()
                            .requestMatchers(HttpMethod.GET , "/api/movies/{id}").permitAll()

                            .requestMatchers(HttpMethod.POST, "/api/movies").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/movies/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/movies/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/genres").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/genres/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/genres/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
