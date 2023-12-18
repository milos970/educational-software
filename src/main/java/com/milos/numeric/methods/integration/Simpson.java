package com.milos.numeric.methods.integration;

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
        JexlExpression expression = jexl.createExpression(function);
        UnivariateFunction fun = x -> evaluateExpression(expression, x);
        return integrator.integrate(iterations, fun, min, max);
    }
}
