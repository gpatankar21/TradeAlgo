package com.projects.stock.TradeAlgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TradeAlgoApplication {

	@Bean
	TradeDirectory getTradeDirectory()
	{
		return new TradeDirectory();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TradeAlgoApplication.class, args);
	}

}
