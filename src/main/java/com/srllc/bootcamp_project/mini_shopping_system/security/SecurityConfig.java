package com.srllc.bootcamp_project.mini_shopping_system.security;

import com.srllc.bootcamp_project.mini_shopping_system.security.jwt.JWTAuthenticationEntryPoint;
import com.srllc.bootcamp_project.mini_shopping_system.security.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Public endpoints
    private static final String[] PUBLIC_ENDPOINTS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/auth/register",
            "/api/auth/login"
    };

    // Public product viewing endpoints
    private static final String[] PUBLIC_PRODUCT_ENDPOINTS = {
            "/api/products",        // GET all products
            "/api/products/**"      // GET specific product
    };

    // Customer endpoints
    private static final String[] CUSTOMER_ENDPOINTS = {
            "/api/orders/**"        // Customers can create and view their orders
    };

    // Admin product management endpoints
    private static final String[] ADMIN_PRODUCT_ENDPOINTS = {
            "/api/products",        // POST create product
            "/api/products/**"      // PUT/DELETE specific product
    };

    // Admin endpoints
    private static final String[] ADMIN_ENDPOINTS = {
            "/api/admin/orders/**"  // Admin can view all orders and update status
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no authentication required)
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()

                        // Public product viewing endpoints (GET only)
                        .requestMatchers(HttpMethod.GET, PUBLIC_PRODUCT_ENDPOINTS).permitAll()

                        // Admin-only product management endpoints
                        .requestMatchers(HttpMethod.POST, ADMIN_PRODUCT_ENDPOINTS).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, ADMIN_PRODUCT_ENDPOINTS).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, ADMIN_PRODUCT_ENDPOINTS).hasAuthority("ROLE_ADMIN")

                        // Admin order management endpoints
                        .requestMatchers(ADMIN_ENDPOINTS).hasAuthority("ROLE_ADMIN")

                        // Customer-only order endpoints
                        .requestMatchers(CUSTOMER_ENDPOINTS).hasAuthority("ROLE_CUSTOMER")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}