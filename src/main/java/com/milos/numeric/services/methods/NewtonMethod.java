package com.milos.numeric.services.methods;


import org.hibernate.AssertionFailure;
import org.mariuszgromada.math.mxparser.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    public static List<String[]> newtonMethod(String expression, double tolerance, double initialGuess)
    {

        prerekvizita();

        Function f = new Function("f(x) = " + expression);

        List<String[]> data = new LinkedList<>();

        double current = initialGuess;

        for (int i = 0; i < ITERATIONS; ++i)
        {
            String[] row = new String[2];

            row[0] = String.valueOf(current);

            double fx = f.calculate(current);

            if (Double.isNaN(fx))
            {
                data.clear();
                return data;
            }


            Argument x = new Argument("x = " + current);
            Expression e = new Expression("der(" + expression + ",x)", x);
            double defx = e.calculate();

            if (Double.isNaN(defx))
            {
                data.clear();
                return data;
            }

            double next = current - (fx / defx);
            double error = Math.abs(next - current);
            row[1] = String.valueOf(error);

            data.add(row);

            if (Double.compare(error, tolerance) <= 0)
            {
                break;
            }

            current = next;
        }



        return data;
    }

    public static List<String[]> regulaFalsi(String expression, double tolerance, double a, double b)
    {

        List<String[]> data = new LinkedList<>();

        prerekvizita();

        Function f = new Function("f(x) = " + expression);

        double funa = f.calculate(a);
        double funb = f.calculate(b);


        if (funa * funb >= 0)
        {
            return data;
        }

        double x = a;

        double prev = x;

        for (int i = 0; i < ITERATIONS; ++i)
        {

            String[] row = new String[4];

            row[0] = String.valueOf(a);
            row[1] = String.valueOf(b);

            funa = f.calculate(a);
            funb = f.calculate(b);

            if (Double.isNaN(funa) || Double.isNaN(funb))
            {
                data.clear();
                break;
            }



            x = (a * funb - b * funa) / (funb - funa);
            row[2] = String.valueOf(x);

            double funx = f.calculate(x);

            if (Double.isNaN(funx)) {
                data.clear();
                break;
            }

             if (funx == 0)
             {
                 break;
             }

             double error = Math.abs(x - prev);
             row[3] = String.valueOf(error);

            if (Double.compare(error,tolerance) <= 0) {
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



        return data;
    }


    public static List<String[]> bisection(String expression, double tolerance, double a, double b)
    {
        prerekvizita();

        List<String[]> data = new LinkedList<>();

        Function f = new Function("f(x) = " + expression);

        double funa = f.calculate(a);

        double funb = f.calculate(b);


        if (funa * funb > 0)
        {
            return data;
        }


        double x = 0;

        for (int i = 0; i < ITERATIONS; ++i)
        {

            String[] row = new String[4];

            x = (a + b)/ 2;

            row[0] = String.valueOf(a);
            row[1] = String.valueOf(b);
            row[2] = String.valueOf(x);

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

        }

        return data;
    }
}
