//clase principal que inicia la aplicación Spring Boot
package com.example.mutant_detector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantDetectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutantDetectorApplication.class, args);
	}

}
