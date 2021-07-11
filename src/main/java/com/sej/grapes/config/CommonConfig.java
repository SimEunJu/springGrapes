package com.sej.grapes.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class CommonConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
