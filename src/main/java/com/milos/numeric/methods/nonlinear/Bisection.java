package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;

import static java.lang.Double.NaN;

public class Bisection extends NonLinear {


    public Bisection()
    {

    }

    public double calculate(Parameters parameters)
    {
        String function = parameters.getExpression();
        double min = parameters.getLower();
        double max = parameters.getUpper();
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();

        Exp4jToUnivariateFunctionAdapter equationFunction = new Exp4jToUnivariateFunctionAdapter(function);

        for (int k = 0; k < iterations; k++)
        {
            double xk = (Math.pow(min,2) + Math.pow(max,2)) / 2;
            double fk = equationFunction.value(xk);
            double ak = Math.pow(min, 2);
            if (fk == 0)
            {
                return xk;
            }

            double ak1 = 0;
            double bk1 = 0;
            if ( (equationFunction.value(ak) * equationFunction.value(xk)) < 0)
            {
                ak1 = ak;
                bk1 = xk;
            }

            double bk = Math.pow(max, 2);

            if ( (equationFunction.value(bk) * equationFunction.value(xk)) < 0)
            {
                ak1 = xk;
                bk1 = bk;
            }

            if ((bk1 - ak1) / 2 <= tolerance)
            {
                return xk;
            }


        }

        return NaN;

    }
}
