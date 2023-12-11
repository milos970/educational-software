package com.milos.numeric.methods.integration;

import org.apache.commons.jexl3.*;

public abstract class Integration
{
    protected static final JexlEngine jexl = new JexlBuilder().create();

    public abstract double calculate(String function, double lower, double upper, int maxEval);

    protected static double evaluateExpression(JexlExpression expression, double x)
    {
        JexlContext context = new MapContext();
        context.set("x", x);
        Object result = expression.evaluate(context);
        return (double) result;
    }


}
