package com.milos.numeric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class NumericApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(NumericApplication.class, args);
	}

}
