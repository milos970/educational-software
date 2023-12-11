package com.milos.numeric.methods.nonlinear;

import com.milos.numeric.Parameters;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;

public class RegulaFalsi extends NonLinear
{

    public RegulaFalsi()
    {

    }


    @Override
    public double calculate(Parameters parameters)
    {
        String function = parameters.getFunction();
        double min = parameters.getMin();
        double max = parameters.getMax();
        int iterations = parameters.getIterations();
        double tolerance = parameters.getTolerance();
        BisectionSolver solver = new BisectionSolver(tolerance);
        UnivariateFunction fun = new Exp4jToUnivariateFunctionAdapter(function);
        return solver.solve(iterations, fun, min, max);
    }


}
