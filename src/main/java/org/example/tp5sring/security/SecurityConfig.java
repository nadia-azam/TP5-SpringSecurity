package org.example.tp5sring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Désactiver CSRF pour simplifier les tests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/hello").permitAll()// Restriction pour ADMIN
                        .anyRequest().authenticated() // Toute autre page nécessite une authentification
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/hello"); // Redirection manuelle après une connexion réussie
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de déconnexion
                        .logoutSuccessUrl("/login") // Redirection après déconnexion
                        .permitAll()
                );

        return http.build();
    }
}
