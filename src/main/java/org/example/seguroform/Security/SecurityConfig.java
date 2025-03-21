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
                                ,"/css/**", "/images/**", "/presentation/usuarios/login", "/presentation/login/ViewLogin",
                                "/presentation/citas/confirmarCita", "/presentation/citas/showCitas",
                                "/presentation/confirmarCita/ViewconfirmarCita","/presentation/medicoGestionCitas/ViewmedicoGestionCitas",
                                "/presentation/medicos/confirm/**","/presentation/medicoGestionCitas/ViewmedicoGestionCitas",
                                "/presentation/citas/searchPatName","/presentation/citas/showHPC", "/presentation/citas/searchDrName",
                                "/presentation/citas/actualizarEstadoCita","/presentation/citas/attendCita",
                                "/presentation/usuarios/loginWrong","/presentation/usuarios/register",
                                "/presentation/usuarios/userExists","/presentation/usuarios/userRegister",
                                "/presentation/administradores/about", "/presentation/aboutUs/aboutUs"
                                ,"/presentation/medicos/showExtendido", "/presentation/buscarCita/ViewBuscarHorarioExtendido"
                                ,"/presentation/medicos/search", "/presentation/medicos/showFilter", "/presentation/citas/showConfirmar",
                                "/presentation/pacienteHistoricoCitas/ViewPacienteHistoricoCitas").permitAll()

                ).formLogin(customizer -> customizer
                        .loginPage("/presentation/buscarCita/ViewbuscarCita").permitAll()).csrf(customizer -> customizer.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }
}
