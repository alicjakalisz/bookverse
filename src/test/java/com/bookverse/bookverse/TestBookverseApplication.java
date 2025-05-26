package com.bookverse.bookverse;

import org.springframework.boot.SpringApplication;

public class TestBookverseApplication {

	public static void main(String[] args) {
		SpringApplication.from(BookverseApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
