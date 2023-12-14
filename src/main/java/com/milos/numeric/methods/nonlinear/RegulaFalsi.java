package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.parameters.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.RegulaFalsiSolver;

public class RegulaFalsi extends NonLinear
{

    public RegulaFalsi()
    {

    }


    @Override
    public double calculate(Parameters parameters)
    {
        String function = parameters.getExpression();
        double min = parameters.getMin();
        double max = parameters.getMax();
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();
        RegulaFalsiSolver solver = new RegulaFalsiSolver(tolerance);
        UnivariateFunction fun = new Exp4jToUnivariateFunctionAdapter(function);
        return solver.solve(iterations, fun, min, max);
    }


}
