package com.Ironhack.FinalProject.security;

import com.Ironhack.FinalProject.securityservice.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.PATCH, "/transfer").hasAnyRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.PATCH, "/new_password").hasAnyRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.GET,"/show_account/{id}").hasRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.GET,"/show_accounts").hasRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST,"/new_address").hasRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST,"/new_mailing_address").hasRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST,"/new_password").hasRole("ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.PATCH,"/add_balance").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/decrease_balance").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_user_account").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_account").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/change_status").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/delete_account/{accountNumber}").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/show_users").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/show_accounts").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/assign_secondary_owner").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_admin").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_third_party").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_address").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/create_mailing_address").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PATCH,"/change_password").hasRole("ADMIN")
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();

    }

}

