package com.inventorymanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                        .requestMatchers("/login", "/register.html", "/css/**", "/registerUser").permitAll() // Publiczne ścieżki
                        .anyRequest().authenticated() // Pozostałe wymagają uwierzytelnienia
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index") // Strona główna po zalogowaniu
                        .failureUrl("/login?error=true")
                        .permitAll() // Dostęp dla wszystkich do strony logowania
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") //
                        .logoutSuccessUrl("/login")
                        .permitAll()
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
        return new BCryptPasswordEncoder();
    }
}
