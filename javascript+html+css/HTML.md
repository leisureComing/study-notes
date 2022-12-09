# 1. HTML笔记

2022年11月06开始记录



# 2. 元素分类

## 2.1 行内元素

​	元素在同一行，无法设置宽高，宽高是适应元素内容。



## 2.2 块级元素

​	每个元素独占一行，可以设置宽高，默认宽度是父容器的100%。



## 2.3 行内块级元素

​	元素在同一行，可以设置宽高。



## 2.4 元素的互相转换

1、块级标签转换为行内标签: display:inline;
2、行内标签转换为块级标签：display:block;
3、转换为行内块标签：display：inline-block;

```html
<style>
    #id{
        display:inline;
        display:block;
        display：inline-block;
    }
</style>
```

