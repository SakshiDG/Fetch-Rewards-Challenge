package com.example.fetch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class FetchApplication {



	public static void main(String[] args) {
		SpringApplication.run(FetchApplication.class, args);
		System.out.println("Hello");

	}

}
