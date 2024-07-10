package com.oncemore.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@SpringBootApplication
@EnableWebMvc
public class OnceMoreStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnceMoreStoreApplication.class, args);
    }
}
