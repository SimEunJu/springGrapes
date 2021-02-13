package com.sej.grapes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GrapesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrapesApplication.class, args);
    }

}
