package org.example.seguroform.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/","/presentation/buscarCita/ViewbuscarCita","/presentation/medicos/show"
                                ,"/css/**", "/images/**").permitAll()
                ).formLogin(customizer -> customizer
                        .loginPage("/presentation/buscarCita/ViewbuscarCita").permitAll()).csrf(customizer -> customizer.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }
}
