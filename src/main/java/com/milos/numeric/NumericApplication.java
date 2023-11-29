package com.milos.numeric;

import com.milos.numeric.methods.NonLinear;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;
import java.text.NumberFormat;

@SpringBootApplication
public class NumericApplication {

	public static void main(String[] args)
	{

		Parameters parameters = Parameters.builder().a0(4).b0(5).build();


		NonLinear en = NonLinear.NEWTON;
		NumberFormat formatter = new DecimalFormat("#0.00000000");
		//System.out.println(formatter.format(en.calculate(null, 2.4,0.00001)));
		SpringApplication.run(NumericApplication.class, args);

	}

}
