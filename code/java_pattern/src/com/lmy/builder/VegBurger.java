package com.lmy.builder;

public class VegBurger extends ABurger{
    @Override
    public String name() {
        return "VegBurger";
    }

    @Override
    public float price() {
        return 18.0f;
    }
}
