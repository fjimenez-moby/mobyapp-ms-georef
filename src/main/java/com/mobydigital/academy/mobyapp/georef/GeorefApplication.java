package com.mobydigital.academy.mobyapp.georef;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRetry
public class GeorefApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeorefApplication.class, args);
	}

}
