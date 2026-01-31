package com.blackshuck.bookFlight.config;

import com.blackshuck.bookFlight.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig
{
    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final String[] publicUrl =
            {
                    "/",
                    "/index",
                    "/sign-up",
                    "/sign-up/**",
                    "/resources/**",
                    "/assets/**",
                    "/css/**",
                    "/js/**",
                    "/*.css",
                    "/*.js",
            };

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService,
                             CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler)
    {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.authenticationProvider(authenticationProvider());

        httpSecurity.authorizeHttpRequests(auth ->
        {
            auth.requestMatchers(publicUrl).permitAll();
            auth.anyRequest().authenticated();
        });

        httpSecurity.formLogin(form -> form.loginPage("/login")
                .failureUrl("/login?error=true").permitAll()
                .successHandler(customAuthenticationSuccessHandler)).logout(logout ->
                {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/");
                })
                .cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable());


        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        return daoAuthenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
