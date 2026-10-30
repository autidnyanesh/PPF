package com.idbi.ppf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.idbi.ppf", exclude = UserDetailsServiceAutoConfiguration.class)
public class PpfApplication {
    public static void main(String[] args) {
        SpringApplication.run(PpfApplication.class, args);
    }
}
