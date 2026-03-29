package com.roommatefinder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoommateFinderBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RoommateFinderBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("starting");
	}
}
