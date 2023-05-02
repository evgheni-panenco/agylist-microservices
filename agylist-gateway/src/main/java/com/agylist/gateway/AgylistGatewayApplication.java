package com.agylist.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AgylistGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgylistGatewayApplication.class, args);
	}

}
