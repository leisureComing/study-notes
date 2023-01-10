package com.lmy.builder;

public abstract class ADrink implements IMeal{
    @Override
    public abstract String name();

    @Override
    public abstract float price();

    /**
     * 饮料杯子打包
     * */
    public IPacking packing() {
        return new Bottle();
    }
}
