package com.milos.numeric.methods;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertFalse;

public class DataValidationTest
{
    private final List<double[][]> list = new LinkedList<>();

    public DataValidationTest() {

    }


    void initUseCase()
    {

        double[][] data3 =  { {1,2,3,4,5}, {2,3,4,5,Double.MAX_VALUE + 1} };
        double[][] data4 =  {   };
        double[][] data5 =  null;


        list.add(data3);
        list.add(data4);
        list.add(data5);
    }

    public void emptyData()
    {
        boolean thrown = false;
        double[][] data =  {   };
        try
        {
            PolynomialFunctionLagrangeForm lagrangePolynomial = new PolynomialFunctionLagrangeForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("Empty data",false);



        thrown = false;
        data =  null;
        try
        {
            PolynomialFunctionNewtonForm lagrangePolynomial = new PolynomialFunctionNewtonForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("Nullable data",false);
    }

    public void nextError()
    {
        boolean thrown = false;
        double[][] data =  { {1,2,3,4,5}, {2,3} };


        try
        {
            PolynomialFunctionLagrangeForm lagrangePolynomial = new PolynomialFunctionLagrangeForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);

        thrown = false;

        try
        {
            PolynomialFunctionNewtonForm lagrangePolynomial = new PolynomialFunctionNewtonForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);
    }

    public void nextError1()
    {
        boolean thrown = false;
        double[][] data =  { {1,2,2,3,4,5}, {2,3,4,5,6,8} };

        try
        {
            PolynomialFunctionLagrangeForm lagrangePolynomial = new PolynomialFunctionLagrangeForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);

        thrown = false;

        try
        {
            PolynomialFunctionNewtonForm lagrangePolynomial = new PolynomialFunctionNewtonForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);

        thrown = false;

        try
        {

            //PolynomialSplineFunction lagrangePolynomial = new PolynomialSplineFunction(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);
    }

    public void nextError2()
    {
        boolean thrown = false;
        double[][] data =  { {1,2,3,4,5}, {2,3,4,5,Double.MAX_VALUE + 1} };

        try
        {
            PolynomialFunctionLagrangeForm lagrangePolynomial = new PolynomialFunctionLagrangeForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);

        thrown = false;

        try
        {
            PolynomialFunctionNewtonForm lagrangePolynomial = new PolynomialFunctionNewtonForm(data[0],data[1]);
        } catch (Exception e) {
            thrown = true;
        }

        assertFalse("ytyt",false);
    }



}
