package com.milos.numeric;

import com.milos.numeric.methods.nonlinear.Bisection;
import com.milos.numeric.methods.nonlinear.RegulaFalsi;
import com.milos.numeric.methods.nonlinear.SimpleIteration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumericApplication {

	public static void main(String[] args)
	{
		Bisection si = new Bisection();
		Parameters p = new Parameters();
		p.setTolerance(0.05);
		p.setMin(1.5);
		p.setMax(2);
		p.setIterations(100);
		p.setExpression("(x/2)^2 - sin x");
		System.out.println(si.calculate(p));
		//SpringApplication.run(NumericApplication.class, args);

	}

}
