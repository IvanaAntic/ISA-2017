package com.example.isa2017;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableScheduling
public class Isa2017Application {

	public static void main(String[] args) {
		SpringApplication.run(Isa2017Application.class, args);
	}
}
//proba
//proba2
//ivana kraljica
//Test, Test