package com.milos.numeric.methods.integration;

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
        double min = parameters.getMin();
        double max = parameters.getMax();
        JexlExpression expression = jexl.createExpression(function);
        UnivariateFunction fun = x -> evaluateExpression(expression, x);
        return integrator.integrate(iterations, fun, min, max);
    }
}
