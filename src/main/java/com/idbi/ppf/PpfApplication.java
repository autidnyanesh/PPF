package com.idbi.ppf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.idbi.gbm", "com.idbi.ppf"})
@EntityScan(basePackages = {"com.idbi.gbm.entity", "com.idbi.ppf.entity"})
@EnableJpaRepositories(basePackages = {"com.idbi.gbm.repository", "com.idbi.ppf.repository"})
public class PpfApplication {
    public static void main(String[] args) {
        SpringApplication.run(PpfApplication.class, args);
    }
}

