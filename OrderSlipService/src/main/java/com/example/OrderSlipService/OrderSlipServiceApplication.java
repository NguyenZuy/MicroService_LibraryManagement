package com.example.OrderSlipService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderSlipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSlipServiceApplication.class, args);
	}

}
