package com.silverrail.stringstate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StringStateApplication {

	public static void main(String[] args) {
		SpringApplication.run(StringStateApplication.class, args);
	}
}
