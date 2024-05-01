package com.example.ImportSlipService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ImportSlipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportSlipServiceApplication.class, args);
	}

}
