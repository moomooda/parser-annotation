package com.javatechie.spring.ajax.api;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@Configuration
@SpringBootApplication
@EnableJpaAuditing
public class SpringAjaxApplication {
	
	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAjaxApplication.class, args);
	}
}
