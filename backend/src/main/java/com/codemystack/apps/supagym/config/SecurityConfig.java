package com.codemystack.apps.supagym.config;

import com.codemystack.apps.supagym.multitenancy.TenantIdentifierResolver;
import com.codemystack.apps.supagym.repositories.tenant.TenantRepository;
import com.codemystack.apps.supagym.security.JwtService;
import com.codemystack.apps.supagym.security.TenantAndJwtFilter;
import com.codemystack.apps.supagym.security.TenantUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           TenantRepository tenantRepository,
                                           TenantIdentifierResolver tenantIdentifierResolver,
                                           JwtService jwtService,
                                           TenantUserDetailsService userDetailsService) throws Exception {

        TenantAndJwtFilter tenantAndJwtFilter = new TenantAndJwtFilter(
                tenantRepository, tenantIdentifierResolver, jwtService, userDetailsService
        );

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .addFilterBefore(tenantAndJwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
