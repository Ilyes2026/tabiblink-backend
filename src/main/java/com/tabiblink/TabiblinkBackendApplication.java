package com.tabiblink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TabiblinkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabiblinkBackendApplication.class, args);
	}
}