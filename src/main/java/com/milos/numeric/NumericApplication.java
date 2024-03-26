package com.milos.numeric;

import com.milos.numeric.email.EmailServiceImpl;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
public class NumericApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(NumericApplication.class, args);
	}
}
