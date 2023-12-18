package com.milos.numeric;

import com.milos.numeric.methods.nonlinear.Bisection;
import com.milos.numeric.methods.nonlinear.Newton;
import com.milos.numeric.methods.nonlinear.RegulaFalsi;
import com.milos.numeric.parameters.Parameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumericApplication {

	public static void main(String[] args)
	{
		Newton si = new Newton();
		Parameters p =Parameters.builder().iterations(4).initialValue(2).expression("10cos(x-1)-x^2 + 2x - 1").der("-10sin(x-1)-2x+2").tolerance(0.000001).build();
		System.out.println(si.calculate(p));
		//SpringApplication.run(NumericApplication.class, args);

	}

}
