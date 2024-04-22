package com.milos.numeric;

import com.milos.numeric.repositories.StudentRepository;
import com.milos.numeric.services.SystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


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



	@EventListener(ApplicationReadyEvent.class)
	public void executeAfterStartup()
	{
		this.systemSettingsService.incrementDays();
	}





}

