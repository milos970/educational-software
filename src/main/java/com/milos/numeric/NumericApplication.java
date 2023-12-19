package com.milos.numeric;

import com.milos.numeric.methods.integration.Simpson;
import com.milos.numeric.methods.integration.Trapezoid;
import com.milos.numeric.methods.nonlinear.Bisection;
import com.milos.numeric.methods.nonlinear.Newton;
import com.milos.numeric.methods.nonlinear.RegulaFalsi;
import com.milos.numeric.methods.nonlinear.SimpleIteration;
import com.milos.numeric.parameters.Parameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumericApplication {

	public static void main(String[] args)
	{
		Newton si = new Newton();
		Parameters p =Parameters.builder().lower(-1.2).upper(-1.1).iterations(8).
				tolerance(0.01).expression("-(e^x + 1)^(1/2)")
				.build();
		System.out.println(si.calculate(p));
		//SpringApplication.run(NumericApplication.class, args);

	}

}
