package org.bidwik.bid.Security;

import org.bidwik.bid.Configuration.CorsMvcConfiguration;
import org.bidwik.bid.Security.Component.AuthenticationEntryPointJwt;
import org.bidwik.bid.Security.Component.LogoutHandlerJwt;
import org.bidwik.bid.Security.Component.OncePerRequestFilterJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final OncePerRequestFilterJwt oncePerRequestFilterJwt;
    private final AuthenticationEntryPointJwt authenticationEntryPointJwt;
    private final AuthenticationProvider authenticationProvider;
    private final CorsMvcConfiguration corsMvcConfiguration;
    private final LogoutHandlerJwt logoutHandlerJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(CsrfConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsMvcConfiguration.corsConfigurationSource()))
            .authorizeHttpRequests(
                request -> request.anyRequest().permitAll()
            )
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPointJwt))
            .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                oncePerRequestFilterJwt,
                UsernamePasswordAuthenticationFilter.class
                )
            .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandlerJwt)
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().flush();
                })
                .permitAll()
            )
            .build();
    }
}
