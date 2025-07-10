package br.edu.ufrn.distances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DistancesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistancesApplication.class, args);
	}

}
