package com.warungsaham.warungsahamappapi.config;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.warungsaham.warungsahamappapi.global.middleware.auth.AuthTokenFilter;
import com.warungsaham.warungsahamappapi.security.auth.CustomDaoAuthProvider;
import com.warungsaham.warungsahamappapi.user.service.auth.AuthService;
import com.warungsaham.warungsahamappapi.utils.auth.AuthEntryPoint;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private AuthService authService;

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public CustomDaoAuthProvider authenticationProvider(){
        CustomDaoAuthProvider authenticationProvider = new CustomDaoAuthProvider();
        
        authenticationProvider.setUserDetailsService(authService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setHideUserNotFoundExceptions(false);

        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
            .exceptionHandling(
                exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(authEntryPoint)   
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/api/v1/auth/**","/api/v1/role/**","/error","/swagger-ui/**","/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/user/**",
                                        "/api/v1/premium/**",
                                        "/api/v1/stock/**",
                                        "/api/v1/rekom/**",
                                        "/api/v1/insight/**")
                                        .hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated()
            );

            http.authenticationProvider(authenticationProvider());
            http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        

        return http.build();
    }

}
