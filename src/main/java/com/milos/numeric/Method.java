package com.milos.numeric;

import java.util.Objects;

public enum Method {
    JACOBIHO_ITERACNA_METODA("Jacobiho iteračná metóda")
    {
        @Override
        public double[] calculate(String[][] array, double E)
        {
            double[][] matrix = Method.validate(array);

            Method.pivotTransformation(matrix);

            double[] s = new double[matrix.length];
            double[] p = new double[matrix.length];

            for (int i = 0; i < 2; ++i) //iteration
            {

                System.arraycopy(s, 0, p, 0, matrix.length);

                for (int j = 0; j < s.length; ++j)
                {
                    s[j] = matrix[j][matrix[0].length - 1];
                    for (int k = 0; k < matrix[0].length - 1; ++k)
                    {
                        if (k == j)
                        {
                            continue;
                        }
                        double odr = (-1 *(p[k] * matrix[j][k]));
                        s[j]  += odr;
                    }

                }

            }


            return s;
        }
    },

    SEIDELOVA_ITERACNA_METODA("Seidelová iteračná metóda")
    {
        @Override
        public double[] calculate(String[][] array, double E)
        {
            double[][] matrix = Method.validate(array);

            Method.pivotTransformation(matrix);

            double[] s = new double[matrix.length];


            for (int i = 0; i < 5; ++i) //iteration
            {

                for (int j = 0; j < s.length; ++j)
                {
                    double res = matrix[j][matrix[0].length - 1];
                    for (int k = 0; k < matrix[0].length - 1; ++k)
                    {
                        if (k == j)
                        {
                            continue;
                        }
                        double odr = (-1 *(s[k] * matrix[j][k]));
                        res  += odr;
                    }

                    s[j] = res;

                }

            }


            return s;
        }
    };

    private final String name;

    Method(String name) {
        this.name = name;
    }


    public abstract double[] calculate(String[][] matrix, double E);

    private static double[][] validate(String[][] matrix)
    {
        Objects.requireNonNull(matrix);

        if (matrix.length == 0)
        {
            throw new ArrayIndexOutOfBoundsException(" Array is empty");
        }


        for (int i = 0; i < matrix.length; ++i)
        {
            double e = Double.parseDouble(matrix[i][i]);
            if (e == 0)
            {
                throw new IllegalArgumentException("Matrix is not dominant");
            }

        }

        double[][] arr = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; ++i)
        {
            for (int j = 0; j < matrix[i].length; ++j)
            {
                arr[i][j] = Double.parseDouble(matrix[i][j]);
            }

        }

        return arr;
    }
    private static void pivotTransformation(double[][] matrix) {
        for (int i = 0; i < matrix.length; ++i)
        {
            double piv = matrix[i][i];
            for (int j = 0; j < matrix[0].length; ++j)
            {
                matrix[i][j] /= piv;
            }

        }

    }

    @Override
    public String toString() {
        return this.name;
    }
}
