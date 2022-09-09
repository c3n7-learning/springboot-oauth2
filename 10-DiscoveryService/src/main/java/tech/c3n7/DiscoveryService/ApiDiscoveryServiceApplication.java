package tech.c3n7.DiscoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ApiDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDiscoveryServiceApplication.class, args);
	}

}
