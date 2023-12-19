package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.RegulaFalsiSolver;

import static java.lang.Double.NaN;

public class RegulaFalsi extends NonLinear
{

    public RegulaFalsi()
    {

    }


    @Override
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

            double c = (min * equationFunction.value(max) - max * equationFunction.value(min)) / (equationFunction.value(max) - equationFunction.value(min));

            if (Math.abs(equationFunction.value(c)) < tolerance)
            {
                return c;
            }

            if (equationFunction.value(c) * equationFunction.value(min) < 0)
            {
                max = c;
            } else {
                min = c;
            }


        }

        return NaN;
    }


}
