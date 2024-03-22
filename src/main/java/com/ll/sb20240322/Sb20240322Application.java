package com.ll.sb20240322;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Sb20240322Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb20240322Application.class, args);
    }

}
