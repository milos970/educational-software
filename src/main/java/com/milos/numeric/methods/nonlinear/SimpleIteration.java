package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;

public class SimpleIteration extends NonLinear
{

    @Override
    public double calculate(Parameters parameters)
    {
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();
        double currentValue = parameters.getInitialValue();
        String function = parameters.getExpression();
        Exp4jToUnivariateFunctionAdapter equationFunction = new Exp4jToUnivariateFunctionAdapter(function);

        for (int i = 0; i < iterations; ++i)
        {
             double nextValue = equationFunction.value(currentValue);


            if (Double.compare(Math.abs(nextValue - currentValue), tolerance) <= 0) {
                return nextValue;
            }

            currentValue = nextValue;
        }

        return Double.NaN;
    }

}
