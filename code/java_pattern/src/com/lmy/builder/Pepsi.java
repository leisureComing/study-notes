package com.lmy.builder;

public class Pepsi extends ADrink{
    @Override
    public String name() {
        return "Pepsi";
    }

    @Override
    public float price() {
        return 8.0f;
    }
}
