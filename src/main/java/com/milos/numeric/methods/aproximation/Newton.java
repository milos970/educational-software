package com.milos.numeric.methods.aproximation;

import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

public class Newton implements Aproximation {
    private final DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();
    private final PolynomialFunctionNewtonForm newtonPolynomial;

    public Newton(PolynomialFunctionNewtonForm newtonPolynomial) {
        this.newtonPolynomial = newtonPolynomial;
    }

    @Override
    public double[] calculate(double[] points)
    {
        double[] y = new double[points.length];

        for (int i = 0; i < points.length; ++i)
        {
            y[i] = this.newtonPolynomial.value(points[i]);
        }
        return y;
    }

    @Override
    public String makeFunction()
    {
        return newtonPolynomial.toString();
    }
}
