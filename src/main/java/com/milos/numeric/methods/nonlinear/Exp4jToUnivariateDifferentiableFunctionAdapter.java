package com.milos.numeric.methods.nonlinear;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.exception.DimensionMismatchException;

public class Exp4jToUnivariateDifferentiableFunctionAdapter implements UnivariateDifferentiableFunction
{
    private final Expression[] expression = new Expression[2];
    private double h = 10e-12;
    public Exp4jToUnivariateDifferentiableFunctionAdapter(String equation)
    {

              this.expression[0] = new ExpressionBuilder(equation)
                .variables("x")
                .build();

        this.expression[1] = new ExpressionBuilder(equation)
                .variables("x")
                .build();
    }

    @Override
    public DerivativeStructure value(DerivativeStructure derivativeStructure) throws DimensionMismatchException {
       return  null;
    }

    @Override
    public double value(double v)
    {
        expression[0].setVariable("x", v + h);
        expression[1].setVariable("x", v);

        return (expression[0].evaluate() - expression[1].evaluate()) / h;
    }

    public static void main(String[] args) {
        String equationString = "10 * cos(x - 1) - x * x + 2 * x - 1";

        UnivariateDifferentiableFunction equationFunction = new Exp4jToUnivariateDifferentiableFunctionAdapter(equationString);

        // Test the equation function with a value of x
        double xValue = 2.4; // Change this value as needed
        double result = equationFunction.value(xValue);

        System.out.println("Result: " + result);
    }
}
