package com.lmy.builder;

public class Coke extends ADrink{
    @Override
    public String name() {
        return "Coke";
    }

    @Override
    public float price() {
        return 8.0f;
    }
}
