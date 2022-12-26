package com.nemirovsky.passportocrwebflux.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
    @Bean
    SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
        // logout handler
        DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
                new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
        );

        // main ServerHttpSecurity config
        http
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/static/**", "/signup", "/login", "/about").permitAll()
                        .pathMatchers("/users/**").authenticated()
                        .pathMatchers("/admin/**").hasRole("ADMIN") // don't need to add "ROLE_"
                        .anyExchange().denyAll())
                .httpBasic(withDefaults())
                .formLogin((form) -> form
                        .loginPage("/login")
                )
                .logout((logout) -> logout.logoutHandler(logoutHandler));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
