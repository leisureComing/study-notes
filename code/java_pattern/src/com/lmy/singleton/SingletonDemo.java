package com.lmy.singleton;

public class SingletonDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("未多线程测试，都是在单线程下的测试");
        // 饿汗式
        println("==饿汗式==");
        Singleton1 obj1 = Singleton1.getInstance();
        Singleton1 obj2 = Singleton1.getInstance();
        println(obj1.hashCode());
        println(obj2.hashCode());
        // 懒汉式线程不安全
        println("==懒汉式线程不安全==");
        Singleton2 obj3 = Singleton2.getInstance();
        Singleton2 obj4 = Singleton2.getInstance();
        println(obj3.hashCode());
        println(obj4.hashCode());
        // 懒汉式线程安全
        println("==懒汉式线程安全==");
        Singleton3 obj5 = Singleton3.getInstance();
        Singleton3 obj6 = Singleton3.getInstance();
        println(obj5.hashCode());
        println(obj6.hashCode());
        // 双检锁/双重校验锁
        println("==双检锁/双重校验锁==");
        Singleton4 obj7 = Singleton4.getInstance();
        Singleton4 obj8 = Singleton4.getInstance();
        println(obj7.hashCode());
        println(obj8.hashCode());
        // 登记式/静态内部类
        println("==登记式/静态内部类==");
        Singleton5 obj9 = Singleton5.getInstance();
        Singleton5 obj10 = Singleton5.getInstance();
        println(obj9.hashCode());
        println(obj10.hashCode());
        // 枚举
        println("==枚举==");
        println(Singleton6.INSTANCE.hashCode());
        println(Singleton6.INSTANCE.hashCode());
        Singleton6.INSTANCE.say();
        Singleton6.INSTANCE.run();
    }
}

/**
 * 饿汉式
 * 线程安全：
 * 基于classloader机制（单线程进行类装载）避免了多线程的问题；
 * 非Lazy：
 * 调用静态方法（静态方法中会用其他的静态字段），所有的静态字段都会被类加载；
 * 当调用的是其他的静态方法（非getInstance方法），实例字段也会被加载；
 */
class Singleton1 {
    private static Singleton1 instance = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return instance;
    }
}


/**
 * 懒汉式线程不安全
 * 多线程下无法正常工作；
 * Lazy
 */
class Singleton2 {
    private static Singleton2 instance;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}


/**
 * 懒汉式线程安全
 * 使用synchronized关键字进行线程同步，每次都进行同步，效率比较低；
 * Lazy
 */
class Singleton3 {
    private static Singleton3 instance;

    private Singleton3() {
    }

    public static synchronized Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}


/**
 * 双检锁/双重校验锁
 * 使用synchronized关键字进行线程同步, 只有第一次进行初始化的需要同步；
 * Lazy
 */
class Singleton4 {
    // volatile防止指令重排
    private static volatile Singleton4 instance;

    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        if (instance == null) {
            synchronized (Singleton4.class) {
                if (instance == null) {
                    instance = new Singleton4();
                }
            }
        }
        return instance;
    }
}


/**
 * 登记式/静态内部类
 * 线程安全：
 * 基于classloader机制（单线程进行类装载）避免了多线程的问题；
 * Lazy：
 * classloader机制，只加载外部类时，不会加载静态内部类；且只加载静态内部类时，外部类也不会被加载；
 */
class Singleton5 {
    private static class SingletonHold {
        private static final Singleton5 INSTANCE = new Singleton5();
    }

    private Singleton5() {
    }

    public static final Singleton5 getInstance() {
        return SingletonHold.INSTANCE;
    }
}


/**
 * 枚举
 * 和普通类一样可以有变量和方法
 * 线程安全
 * 非Lazy
 */
enum Singleton6 {
    INSTANCE;

    public void say() {
    }

    public void run() {
    }
}

/**
 * 枚举基础知识
 */
enum EnumBase {
    // 枚举实例必须定义在开头
    // 多个枚举实例，用[,]隔开，用[;]结尾
    INSTANCE,
    INSTANCE2;

    // 枚举属性，用[;]结尾
    private String name = "枚举单例";
    private int num = 0;

    public void say() {

    }

    private int nj = 0;

    public void run() {

    }
}