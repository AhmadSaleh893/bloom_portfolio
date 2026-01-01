package com.portfolio.bloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

import java.util.TimeZone;

@SpringBootApplication
public class BloomApplication {

	@PostConstruct
	public void init() {
		// Force UTC timezone for the entire application
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		// Set UTC timezone before Spring starts
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(BloomApplication.class, args);
	}
}
