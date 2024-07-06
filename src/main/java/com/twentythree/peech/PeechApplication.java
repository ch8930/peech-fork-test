package com.twentythree.peech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync    // 비동기 처리를 위한 어노테이션
@SpringBootApplication
public class PeechApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeechApplication.class, args);
    }
}
