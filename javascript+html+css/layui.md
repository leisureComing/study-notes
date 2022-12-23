# 1 layui

官网：https://layuion.com/



# 2 简单使用

## 2.1 官网下载

当前下载的layui的版本为：layui-v2.7.6

目录结构：

```js
layui/
  ├─css
  │  └─modules // ccs模块目录
  │  └─layui.css // 核心样式库
  └─font // 字体目录
  └─layui.js // 核心模块库
```



## 2.2 项目中添加

​	如果你将 Layui 下载到了本地，那么可将其放置到你项目的静态资源目录，并在页面引入核心文件。



## 2.3 引入核心文件

### layui css

```html
<!DOCTYPE html>
<html>
<head>
    ...
    <!-- 引入layui的css -->
  	<link href="./layui/css/layui.css" rel="stylesheet">
</head>
<body>
	<!-- 
		使用layui的css
		class="layui-container"
		class="layui-row"
		class="layui-col-md9"
		class="layui-col-md1"
		...
		都是layui的css样式
	-->
    <div class="layui-container">
    	<div class="layui-row">
        	<div class="layui-col-md9"></div>
            <div class="layui-col-md1"></div>
      	</div>
    </div>
</body>
</html>
```

### layui js

```html
<!DOCTYPE html>
<html>
<head>
    ...
    <!-- 引入layui的js -->
  	<script src="./layui/layui.js"></script>
</head>
<body>
	<script>
        /*
        	其中['layer', 'form']
			引用layui js的layer和form模块
			多个使用数组['','']
			单个使用''
        */
        
        layui.use(['layer', 'form'], function(){
          	var layer = layui.layer;
         	// 吐司
          	layer.msg('Hello World');
        });
	</script> 
</body>
</html>
```



# 3 布局容器

​	固定宽度的两边留白的class="layui-container"；不固定容器宽度，适配屏幕100%的class="layui-fluid"。

```html
<!-- 固定宽度的两边留白 -->
<div class="layui-container">
  ...
</div>
```

```html
<!-- 不固定容器宽度，适配屏幕100% -->
<div class="layui-fluid">
  ...
</div>
```



# 4 布局栅格系统

```html
<!-- 需要放入容器里 -->
<div class="layui-container">
    <!-- 行布局和列布局需要一起搭配使用 -->
	<div class="layui-row">
        <!-- 列布局：将容器进行了12等分 -->
    	 <div class=
              "
              	layui-col-xs6  <!-- 移动6/12 -->
                layui-col-sm6  <!-- 平板6/12 -->
                layui-col-md6"><!-- 桌面6/12 -->
    	</div>
        
        <!-- 其他属性 -->
        <!-- 偏移量，也是总共12等分；表示偏移占据列数 -->
        <div class="layui-col-md4 layui-col-md-offset4"></div>
        <!-- 列间距，预设值不满足，只能搭配偏移量 -->
        <div class="layui-col-md4 layui-col-space1"></div>
    </div>
</div>
```



# 5 图标

```html
<!-- layui 2.3.0 开始支持 font-class 的形式定义图标 -->
<i class="layui-icon layui-icon-face-smile"></i> 

<!-- layui 2.3.0 之前的版本，只能设置 unicode 来定义图标 -->
<!-- 其中的 &#xe60c; 即是图标对应的 unicode 字符 -->
<i class="layui-icon">&#xe60c;</i>   
```



# 6 动画

​	css3动画

```html
<!-- 其中 layui-anim 是必须的，后面跟着的即是不同的动画类 -->
<div class="layui-anim layui-anim-up"></div>
```



# 7 按钮

## 7.1 基础按钮

向任意HTML元素设定*class="layui-btn"*，建立一个基础按钮。

```html
<button type="button" class="layui-btn">基础按钮</button>
<a href="" class="layui-btn">基础按钮</a>
<div class="layui-btn">基础按钮</div>
```



## 7.2 主题

```html
原始	class="layui-btn layui-btn-primary"
默认	class="layui-btn"
百搭	class="layui-btn layui-btn-normal"
暖色	class="layui-btn layui-btn-warm"
警告	class="layui-btn layui-btn-danger"
禁用	class="layui-btn layui-btn-disabled"

主色	class="layui-btn layui-btn-primary layui-border-green"
百搭	class="layui-btn layui-btn-primary layui-border-blue"
暖色	class="layui-btn layui-btn-primary layui-border-orange"
警告	class="layui-btn layui-btn-primary layui-border-red"
深色	class="layui-btn layui-btn-primary layui-border-black"
```



## 7.3 尺寸

```html
大型	class="layui-btn layui-btn-lg"
默认	class="layui-btn"
小型	class="layui-btn layui-btn-sm"
迷你	class="layui-btn layui-btn-xs"

大型百搭	class="layui-btn layui-btn-lg layui-btn-normal"
正常暖色	class="layui-btn layui-btn-warm"
小型警告	class="layui-btn layui-btn-sm layui-btn-danger"
迷你禁用	class="layui-btn layui-btn-xs layui-btn-disabled"

流体按钮    class="layui-btn layui-btn-fluid"
```



## 7.4 圆角

```html
class="layui-btn layui-btn-radius"
```



## 7.5 按钮图标

```html
<button type="button" class="layui-btn">
  <i class="layui-icon layui-icon-down layui-font-12"></i>
</button>
```



## 7.6 按钮组

```html
<div class="layui-btn-group">
  <button type="button" class="layui-btn">增加</button>
  <button type="button" class="layui-btn">编辑</button>
  <button type="button" class="layui-btn">删除</button>
</div>
```



## 7.7 按钮容器

```html
<div class="layui-btn-container">
  <button type="button" class="layui-btn">按钮一</button> 
  <button type="button" class="layui-btn">按钮二</button> 
  <button type="button" class="layui-btn">按钮三</button> 
</div>
```



# 8 表单

依赖加载模块：form

```html
<!DOCTYPE html>
<html>
<head>
    ...
    <!-- 引入layui的css -->
  	<link href="./layui/css/layui.css" rel="stylesheet">
    <!-- 引入layui的js -->
  	<script src="./layui/layui.js"></script>
</head>
<body>
	<script>
		// 引用layui js的form模块
        layui.use('form', function(){
            var form = layui.form;
        });
	</script> 
</body>
</html>


```



在一个容器中设定 *class="layui-form"* 来标识一个表单元素块

```html
<form class="layui-form" action="">
    ...
</form>
```



行区块结构

```html
<div class="layui-form-item">
  <label class="layui-form-label">标签区域</label>
  <div class="layui-input-block">
    原始表单元素区域
  </div>
</div>
```



# 9 导航