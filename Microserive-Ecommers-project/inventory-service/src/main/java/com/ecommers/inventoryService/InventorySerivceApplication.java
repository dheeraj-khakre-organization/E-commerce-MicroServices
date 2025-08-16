package com.ecommers.inventoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventorySerivceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorySerivceApplication.class, args);
	}

}
