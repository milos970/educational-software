package com.milos.numeric.methods;

import com.milos.numeric.Parameters;
import org.mariuszgromada.math.mxparser.*;

public enum NonLinear
{

    NEWTON("Newtonová metóda")
            {
                @Override
                public double calculate(Parameters parameters)
                {
                    double xk = 0.0;
                    double xk_1 = parameters.getX_1();
                    double E = parameters.getE();

                    Argument x = new Argument("x");
                    Function f = new Function("f(x) = 10*cos(x-1) - x^2 + 2x - 1");


                    for (int i = 1; i < Integer.MAX_VALUE; ++i)
                    {
                        x.setArgumentValue(xk_1);
                        double der = new Expression("der(10*cos(x-1) - x^2 + 2x - 1,x)",x).calculate();
                        double fun = new Expression("f(" + xk_1 + ")",f).calculate();
                        xk = xk_1 -  (fun / der);

                        if (Math.abs(xk - xk_1) <= E)
                        {
                            break;
                        }

                        xk_1 = xk;

                    }

                    return xk;

                }
            };

    /*PROSTA_ITERACIA("Prostá iterácia")
    {
        @Override
        public double calculate(Parameters parameters)
        {
            double xk = 0.0;
            double xk_1 = parameters.getX_1();
            double E = parameters.getE();

            Argument x = new Argument("x");
            Function f = new Function("f(x) = " + parameters.getFunction());

            for (int i = 0; i < Integer.MAX_VALUE; ++i)
            {
                xk = new Expression("f(" + xk_1 + ")",f).calculate();

                if (Math.abs(xk - xk_1) <= E) {
                    break;
                }
                xk_1 = xk;
            }

            return xk;
        }
    },*/

    /*REGULA_FALSI("Regula falsi")
            {
                @Override
                public double calculate(String equation, double xk_1, double E)
                {
                    Argument x = new Argument("x");
                    Function f = new Function("f(x) = " + equation);

                    double xk = 0;
                    for (int i = 0; i < Integer.MAX_VALUE; ++i)
                    {
                        xk = new Expression("f(" + xk_1 + ")",f).calculate();

                        if (Math.abs(xk - xk_1) <= E) {
                            break;
                        }
                        xk_1 = xk;
                    }

                    return xk;
                }
            };*/

    private final String name;

    NonLinear(String name) {
        this.name = name;
    }

    public abstract double calculate(final Parameters parameters);




    @Override
    public String toString() {
        return this.name;
    }
}
