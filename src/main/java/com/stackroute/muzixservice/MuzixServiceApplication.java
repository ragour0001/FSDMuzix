package com.stackroute.muzixservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*
@Configuration // used to define beans
@EnableAutoConfiguration //
@ComponentScan*/
@SpringBootApplication
public class MuzixServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuzixServiceApplication.class, args);
	}

}
