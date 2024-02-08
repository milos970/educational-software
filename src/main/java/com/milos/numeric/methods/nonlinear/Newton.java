package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;

import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;

public class Newton extends NonLinear
{
    public Newton()
    {
        
    }

    @Override
    public double calculate(Parameters parameters)
    {
        String function = parameters.getExpression();
        String der = parameters.getDer();
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();
        double initial = parameters.getInitialValue();

        Exp4jToUnivariateFunctionAdapter equationFunction = new Exp4jToUnivariateFunctionAdapter(function);
        Exp4jToUnivariateFunctionAdapter derFunction = new Exp4jToUnivariateFunctionAdapter(der);

        double current = initial;

        for (int i = 0; i < iterations; ++i)
        {
            double next = current - (equationFunction.value(current) / derFunction.value(current));


            if (Double.compare(Math.abs(next - current), tolerance) <= 0)
            {
                return next;
            }

            current = next;
        }

        return Double.NaN;
    }

};


