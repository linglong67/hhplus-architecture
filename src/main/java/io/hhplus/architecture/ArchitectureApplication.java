package io.hhplus.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitectureApplication.class, args);
    }

}
