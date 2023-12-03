package com.milos.numeric;

import com.milos.numeric.methods.Aproximation;
import com.milos.numeric.methods.NonLinear;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class NumericApplication {

	public static void main(String[] args)
	{

		Parameters parameters = Parameters.builder().a0(4).b0(5).build();


		NonLinear en = NonLinear.NEWTON;
		NumberFormat formatter = new DecimalFormat("#0.00000000");
		//System.out.println(formatter.format(en.calculate(null, 2.4,0.00001)));

		/*List<Entry> list = new LinkedList<>();
		list.add(new Entry(1,2));
		list.add(new Entry(3,6));
		list.add(new Entry(5,12));
		System.out.print(Aproximation.LANGRANGEOV.makeEquation(list));*/
		System.out.print(Aproximation.NEWTON.recursion(2,1,-1,-2));

		//SpringApplication.run(NumericApplication.class, args);

	}

}
