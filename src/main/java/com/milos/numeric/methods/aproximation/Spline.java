package com.milos.numeric.methods.aproximation;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class Spline implements Aproximation
{
    private final PolynomialSplineFunction spline;

    public Spline(PolynomialSplineFunction spline)
    {
        this.spline = spline;
    }

    @Override
    public double[] calculate(double[] points)
    {
        double[] y = new double[points.length];

        for (int i = 0; i < points.length; ++i)
        {
            y[i] = this.spline.value(points[i]);
        }
        return y;
    }

    @Override
    public String makeFunction()
    {
        return this.spline.toString();
    }
}
