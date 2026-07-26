package com.cloud.oms.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Value("${cloud-oms-db-url:NOT_FOUND}")
    private String dbUrl;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

    @Override
    public void run(String... args) {
        log.info("Resolved DB URL: " + dbUrl);
    }

}
	