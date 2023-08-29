package com.ktb.clmapiauthen.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.ktb.clmapiauthen.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
	private AuthEntryPointJwt unauthorizedHandler; 
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers(
                    "/api/auth/**",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html").permitAll()
            .requestMatchers("/api/simple/**").authenticated()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // .and()
            // .authenticationProvider(authenticationProvider)
            // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            // .logout()
            // .logoutUrl("/api/v1/auth/logout")
            // .addLogoutHandler(logoutHandler)
            // .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        // http.headers().addHeaderWriter(
        //                 new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
        //         .addHeaderWriter(
        //                 new StaticHeadersWriter("Strict-Transport-Security","max-age=31536000 ; includeSubDomains"))
        //         .addHeaderWriter(
        //                 new StaticHeadersWriter("Set-Cookie","HttpOnly;Secure;"));

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
    
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
