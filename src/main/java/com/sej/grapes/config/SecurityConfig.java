package com.sej.grapes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.dto.jwt.TokenDto;
import com.sej.grapes.security.jwt.*;
import com.sej.grapes.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        Map<String, CorsConfiguration> configurations = new HashMap<>();
        configurations.put("/api/**", config);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.setCorsConfigurations(configurations);

        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/error")
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())

                // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#servlet-exceptiontranslationfilter
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin()
                .loginProcessingUrl("/api/authenticate")
                .successHandler(new JwtAuthenticationSuccessHandler(tokenProvider, refreshTokenService))

                .and()
                .authorizeRequests()

                .antMatchers("/api/authenticate").anonymous()
                .antMatchers("/swagger-ui/**").anonymous()
                .anyRequest().authenticated()
                ;

                //.and()
                //.apply(new JwtSecurityConfig(tokenProvider, refreshTokenService));
    }

}

