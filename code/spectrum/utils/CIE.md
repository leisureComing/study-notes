# CIE色品计算公式

##### 公式一：

<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210729155056459.png" alt="image-20210729155056459" style="zoom: 50%;" />

##### 公式二：

<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210729163336787.png" alt="image-20210729163336787" style="zoom:50%;" />

##### 公式三：

<img src="C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210729163539412.png" alt="image-20210729163539412" style="zoom:50%;" />





## 程序描述

```dart
/// CIE1931-色品坐标-三刺激值常量表
/// https://wenku.baidu.com/view/b3bd3c68f66527d3240c844769eae009581ba23e.html

/// 波长：380-780
/// 间隔1nm
/// 光源数据
spectrum = [1,2,3,4...];

/// Xs累加之后的X值
/// Ys累加之后的Y值
/// Zs累加之后的Z值
/// Xnm,Ynm,Znm波长对应的色品坐标（三刺激值）常量表
Xs = (spectrum[0] * X380)+(spectrum[1] * X381) + ... +(spectrum[399] * Xnm)
Ys = (spectrum[0] * Y380)+(spectrum[1] * Y381) + ... +(spectrum[399] * Ynm)
Zs = (spectrum[0] * Z380)+(spectrum[1] * Z381) + ... +(spectrum[399] * Znm)

/// 计算K值
/// Y === 100    
K = 100 / Ys
    
/// 计算Y, X, Z混色坐标
/// Y === 100     
X = Xs * K    
Y = 100;
Z = Zs * K

/// 计算x,y,z归一化后的混色坐标
x = X / (X + Y + Z)
y = Y / (X + Y + Z)
z = Z / (X + Y + Z)    
    
```



# CIE色温公式

```dart
/// x,y换算色温公式
437*((x-0.332)/(0.1858-y))^3+3601*((x-0.332)/(0.1858-y))^2+6831*((x-0.332)/(0.1858-y))+5517
```



# CIE显色计算

##### 求待测光源色度坐标：

![image-20210818140440229](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210818140440229.png)

![image-20210818140447603](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210818140447603.png)

![image-20210818140503218](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210818140503218.png)



##### 色差公式：

![image-20210813100935217](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210813100935217.png)

![image-20210818140901238](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210818140901238.png)

##### 显示指数公式：

![image-20210813101039248](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210813101039248.png)

![image-20210813101055574](C:\Users\DELL\AppData\Roaming\Typora\typora-user-images\image-20210813101055574.png)

## 程序描述

```dart
/// 已知色温T,[Uri],[Vri],[Wri];求显色指数R？
/// (1)找光谱辐亮度因数 βi
/// (2)求[Uki],[Vki],[Wki]
/// (3)求色差
/// (4)显色指数R
```

