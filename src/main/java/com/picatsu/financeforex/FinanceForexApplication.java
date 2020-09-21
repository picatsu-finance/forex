package com.picatsu.financeforex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceForexApplication {
	// http://localhost:8002/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
	public static void main(String[] args) {
		SpringApplication.run(FinanceForexApplication.class, args);
	}

}
