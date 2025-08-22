package com.milos.numeric;

import com.milos.numeric.service.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class NumericApplication
{
	@Autowired
	private  SystemSettingsService systemSettingsService;


	public static void main(String[] args)
	{
		SpringApplication.run(NumericApplication.class, args);
	}









}

