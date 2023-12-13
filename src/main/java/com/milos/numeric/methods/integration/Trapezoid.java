package com.milos.numeric.methods.integration;

import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;

public class Trapezoid extends Integration
{
    private static final TrapezoidIntegrator integrator =  new TrapezoidIntegrator();

    @Override
    public double calculate(String function, double lower, double upper, int maxEval)
    {
        JexlExpression expression = jexl.createExpression(function);
        UnivariateFunction fun = x -> evaluateExpression(expression, x);
        return integrator.integrate(maxEval, fun, lower, upper);
    }
}
