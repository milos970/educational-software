package com.milos.numeric;

public enum Method {
    JACOBIHO_ITERACNA_METODA("Jacobiho iteračná metóda"),
    NEWTONOVA_METODA("Newtonová metóda"),
    SEIDELOVA_ITERACNA_METODA("Seidelová iteračná metóda");

    private final String name;

    Method(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
