package com.milos.numeric.methods.aproximation;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;

public class Lagrangev implements Aproximation {
    private final PolynomialFunctionLagrangeForm lagrangePolynomial;

    public Lagrangev(PolynomialFunctionLagrangeForm lagrangePolynomial) {
        this.lagrangePolynomial = lagrangePolynomial;
    }

    @Override
    public double[] calculate(double[] points)
    {
        double[] y = new double[points.length];

        for (int i = 0; i < points.length; ++i)
        {
            y[i] = this.lagrangePolynomial.value(points[i]);
        }
        return y;
    }

    @Override
    public String makeFunction()
    {
        double[] values = this.lagrangePolynomial.getInterpolatingValues();
        double[] coefficients = this.lagrangePolynomial.getCoefficients();
        double[] points = this.lagrangePolynomial.getInterpolatingPoints();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; ++i)
        {
            sb.append(values[i]);
            sb.append(" * ");
            sb.append("( ");

            for (int j = 0; j < points.length; ++j)
            {
                if (i == j)
                {
                    continue;
                }

                if (points[i] >= 0)
                {
                    sb.append("(x - ");
                } else {
                    sb.append("(x + ");
                }

                sb.append(points[j]);
                sb.append(")");
            }

            sb.append(" )");

            sb.append(" / ");


            double menovatel = 1;
            for (int j = 0; j < points.length; ++j)
            {
                if (i == j)
                {
                    continue;
                }

                menovatel *= (points[i] - points[j]);
            }

            sb.append(menovatel);



            if (i + 1 != points.length)
            {
                if (points[i + 1] >= 0) {
                    sb.append(" + ");
                } else {
                    points[i + 1] *= -1;
                    sb.append(" - ");
                }
            }




        }

        return sb.toString();
    }

}
