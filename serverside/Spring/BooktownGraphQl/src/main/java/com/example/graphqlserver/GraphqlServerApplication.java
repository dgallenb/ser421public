package com.example.graphqlserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@SpringBootApplication
@EntityScan("com.example.graphqlserver.model")
public class GraphqlServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlServerApplication.class, args);
	}

}
