package com.lmy.builder;

public class ChickenBurger extends ABurger{
    @Override
    public String name() {
        return "ChickenBurger";
    }

    @Override
    public float price() {
        return 17.0f;
    }
}
