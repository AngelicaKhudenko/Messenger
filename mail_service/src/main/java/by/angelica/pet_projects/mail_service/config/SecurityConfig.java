package by.angelica.pet_projects.mail_service.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {

        http = http.cors().and().csrf().disable();


        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.setStatus(
                                    HttpServletResponse.SC_UNAUTHORIZED
                            );
                        }
                )
                .accessDeniedHandler((request, response, ex) -> {
                    response.setStatus(
                            HttpServletResponse.SC_FORBIDDEN
                    );
                })
                .and();


        http.authorizeHttpRequests(requests -> requests

                .requestMatchers("/users/details").authenticated()
                .requestMatchers("/users/**").hasAnyRole("ADMIN")

                .requestMatchers("/cabinet/me","cabinet/friends","cabinet/friends/**","cabinet/password").authenticated()

                .requestMatchers("/cabinet/registration","/cabinet/verification","/cabinet/login").permitAll()

                .anyRequest().authenticated()
        );

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}


