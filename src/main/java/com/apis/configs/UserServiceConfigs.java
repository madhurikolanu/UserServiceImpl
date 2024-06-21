package com.apis.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Configuration
public class UserServiceConfigs {
    @Bean
    public BCryptPasswordEncoder getbCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
