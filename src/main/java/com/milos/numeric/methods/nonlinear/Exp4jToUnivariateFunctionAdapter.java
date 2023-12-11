package com.milos.numeric.methods.nonlinear;

import org.apache.commons.math3.analysis.UnivariateFunction;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Exp4jToUnivariateFunctionAdapter implements UnivariateFunction {
    private final Expression expression;

    public Exp4jToUnivariateFunctionAdapter(String equation) {
        this.expression = new ExpressionBuilder(equation)
                .variables("x")
                .build();
    }

    @Override
    public double value(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }


}
