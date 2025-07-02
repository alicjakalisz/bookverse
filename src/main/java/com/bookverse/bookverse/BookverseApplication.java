package com.bookverse.bookverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.bookverse.bookverse.model")
public class BookverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookverseApplication.class, args);
	}

}
