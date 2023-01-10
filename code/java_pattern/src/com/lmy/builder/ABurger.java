package com.lmy.builder;

/**
 * 汉堡抽象类
 * */
public abstract class ABurger implements IMeal {
    @Override
    public abstract String name();

    @Override
    public abstract float price();

    /**
     * 汉堡用纸打包
     * */
    public IPacking packing() {
        return new Wrapper();
    }
}
