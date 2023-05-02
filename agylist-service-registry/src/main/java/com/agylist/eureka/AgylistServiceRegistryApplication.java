package com.agylist.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AgylistServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgylistServiceRegistryApplication.class, args);
	}

}
