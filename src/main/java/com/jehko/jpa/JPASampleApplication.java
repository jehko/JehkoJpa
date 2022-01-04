package com.jehko.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class JPASampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JPASampleApplication.class, args);
	}

}
