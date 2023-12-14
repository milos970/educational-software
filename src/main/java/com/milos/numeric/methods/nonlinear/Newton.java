package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;
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
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();
        double initial = parameters.getX0();
        NewtonRaphsonSolver solver = new NewtonRaphsonSolver(tolerance);
        UnivariateDifferentiableFunction der = new Exp4jToUnivariateDifferentiableFunctionAdapter(function);
        UnivariateFunction fun = new Exp4jToUnivariateFunctionAdapter(function);

        return solver.solve(iterations, der, initial);
    }
/*public double calculate(String function, double xk_1, double E)
    {
        double xk = 0.0;

        Argument x = new Argument("x");
        Function f = new Function("f(x) = " + function);


        for (int i = 1; i < Integer.MAX_VALUE; ++i)
        {
            x.setArgumentValue(xk_1);
            double der = new Expression("der(" + function + ",x)",x).calculate();
            double fun = new Expression("f(" + xk_1 + ")",f).calculate();
            xk = xk_1 -  (fun / der);

            if (Math.abs(xk - xk_1) <= E)
            {
                break;
            }

            xk_1 = xk;

        }

        return xk;

    }*/
};


