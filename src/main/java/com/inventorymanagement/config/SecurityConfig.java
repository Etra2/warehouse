package com.inventorymanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Wyłączenie CSRF tylko na potrzeby testowe
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll() // Publiczne ścieżki
                        .anyRequest().authenticated() // Pozostałe wymagają uwierzytelnienia
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/") // Strona główna po zalogowaniu
                        .permitAll() // Dostęp dla wszystkich do strony logowania
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Endpoint wylogowania
                        .logoutSuccessUrl("/login") // Po wylogowaniu przekierowanie na login
                        .permitAll() // Dostępny dla wszystkich
                );
        return http.build();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                System.out.println("Encoding password: " + rawPassword);
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                System.out.println("Raw password: " + rawPassword);
                System.out.println("Encoded password: " + encodedPassword);
                boolean isMatch = rawPassword.toString().equals(encodedPassword);
                System.out.println("Password match: " + isMatch);
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}
