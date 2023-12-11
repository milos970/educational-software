package com.milos.numeric;

import com.milos.numeric.methods.DataValidationTest;
import com.milos.numeric.methods.nonlinear.Bisection;
import com.milos.numeric.methods.nonlinear.Newton;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class NumericApplicationTests {

	@Test
	void contextLoads()
	{
		Newton bi = new Newton();
		String equationString = "10 * cos(x - 1) - x * x + 2 * x - 1";
		Parameters p = Parameters.builder().function(equationString)
				.min(2.3).max(2.4).iterations(10000000).tolerance(0.000010).build();
		System.out.print(bi.calculate(p));
	}

}
