package com.lmy.flyweight;

import java.util.ArrayList;
import java.util.List;

/**
 * 享元模式
 * 优点：
 * 减少运行时对象实例的个数，节省内存；
 * 将许多“虚拟”对象的状态（外部状态）集中管理；
 * 用途和缺点：
 * 当一个类有许多的实例，而这些实例能被同一个方法控制的时候，我们就可以使用蝇量模式；
 * 缺点在，一旦你实现了它，那么单个的逻辑实例将无法拥有独立而不同的行为；
 * <p>
 * 注意：分清内部状态（共享的部分）和外部状态（独立的不共享的部分）。
 */
public class FlyweightDemo {
    private static void println(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) {

        long weight = 45;

        List<TreeState> treeArray = new ArrayList<>();
        treeArray.add(new TreeState(1, 2,2));
        treeArray.add(new TreeState(2, 3,3));
        treeArray.add(new TreeState(5, 5,6));
        treeArray.add(new TreeState(5, 6,8));

        TreeManager treeManager = new TreeManager(treeArray);
        treeManager.displayTree();
    }
}


/**
 * 树管理类
 * 减少Tree对象实例的个数；
 * 将许多“虚拟”对象的状态（外部状态）集中管理；
 */
class TreeManager {
    private List<TreeState> treeArray;
    private Tree tree = new Tree();

    public TreeManager(List<TreeState> treeArray) {
        this.treeArray = treeArray;
    }

    public void displayTree() {
        if (treeArray != null) {
            for (TreeState state : treeArray) {
                tree.display(state.getX(), state.getY(), state.getHeight());
                System.out.println(tree.hashCode());
            }
        }

    }

}


/**
 * 树类
 */
class Tree {
    /// 内部状态（共享的部分）
    /// 认为树都是绿色的
    private String color = "green";

    /// 我们认为树的位置和树的高度是变化的
    /// 提到外部来进行统一管理
    public void display(int x, int y, int height) {
        System.out.println("树的颜色是：" + color + "-树的位置：x:" + x + "y:" + y + "高度：" + height);
    }
}

/**
 * 树的外部状态（独立的不共享的部分）
 */
class TreeState {
    private int x;
    private int y;
    private int height;

    public TreeState(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}