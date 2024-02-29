package com.meter.meter_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
public class MeterModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeterModuleApplication.class, args);
	}

}
