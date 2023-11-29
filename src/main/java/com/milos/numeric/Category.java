package com.milos.numeric;

public enum Category {
    NELINEARNE_ROVNICA("Nelineárne rovnice"),
    APROXIMACIA_FUNKCII("Aproximacia funkcii"),
    URCITY_INTEGRAL("Určitý integral");
    private final String name;

    Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
