package com.comps.companion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.comps.companion.service.DataSimulationService;


@SpringBootApplication
public class CompanionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanionApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(DataSimulationService dataSimulationService) {

		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				dataSimulationService.generateMockData(10);
			}
		};
	}
}
