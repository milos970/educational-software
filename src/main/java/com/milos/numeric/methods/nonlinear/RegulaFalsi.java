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

            double ak = Math.pow(min, k);
            double bk = Math.pow(max, k);

            double fbk = equationFunction.value(bk);
            double fak = equationFunction.value(ak);

            double xk = ak - (bk -ak) / ((fbk - fak)) * fak;

            double fxk = equationFunction.value(xk);
            if (fxk == 0)
            {
                return xk;
            }

            double ak1 = 0;
            double bk1 = 0;
            if ( (fak * fxk) < 0)
            {
                ak1 = ak;
                bk1 = xk;
            }



            if ( (fxk * fbk) < 0)
            {
                ak1 = xk;
                bk1 = bk;
            }

            if ((Math.abs(xk - )) / 2 <= tolerance)
            {
                return xk;
            }


        }

        return NaN;
    }


}
