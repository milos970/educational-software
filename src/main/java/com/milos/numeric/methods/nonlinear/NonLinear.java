package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;

public abstract class NonLinear
{
    public abstract double calculate(Parameters parameters);

    public static void validate(Parameters parameters)
    {
        double a = parameters.getLower();
        double b = parameters.getUpper();

        if (a >= b)
        {

            //exception: lower is higher than upper
        }

        double tolerance = parameters.getTolerance();

        if (tolerance < 0)
        {
            //exception: invalid tolerance
        }

        int iterations = parameters.getIterations();

        if (iterations > 1)
        {
            //exception: invalid iterations
        }
    }
}
