package com.milos.numeric.methods;

import com.milos.numeric.Entry;

import java.util.List;
import java.util.HashMap;
public enum Aproximation {
    LANGRANGEOV("Langrangeov interpolačný polynom") {
        @Override
        public double calculate(List<Entry> list)
        {

            String polynom = this.makeEquation(list);


            return 4;
        }
    },

    NEWTON("Newtonov interpolačný polynom") {
        @Override
        public double calculate(List<Entry> list)
        {
            StringBuilder sb = new StringBuilder();







            return 4;
        }
    };

    public double recursion(double... values)
    {

        if (values.length == 2)
        {
            double x1 = map.get(values[1]);
            double x2 = map.get(values[0]);
            double res = ( x1 - x2 ) / ( values[1] - values[0] );
            return res;
        }



        double[] value = new double[values.length - 1];
        double[] value1 = new double[values.length - 1];

        for ( int j = 0; j < values.length - 1; j++)
        {
            value[j] = values[j];
        }

        for ( int j = 1; j < values.length; j++)
        {
            value1[j - 1] = values[j];
        }

        double a = recursion(value1);
        double b = recursion(value);
        double c = value1[value1.length - 1];
        double d = value[0];

        return (a - b) / (c - d);

    }



    private final String name;
    private HashMap<Double,Double> map;

    Aproximation(String name) {
        this.name = name;
        this.map = new HashMap<>();
        this.map.put(-2.0,10.0);
        this.map.put(-1.0,4.0);
        this.map.put(1.0,6.0);
        this.map.put(2.0,3.0);
    }

    public String makeEquation(List<Entry> list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {

            if (i != 0) {
                sb.append(" + ");
            }

            Entry firstEntry = list.get(i);
            sb.append(firstEntry.y());
            sb.append(" * ( ");

            int count = 0; //temporarily solution
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    continue;
                }


                sb.append("( ( x ");

                Entry entry = list.get(j);
                if (entry.x() >= 0) {
                    sb.append(" - ");
                } else {
                    sb.append(" + ");
                }

                sb.append(entry.x());
                sb.append(" )");
                sb.append(" / ");
                double res = firstEntry.x() - entry.x();
                sb.append(res);

                if (count == list.size()) {
                    sb.append(" ) * ");

                } else {
                    sb.append(" )");
                }
                count++;


            }

            sb.append(" )");

        }
        return sb.toString();
    }

    public abstract double calculate(List<Entry> list);


    @Override
    public String toString() {
        return this.name;
    }
}
