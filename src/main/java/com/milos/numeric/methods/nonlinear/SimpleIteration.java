package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class SimpleIteration extends NonLinear
{

    @Override
    public double calculate(Parameters parameters)
    {
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();
        double currentValue = parameters.getX0();
        String function = parameters.getExpression();
        UnivariateFunction fun = new Exp4jToUnivariateFunctionAdapter(function);

        for (int i = 0; i < Integer.MAX_VALUE; ++i)
        {
             double nextValue = fun.value(currentValue);


            if (Math.abs(nextValue - currentValue) < tolerance) {
                return nextValue;
            }

            currentValue = nextValue;
        }

        return Double.NaN;
    }

}
