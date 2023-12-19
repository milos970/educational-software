package com.milos.numeric.methods.integration;

import com.milos.numeric.methods.nonlinear.Exp4jToUnivariateFunctionAdapter;
import com.milos.numeric.parameters.Parameters;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;

public class Simpson extends Integration
{
    private static final SimpsonIntegrator integrator = new SimpsonIntegrator();

    @Override
    public double calculate(Parameters parameters)
    {
        String function = parameters.getExpression();
        double tolerance = parameters.getTolerance();
        int iterations = parameters.getIterations();
        double min = parameters.getLower();
        double max = parameters.getUpper();

        int numberOfSubintervals = parameters.getIntervals();
        Exp4jToUnivariateFunctionAdapter equationFunction = new Exp4jToUnivariateFunctionAdapter(function);

        double h = (max - min) / numberOfSubintervals;

        double sum = 0;
        sum += equationFunction.value(min);
        sum += equationFunction.value(max);

        for (int i = 1; i < numberOfSubintervals; i++)
        {
            double x_i = min + i * h;

            if (i % 2 == 0)
            {
                sum += 2 * equationFunction.value(x_i);
            } else
            {
                sum += 4 * equationFunction.value(x_i);
            }

        }

        return (h / 3) * sum;
    }
}
