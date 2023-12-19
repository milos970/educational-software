package com.milos.numeric.methods.integration;

import com.milos.numeric.methods.nonlinear.Exp4jToUnivariateFunctionAdapter;
import com.milos.numeric.parameters.Parameters;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;

public class Trapezoid extends Integration
{
    private static final TrapezoidIntegrator integrator =  new TrapezoidIntegrator();

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
        sum += equationFunction.value(min) / 2;
        sum += equationFunction.value(max) / 2;

        for (int i = 1; i < numberOfSubintervals; i++)
        {
            double x_i = min + i * h;
            sum += equationFunction.value(x_i);

        }

        return h * sum;

    }
}
