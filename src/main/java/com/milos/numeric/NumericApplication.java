package com.milos.numeric;

import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NumericApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(NumericApplication.class, args);
	}

}
