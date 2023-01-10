package com.lmy.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 建造者模式
 * 将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示
 * */
public class BuilderDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==套餐1==");
        IMealBuilder iMealBuilder = new BurgerBuilder();
        String setMealName1 = iMealBuilder.setMeal();
        println(setMealName1);

        println("==套餐2==");
        iMealBuilder = new DrinkBuilder();
        String setMealName2 = iMealBuilder.setMeal();
        println(setMealName2);
    }
}



/**
 * 食物制造者抽象类
 * */
abstract class IMealBuilder {
    protected List<IMeal> items = new ArrayList<>();
    /**
     * 套餐
     * */
    public abstract String setMeal();
}



/**
 * 汉堡堡大套餐
 * 具体制造者
 * */
class BurgerBuilder extends IMealBuilder{
    @Override
    public String setMeal() {
        items.add(new ChickenBurger());
        items.add(new VegBurger());
        float sumPrice = 0.0f;
        for(IMeal meal : items){

            System.out.println("汉堡名称：" + meal.name());
            System.out.println("汉堡价格：" + meal.price());
            sumPrice +=  meal.price() * 0.7;
        }
        System.out.println("套餐价格：" + sumPrice);
        return "汉堡堡大套餐";
    }
}



/**
 * 满胜饮料大套餐
 * 具体制造者
 * */
class DrinkBuilder extends IMealBuilder{
    @Override
    public String setMeal() {
        items.add(new Coke());
        items.add(new Pepsi());
        float sumPrice = 0.0f;
        for(IMeal meal : items){
            System.out.println("饮料名称：" + meal.name());
            System.out.println("饮料价格：" + meal.price());
            sumPrice +=  meal.price() * 0.9;
        }
        System.out.println("套餐价格：" + sumPrice);
        return "满胜饮料大套餐";
    }
}