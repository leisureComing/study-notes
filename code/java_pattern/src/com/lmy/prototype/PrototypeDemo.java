package com.lmy.prototype;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import javax.print.attribute.standard.NumberUp;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 原型模式
 * 用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。
 * java中要实现拷贝现有对象的需要实现接口[Cloneable];
 * 当然也可通过[new]关键字重新创建新对象，并把现有对象的成员属性进行赋值（效率低）；
 * 浅拷贝
 * 只会对基本数据类型进行拷贝
 * 深拷贝
 * 基本数据类型和引用类型都进行拷贝
 * 序列化
 * 需要序列化和反序列化的对象要实现[Serializable]接口
 */
public class PrototypeDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {
        println("==原型模式浅克隆==");
        Prototype1 prototype1 = new Prototype1();
        prototype1.name = "张三";
        prototype1.age = 10;
        prototype1.test.add(1);
        prototype1.test.add(2);
        println(prototype1.toString());
        println("==test是拷贝引用，所以当改变prototype1的test时，clone1对象的test同时会被改变==");
        Prototype1 clone1 = (Prototype1) prototype1.clone();
        // 修改原有的值
        prototype1.test.add(3);
        println(clone1.toString());

        println("==原型模式利用[Cloneable]深克隆==");
        Prototype2 prototype2 = new Prototype2();
        prototype2.name = "李四";
        prototype2.age = 10;
        prototype2.test.add(1);
        prototype2.test.add(2);
        println(prototype2.toString());
        Prototype2 clone2 = (Prototype2) prototype2.clone();
        // 修改原有的值
        prototype1.test.add(3);
        println(clone2.toString());

        println("==原型模式利用[序列化]深克隆==");
        Prototype3 prototype3 = new Prototype3();
        prototype3.name = "王五";
        prototype3.age = 10;
        prototype3.test.add(1);
        prototype3.test.add(2);
        println(prototype3.toString());
        Prototype3 clone3 = (Prototype3) prototype3.clone();
        // 修改原有的值
        prototype3.test.add(3);
        println(clone3.toString());

    }
}


/**
 * 浅拷贝
 */
class Prototype1 implements Cloneable {
    public String name;
    public int age;
    public ArrayList<Integer> test = new ArrayList<>();

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        return "Prototype{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", test=" + test +
                '}';
    }
}

/**
 * 深拷贝
 * ArrayList有实现[Cloneable]接口，利用这一点对[test]进行深拷贝
 */
class  Prototype2 implements Cloneable {
    public String name;
    public int age;
    public ArrayList<Integer> test = new ArrayList<>();

    @Override
    protected Object clone() {
        try {
            Prototype2 clone = (Prototype2) super.clone();
            clone.test = (ArrayList<Integer>) this.test.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        return "Prototype{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", test=" + test +
                '}';
    }
}


/**
 * 深拷贝
 * 利用序列化进行深拷贝
 */
class Prototype3 implements Cloneable, Serializable {
    public String name;
    public int age;
    public ArrayList<Integer> test = new ArrayList<>();

    @Override
    protected Object clone() {
        Object obj = null;
        ByteArrayInputStream in = null;
        ObjectInputStream oIn = null;
        ByteArrayOutputStream out = null;
        ObjectOutputStream oOut = null;
        try {
            // 序列化
            out = new ByteArrayOutputStream();
            oOut = new ObjectOutputStream(out);
            oOut.writeObject(this);

            // 反序列化
            in = new ByteArrayInputStream(out.toByteArray());
            oIn = new ObjectInputStream(in);
            obj = oIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                oIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                oOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }


    @Override
    public String toString() {
        return "Prototype{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", test=" + test +
                '}';
    }
}