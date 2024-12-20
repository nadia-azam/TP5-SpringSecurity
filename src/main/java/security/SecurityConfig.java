package security;

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

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Utiliser la base de données pour authentifier les utilisateurs
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, true FROM user WHERE username = ?")
                .authoritiesByUsernameQuery(
                        "SELECT u.username, r.name " +
                                "FROM user u " +
                                "JOIN user_roles ur ON u.id = ur.user_id " +
                                "JOIN role r ON ur.role_id = r.id " +
                                "WHERE u.username = ?"
                )
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Désactiver CSRF pour simplifier les tests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()// Pages publiques
                        .requestMatchers("/admin").hasRole("ADMIN") // Restriction pour ADMIN
                        .anyRequest().authenticated() // Toute autre page nécessite une authentification
                )
                .formLogin(login -> login
                        .loginPage("/login") // Page de connexion
                        .defaultSuccessUrl("/") // Redirection après connexion réussie
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
