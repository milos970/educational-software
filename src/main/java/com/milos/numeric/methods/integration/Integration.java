package com.milos.numeric.methods.integration;

import com.milos.numeric.parameters.Parameters;
import org.apache.commons.jexl3.*;

public abstract class Integration
{
    protected static final JexlEngine jexl = new JexlBuilder().create();

    public abstract double calculate(Parameters parameters);

    protected static double evaluateExpression(JexlExpression expression, double x)
    {
        JexlContext context = new MapContext();
        context.set("x", x);
        Object result = expression.evaluate(context);
        return (double) result;
    }


}
