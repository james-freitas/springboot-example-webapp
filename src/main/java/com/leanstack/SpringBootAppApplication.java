package com.leanstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableCaching
@EnableScheduling
@EnableAsync

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan

public class SpringBootAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootAppApplication.class, args);

	}

	@Bean
	public CacheManager cacheManager(){
		GuavaCacheManager cacheManager = new GuavaCacheManager("greetings");
		return cacheManager;
	}
}
