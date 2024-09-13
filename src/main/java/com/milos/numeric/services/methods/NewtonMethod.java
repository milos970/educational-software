package com.milos.numeric.services.methods;


import org.hibernate.AssertionFailure;
import org.mariuszgromada.math.mxparser.*;

public final class NewtonMethod
{
    private static final int ITERATIONS = 1000;

    private NewtonMethod() {
        throw new AssertionFailure("afsd");
    }

    private static void prerekvizita() {
        boolean isCallSuccessful = License.iConfirmNonCommercialUse("John Doe");

        /* Verification if use type has been already confirmed */
        boolean isConfirmed = License.checkIfUseTypeConfirmed();

        /* Checking use type confirmation message */
        String message = License.getUseTypeConfirmationMessage();

        /* ----------- */
        mXparser.consolePrintln("isCallSuccessful = " + isCallSuccessful);
        mXparser.consolePrintln("isConfirmed = " + isConfirmed);
        mXparser.consolePrintln("message = " + message);
    }
    public static double newtonMethod(String expression, double tolerance, double initialGuess)
    {

        prerekvizita();

        Function f = new Function("f(x) = " + expression);

        double[][] data = new double[ITERATIONS][3];

        double current = initialGuess;

        for (int i = 0; i < ITERATIONS; ++i)
        {
            data[i][0] = i;
            data[i][1] = current;

            double fx = f.calculate(current);


            Argument x = new Argument("x = " + current);
            Expression e = new Expression("der(" + expression + ",x)", x);
            double defx = e.calculate();

            double next = current - (fx / defx);
            double error = Math.abs(next - current);
            data[i][2] = error;

            if (error < tolerance)
            {
                break;
            }

            current = next;
        }



        return current;
    }

    public static double regulaFalsi(String expression, double tolerance, double a, double b)
    {

        prerekvizita();

        Function f = new Function("f(x) = " + expression);

        double funa = f.calculate(a);
        double funb = f.calculate(b);


        if (funa * funb >= 0)
        {

            return Double.NaN;
        }

        double x = a;
        double[][] data = new double[ITERATIONS][4];
        double prev = x;

        for (int i = 1; i < ITERATIONS; ++i)
        {

            funa = f.calculate(a);

            funb = f.calculate(b);

            if (Double.isNaN(funa) || Double.isNaN(funb)) {
                break;
            }



            x = (a * funb - b * funa) / (funb - funa);

            double funx = f.calculate(x);

            if (Double.isNaN(funx)) {
                break;
            }

             if (funx == 0)
             {
                 break;
             }


            if (Math.abs(x - prev) <= tolerance) {
                break;
            }

             if (funx * funa < 0)
             {
                 b = x;
             } else {
                 a = x;
             }

            prev = x;


        }



        return x;
    }


    public static double bisection(String expression, double tolerance, double a, double b)
    {
        prerekvizita();
        Function f = new Function("f(x) = " + expression);

        double funa = f.calculate(a);

        double funb = f.calculate(b);


        if (funa * funb > 0)
        {
            return Double.NaN;
        }

        double x = 0;

        for (int i = 0; i < ITERATIONS; ++i)
        {
            x = (a + b)/ 2;

            double func = f.calculate(x);

            if (func == 0)
            {
                break;
            }

            if (Double.compare((Math.abs(b - a) / 2),tolerance) <= 0 || Math.abs(func) < tolerance)
            {
                break;
            }

            if (funa * func < 0)
            {
                b = x;
            } else
            {
                a = x;
            }




            funa = f.calculate(a);


            funb = f.calculate(b);

        }

        return x;
    }
}
