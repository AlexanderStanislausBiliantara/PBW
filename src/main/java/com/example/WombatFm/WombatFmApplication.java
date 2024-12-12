package com.example.WombatFm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class WombatFmApplication {

	public static void main(String[] args) {
		SpringApplication.run(WombatFmApplication.class, args);
	}

}
