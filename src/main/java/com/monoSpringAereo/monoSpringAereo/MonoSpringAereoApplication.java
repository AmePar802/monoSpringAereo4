package com.monoSpringAereo.monoSpringAereo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MonoSpringAereoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonoSpringAereoApplication.class, args);
		System.out.println("In ascolto su porta 8096!");
	}

}
