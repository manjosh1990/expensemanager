package com.xpense.services.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class XpenseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(XpenseServiceApplication.class,args);
    }
}
