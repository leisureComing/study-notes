# Android笔记

2022年11月11号开始记录



# 1 发展历程

Android是基于Linux内核的自由及开发源代码的操作系统。

2003年10月，Andy Rubin等人创建Android公司，并组建Android团队。

2005年8月被Google收购注资。

2007年11月5日，谷歌公司正式展示Android的操作系统；并成立了手机开放联盟。



系统架构

...



android发行版本

| API等级 | Android版本 | 发布日期   |
| ------- | ----------- | ---------- |
| 31      | 12.0        | 2021-10    |
| 30      | 11.0        | 2020-09    |
| 29      | 10.0        | 2019-09-04 |
| 28      | 9.0         | 2018-08-07 |
| 27      | 8.1         | 2017-12-05 |
| 26      | 8.0         | 2017-08-22 |
| 25      | 7.1.1       | 2016-10-04 |
| 24      | 7.0         | 2016-08-22 |
| 23      | 6.0         | 2015-09-29 |
| 22      | 5.1         | 2015-03-10 |
| 21      | 5.0         | 2014-10-15 |
| 20      | 4.4w        | 2014-06-25 |
| 19      | 4.4         | 2013-10-31 |
| 18      | 4.3         | 2013-07-24 |
| 17      | 4.2         | 2012-11-13 |
| 16      | 4.1         | 2012-06-28 |
| 15      | 4.0.3       | 2011-12-16 |
| 14      | 4.0         | 2011-10-18 |
| 13      | 3.2         | 2011-07-15 |
| 12      | 3.1         | 2011-05-10 |
| 11      | 3.0         | 2011-02-24 |
| 10      | 2.3.3       | 2011-02-09 |
| 9       | 2.3         | 2010-12-06 |
| 8       | 2.2         | 2010-05-20 |
| 7       | 2.1         | 2010-01-12 |
| 6       | 2.0.1       | 2009-12-03 |
| 5       | 2.0         | 2009-10-26 |
| 4       | 1.6         | 2009-09-15 |
| 3       | 1.5         | 2009-04-17 |
| 2       | 1.1         | 2009-02-02 |
| 1       | 1.0         | 2008-09-23 |



简谈Android与java的千丝万缕的关系。

​	Android程序使用java当做开发语言，因此需要安装JDK，java开发环境。同样的，java源码经过编译生成字节码文件（class文件），需要在java虚拟机上运行。然后Android的DalviK VM没有遵循java虚拟机规范，所以并不能直接运行java字节码文件，需要【dx.bat】工具进一步转换成【dex】文件才能在DalviK VM上运行。（所以就流传着说，Android程序虽然是java代码编写的，确不能在java虚拟机上运行，有违背java口号，一次编译到处运行的说法。我想这也是Google被sun告的缘故吧。）



​	DalviK VM虚拟机，采用的是基于寄存器的指令集架构。这样的架构执行速度更快，但于硬件高度绑定。

​	java虚拟机，采用的是基于栈的指令集架构。与硬件没有关联，可移植性高，技术实现简单，但运行速度相对要慢。



​	Android 5.0 使用支持提前编译（Ahead of Time Compilation，AOT）的【ART VM】替换【Dalvik VM】；



# 2 目录结构

....



# 3 四大组件

​	Android系统四大组件分别是活动（Activity）、服务（Service）、广播接收器（Broadcast Receiver）和内容提供器（Content Provider）。

## 3.1 Activity

​	活动（Activity）包含用户界面的组件，主要用于和用户进行交互。

### 3.1.1 创建活动

创建Activity类

创建目录：app/src/main/java/com.lmy.androidstudy

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 给当前活动加载一个布局
        setContentView(R.layout.activity_main);
    }
}
```



创建布局

创建目录：app/src/main/res

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    
</androidx.constraintlayout.widget.ConstraintLayout>
```



注册Activity

注册文件：AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.lmy.androidstudy">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AndroidStudy">
        <activity
            android:name=".MainActivity">
            <!-- 
				应用启动入口活动
				应用添加入启动器（用户可通过系统桌面看到和启动应用）
 			-->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```



### 3.1.2 启动活动

显式Intent启动活动

```java
Intent intent = new Intent(MainActivity.this, SecondActivity.class);
startActivity(intent);

// 结束活动：按下Back键或finish()
```



------



隐式Intent启动活动

通过指定的action（只能由一个action）和一系列的category（可以有多个category）等信息（还有一些附加信息可以添加），由系统去分析这个Intent，找到合适的活动去启动。



AndroidManifest.xml：配置活动能够响应的action和category

```xml
<!-- action只能有一个；category可以有多个 -->
<activity
	android:name=".SecondActivity">
	<intent-filter>
		<action android:name="com.lmy.androidstudy.ACTION_START" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="com.lmy.androidstudy.MY_CATEGORY" />
    </intent-filter>
</activity>
```



只有<action>和<category>中的内容同时匹配上Intent中指定的action和category时，这个活动才能响应。

```java
Intent intent = new Intent("com.lmy.androidstudy.ACTION_START");
intent.addCategory("com.lmy.androidstudy.MY_CATEGORY");
startActivity(intent);

/**
	注意：android.intent.category.DEFAULT是默认的category，当调用startActivity()方法时会自动将这个category添加到Intent中。
*/
```



------



隐式Intent启动活动更多用法

使用隐式Intent，我们不仅可以启动自己程序内的活动，还可以启动其他程序的活动，这使得Android多个应用程序之间的功能共享成为可能。

启动浏览器

```java
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("http://www.baidu.com"));
startActivity(intent);
// Intent.ACTION_VIEW是Android系统内置的动作。
// 其常量值：android.intent.action.VIEW。

// setData()方法接收Uri对象，指定当前Intent正在操作的数据。
// 通常，通过字符串传入Uri.parse()解析产生。
// data部分指定的协议是http，网址是www.baidu.com
```



------



启动拨号

```java
Intent intent = new Intent(Intent.ACTION_DIAL);
intent.setData(Uri.parse("tel:10086"));
startActivity(intent);
// Intent.ACTION_DIAL是Android系统内置的动作。

// data部分指定的协议是tel，号码是10086
```



### 3.1.3 传递数据

向下一个活动传递数据

```java
Intent intent = new Intent(MainActivity.this, SecondActivity.class);
intent.putExtra("data_key", "data");
startActivity(intent);
```



获取数据

```java
Intent intent = getIntent();
String data = intent.getStringExtra("data_key");
```



------

返回数据给上一个活动

跳转到另一个活动，并希望有数据返回。

```java
Intent intent = new Intent(MainActivity.this, SecondActivity.class);
// 这个启动活动方法期望在活动销毁的时候能够返回一个结果回来
// [1]请求码，用于在之后的回调中判断数据的来源
startActivityForResult(intent, 1);
```

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
    // requestCode请求码
    // resultCode返回码：一般RESULT_CANCELED和RESULT_OK
    switch(requestCode){
        case 1:
            if(resultCode == RESULT_OK){
                String data = data.getStringExtra("data_key");
            }
            break;
        default:
    }
}
```



返回数据

```java
Intent intent = new Intent();
intent.putExtra("data_key", "data");
// 返回码：一般RESULT_CANCELED和RESULT_OK
setResult(RESULT_OK, intent);
finish();
```

```java
// 注意：如果用户是通过返回键返回上一个活动，需要重写
@Override
public void onBackPressed() {
	super.onBackPressed();
    
    // 向上一个活动返回数据的代码
    Intent intent = new Intent();
	intent.putExtra("data_key", "data");
	// 返回码：一般RESULT_CANCELED和RESULT_OK
	setResult(RESULT_OK, intent);
	finish();
}
```



### 3.1.4 生命周期

Activity类中定义了7个回调方法，覆盖了活动生命周期的每个环节。

| 生命函数    | 描述                                                  |
| ----------- | ----------------------------------------------------- |
| onCreate()  | 活动创建的时候调用                                    |
| onStart()   | 在活动由不可见变为可见的时候调用                      |
| onResume()  | 活动准备好和用户进行交互的时候调用                    |
| onPause()   | 准备去【启动/恢复】另一个活动调用；内存不够时会被销毁 |
| onStop()    | 活动完全不可见的时候调用；内存不够时会被销毁          |
| onDestroy() | 活动被销毁之前调用                                    |
| onRestart() | 活动由停止状态变为运行状态之前调用                    |

生命周期图：

// 等待补充

// ...

// ...





### 3.1.5 启动模式

​	启动模式一共4种，分别是standard、singleTop、singleTask和singleInstance可以在AndroidManifest.xml中通过给<activity>标签指定android:launchMode属性来选择启动模式。



standard

​	standard是活动默认的启动模式，在不进行显示指定的情况下，所有活动都会自动使用这种启动模式。每当启动一个新活动，它就会在返回栈（Back Stack）中入栈，并处于栈顶的位置（系统不在乎这个活动是否已经在返回栈中存在）。

```xml
<activity
	android:name=".SecondActivity"
    android:launchMode="standard">
</activity>
```



------



singleTop

​	在启动活动时如果发现返回栈的栈顶已经是该活动，则直接使用它，不会再创建新的活动实例。

```xml
<activity
	android:name=".SecondActivity"
    android:launchMode="singleTop">
</activity>
```



------



singleTask

​	每次启动该活动时系统首先会在返回栈中检查是否存在该活动的实例，如果发现已经存在则直接使用该实例，并把在这个活动之上的所有活动统统出栈，如果没有发现就会创建一个新的活动实例。

```xml
<activity
	android:name=".SecondActivity"
    android:launchMode="singleTask">
</activity>
```



------



singleInstance

指定为singleInstance模式的活动会启用一个新的返回栈来管理这个活动；且不管是哪个应用程序来访问这个活动，都共用的同一个返回栈。

```xml
<activity
	android:name=".SecondActivity"
    android:launchMode="singleInstance">
</activity>
```



## 3.2 Service

​	服务是Android中实现程序后台运行的解决方案，它非常适合去执行哪些不需要和用户交互而且还要求长期运行的任务。服务的运行不依赖于任何用户界面，即使程序被切换到后台，或者用户打开了另外一个应用程序，服务仍然能够保持正常运行。

​	不过需要注意的是，服务并不是运行在一个独立的进程当中的，而是依赖创建服务时所在的应用程序进程。当某个应用程序进程被杀掉时，所有依赖于该进程的服务也会停止运行。

​	另外，也不要被服务的后台概念所迷惑，实际上服务并不会自动开启线程，所有的代码都是默认运行在主线程当中的。也就是说，我们需要在服务的内部手动创建子线程，并在这里执行具体的任务，否则就有可能出现主线程被阻塞主的情况。



### 3.2.1 启动服务

通过`startService()`启动的服务。

定义服务类

```java
class MyService extends Service{
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 服务在第一次创建时别调用
    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 服务启动时调用
    // 逻辑代码一般写在这里
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    // 服务销毁时调用
    // 回收资源
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
```



在AndroidManifest.xml配置

```xml
<service
    android:name=".MyService"     
	android:enabled="true"
	android:exported="true">
</service>
```



启动和停止服务

```java
// 在活动中启动和停止服务
Intent intent = new Intent(MainActivity.this, MyService.class);
// 启动服务
startService(intent);
// 停止服务
stopService(intent);
```

```java
// 服务自己停止服务
stopSelf();
```



### 3.2.2 绑定服务

通过`bindService()`启动的服务，活动和服务紧密关联在一起，当活动销毁时必须对服务进行解绑（停止服务），并且服务和活动可以进行通讯。

定义服务类

```java
class MyService extends Service{
    // 定义内部类
    // 此类主要用于和活动进行通信使用
    private class MyBind extends Binder{
        // 编写业务逻辑
        public void startDownload(){
            // ...
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 返回内部类MyBind
        return new MyBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
```



在AndroidManifest.xml配置

```xml
<service
    android:name=".MyService"     
	android:enabled="true"
	android:exported="true">
</service>
```



绑定和解绑服务

```java
// 创建匿名类
private ServiceConnection conn = new ServiceConnection() {
	@Override
	public void onServiceConnected(
        ComponentName componentName, IBinder iBinder) {
    	// iBinder就是服务类返回的MyBind对象
        // 当调用bindService()方法时，如果有onBind返回的不是null
		// 则会回调到这个方法
	}

    @Override	
	public void onServiceDisconnected(
        ComponentName componentName) {
	
    }
};

// 意图
Intent intent = new Intent(MainActivity.this, MyService.class);

// 绑定服务
bindService(intent, conn, BIND_AUTO_CREATE);

// 解绑服务
// 一般是在活动的销毁函数中进行解绑
unbindService(conn);
```



### 3.2.3 前台服务

服务几乎都是在后台运行的，一直以来它都是默默地做着辛苦的工作。但是服务的系统优先级还是比较低的，当系统出现内存不足打得情况时，就有可能会回收掉正在后台运行的服务。如果你希望服务可以一直保持运行状态，而不会由于系统内存不足的原因导致被回收，就可以考虑使用前台服务。



```java
class MyService extends Service{
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 服务在第一次创建时别调用
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = new PendingIntent.getActivity(
            this, 0, intent, 0);
        Notification nc = new NotificationCompat.Builder(this)
            .setContentTitle("标题")
            .setContentText("内容")
            .setWhen(System.currentTimeMillis())//启动时间
            .setSmallIcon(R.mipmap.ic_launcher)// 小icon
            .setLargeIcon(
            BitmapFactory.decodeResource(
                getResources(),R.mipmap.ic_launcher))//大icon
            .setContentIntent(pi)
            .build();
        // 调用startForeground()方法后让MyService变成一个前台服务
        startForeground(1, notification);
        
    }

    // 服务启动时调用
    // 逻辑代码一般写在这里
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    // 服务销毁时调用
    // 回收资源
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
```



### 3.2.4 IntentService

Android专门提供了一个`IntentService`类，这个类会自动开启线程，并在代码执行完毕的时候自动关闭服务。

定义IntentService子类

```java
class MyIntentService extends IntentService{

    public MyIntentService(){
        super("MyIntentService");
    }
    
    // onHandleIntent函数的全部代码是在子线程中运行
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
```



在AndroidManifest.xml配置

```xml
<service
    android:name=".MyIntentService"     
	android:enabled="true"
	android:exported="true">
</service>
```



启动和停止服务

```java
// 在活动中启动和停止服务
Intent intent = new Intent(
    MainActivity.this, MyIntentService.class);
// 启动服务
startService(intent);
// 停止服务
stopService(intent);
```



## 3.3 Broadcast Receiver

标志广播（Normal broadcasts）：是一种完全异步的广播，在广播发出之后，所有的广播接收器几乎都会在同一时刻接收到这条广播消息，因此它们之间没有任何先后顺序可言。这种广播的效率会比较高，但同时也意味着它是无法被截断的。

有序广播（Ordered broadcasts）:则是一种同步执行的广播，在广播发出之后，同时刻只会有一个广播接收器能收到这条广播消息，当这个广播接收器中的逻辑执行完毕之后，广播才会继续传递。所以此时的广播接收器是有先后顺序的，优先级高的广播接收器就可以先收到广播消息，并且前面的广播接收器还可以截断正在传递的广播，这样后面的广播接收器就无法收到广播消息了。



### 3.3.1 动态注册

定义广播接收器

```java
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收到广播的逻辑处理
    }
}
```



在活动中注册广播接收器

```java
IntentFilter intentFilter = new IntentFilter();
intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
// 设置广播接收器的优先级（对于有序广播优先级越大最先接到广播）
intentFilter.setPriority(100);
// 广播接收器实例
NetworkChangeReceiver nR = new NetworkChangeReceiver();
registerReceiver(nR, intentFilter);
```



在活动中注销广播接收器

```java
unregisterReceiver(nR);
```



### 3.3.2 静态注册

定义广播接收器

```java
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收到广播的逻辑处理
    }
}
```



在AndroidManifest.xml静态注册广播接收器

```xml
<!-- enabled：是否启用这个广播接收器 -->
<!-- exported：是否允许这个广播接收器接收本程序以外的广播 -->
<!-- priority：对于有序，设置广播接收器的优先级 -->
<receiver
	android:name=".NetworkChangeReceiver"
	android:enabled="true"
	android:exported="true">
	<intent-filter
    	android:priority="100">
    	<action 
        	android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
    </intent-filter>
</receiver>
```



### 3.3.4 发送标准广播

```java
Intent intent = new Intent("com.lmy.androidstudy.MY_BROADCAST");
sendBroadcast(intent);
```



### 3.3.5 发送有序广播

```java
Intent intent = new Intent("com.lmy.androidstudy.MY_BROADCAST");
sendOrderedBroadcast(intent, null);
```



在广播接收器里截断广播

```java
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 截断广播
        abortBroadcast();
    }
}
```



## 3.4 Content Provider

​	内容提供（Content Provider）主要用于在不同的应用程序之间实现数据共享的功能，它提供了一套完整的机制，允许一个程序访问另一个程序中的数据，同时还能保证被访问数据的安全性。

​	内容提供器的用法一般有两种，一种是使用现有的内容提供器来读取和操作相应程序中的数据，另一种是创建自己的内容提供器给我们程序的数据提供外部访问接口。

### 3.4.1 使用内容提供器

​	对于每一个应用程序来说，如果想要访问内容提供器中共享的数据，就一定要借助`ContentResolver`类，可以通过`Context`中的`getContentResolver()`方法获取到该类的实例（这个类提供了数据的CRUD方法）。



内容URI由有三部分组成：

协议头：`content://`（表明是内容协议）

authority：`com.lmy.androidstudy.provider`（一般有包名+provider，用来标志是哪个应用程序的provider）

path：
`/table1`（需要共享的数据库表名）
`/table1/id`（对应表且对应id的一条数据）

```java
Uri uri = Uri.parse(
    "content://com.lmy.androidstudy.provider/table1");

Uri uri2 = Uri.parse(
    "content://com.lmy.androidstudy.provider/table1/1");
```



查询

```java
Cursor cursor = getContentResolver().query(
	uri, // 指定查询某个应用程序下的某一张表
    projection, // 查询指定的列名
    selection, // 指定Where的约束条件
    selectionArgs, // 为where中的占位符提供具体的值
    sortOrder // 指定长结果的排序方式
);

// 遍历
if(cursor != null){
    while(cursor.moveToNext()){
        String c1 = cursor.getString(cursor.getColumIndex("c1"));
        int c2 = cursor.getInt(cursor.getColumIndex("c2"));
    }
    cursor.close();
}
```



添加

```java
ContentValues values = new ContentValues();
values.put("c1", "text");
values.put("c2", 1);
getContentResolver().insert(uri, values);
```



更新

```java
ContentValues values = new ContentValues();
values.put("c1", "text2");
getContentResolver().update(
    uri, values, "c1=? and c2=?", new String[]{"text", "1"});
```



删除

```java
getContentResolver().delete(
    uri, values, "c2=?", new String[]{"1"});
```



### 3.4.2 创建内容提供器

创建自定义类，继承`ContentProvider`

```java
public class MyProvider extends ContentProvider{

    // 初始化数据库
    // true：初始化成功
    @Override
    public boolean onCreate() {
        return false;
    }

    // 必须提供的返回MIME类型
    // 根据MIME的规则来创建
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case 1:
                return "vnd.android.cursor.dir/vnd.com.lmy.androidstudy.provider.table1";
            case 2:
                return "vnd.android.cursor.item/vnd.com.lmy.androidstudy.provider.table1";
        }
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case 1:
                // 获取表中全部数据
                
                break;
            case 2:
                // 获取表中指定id的数据
                
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
```



`UriMatcher`类

```java
UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
// 三个参数：authority；path；自定义参数（自定义代码）
// 当UriMatcher使用match()方法时会返回【自定义参数（自定义代码）】，可判断出调用方期望拿到什么数据
uriMatcher.addURI("com.lmy.androidstudy.provider", "table1", 1);
uriMatcher.addURI("com.lmy.androidstudy.provider", "table1/#", 2);

// 通配符
// *:匹配任意长度的字符
// #:匹配任意长度的数字

```



MIME的规则

```java
// 内容URI有两种
// path结尾
"content://com.lmy.androidstudy.provider/table1";
// 对应的MIME
"vnd.android.cursor.dir/vnd.com.lmy.androidstudy.provider.table1"

// id结尾
"content://com.lmy.androidstudy.provider/table1/1"
// 对应的MIME
"vnd.android.cursor.item/vnd.com.lmy.androidstudy.provider.table1"
```



在AndroidManifest.xml配置

```xml
<provider
	android:authorities="com.lmy.androidstudy.provider"
	android:name=".MyProvider"
	android:enabled="true"
	android:exported="true">
</provider>
```



## 3.5 Intent

​	从上面的四大组件中可看出，intent都穿插在这四大组件中，为这四大组件服务的。Intent是Android程序中各组件之间进行交互的一种重要方式，它不仅可以指明当前组件想要执行的动作，还可以在不同组件之间传递数据。Intent一般可被用于启动活动、启动服务、以及广播发送等场景。

AndroidManifest.xml可配置的<intent-filter>：

<action>：活动能响应的动作。

<category>：活动响应的种类，可配置多个。

<data>：详细指定当前活动响应什么类型的数据。

```xml
<activity
	android:name=".MainActivity">
	<intent-filter>
		<action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
        <category android:name="com.lmy.androidstudy.MY_CATEGORY" />
        <data
              android:scheme="http"
              android:host="www.baidu.com"
              android:port="数据端口"
              android:path="域名之后的内容"
              android:mineType="指定数据类型；允许使用通配符">
        </data>
    </intent-filter>
</activity>
```



## 3.6 运行时权限

​	Android开发团队在Android6.0系统中引用了运行时权限这个功能，从而更好地保护用户的安全和隐私。

​	Android中有一共上百种权限，权限分为：普通权限、危险权限、特殊权限，危险权限运行时由用户授权，不然无法使用相应的功能。

危险权限：

| 权限组名   | 权限名                                                       |
| ---------- | ------------------------------------------------------------ |
| CALENDAR   | READ_CALENDAR<br />WRITE_CALENDAR                            |
| CAMERA     | CAMERA                                                       |
| CONTACTS   | READ_CONTACTS<br />WRITE_CONTACTS<br />GET_ACCOUNTS          |
| LOCATION   | ACCESS_FINE_LOCATION<br />ACCESS_COARSE_LOCATION             |
| MICROPHONE | RECORD_AUDIO                                                 |
| PHONE      | READ_PHONE_STATE<br />CALL_PHONE<br />READ_CALL_LOG<br />WRITE_CALL_LOG<br />ADD_VOICEMAIL<br />USE_SIP<br />PROCESS_OUTGOING_CALLS |
| SENSORS    | BODY_SENSORS                                                 |
| SMS        | SEND_SMS<br />RECEIVE_SMS<br />READ_SMS<br />RECEIVE_WAP_PUSH<br />RECEIVE_MMS |
| STORAGE    | READ_EXTERNAL_STORAGE<br />WRITE_EXTERNAL_STORAGE            |

注意：我们在进行运行时权限处理时使用的是权限名，当是用户一旦同意授权了，那么该权限组中所有的其他权限也会同时被授权。



申请电话权限

AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```

权限申请代码

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // 检测需要的权限是否已经授权
    if (ContextCompat.checkSelfPermission(
        MainActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
      	// 没有权限则申请权限
        // 系统会弹出授权弹窗
        // 【1】请求码，保证唯一即可
       	ActivityCompat.requestPermissions(
            MainActivity.this,
            new String[]{Manifest.permission.CALL_PHONE}, 1);
     } else {
     	// 跳转到打电话画面
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
	}
}


// 权限申请结果的回调函数
@Override
public void onRequestPermissionsResult(
    int requestCode, 
    @NonNull String[] permissions, 
    @NonNull int[] grantResults) {
	super.onRequestPermissionsResult(
        requestCode, 
        permissions, 
        grantResults);
    
    switch (requestCode){
      	case 1:
        	// 判断用户是否同意授权
            if(grantResults.length > 0 && grantResults[0] == 
                PackageManager.PERMISSION_GRANTED){
                // 跳转到打电话画面
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
            break;
       	default:
 	}
}
```

分析

```java
// 检测是否已经授权
ContextCompat.checkSelfPermission();

// 权限
Manifest.permission.CALL_PHONE;

// 返回值对比
// 表示已经授权
PackageManager.PERMISSION_GRANTED;

// 申请权限
ActivityCompat.requestPermissions();
```



## 3.7 异步处理消息

`Message`

Message是在线程之间传递消息，它可以在内部携带少量的信息，用于在不同线程之间交换数据。

```java
Message mg = Message.obtain();
// 判别字段
mg.what = 1;
// int
mg.arg1 = 1;
// int
mg.arg2 = 1;
// Object对象
mg.obj = 1;
```



`Handler`

Handler顾名思义也就是处理者的意思，它主要是用于发送和处理消息的。

```java
Handler handler = new Handler(Looper.myLooper()){    
    // 回调函数
	@Override
	public void handleMessage(@NonNull Message msg) {
		super.handleMessage(msg);
		switch (msg.what){
			case 1:
				// 处理程序
				break;
			default:
		}
	}
};

// 发送消息
handler.sendMessage();
```



`MessageQueue`

MessageQueue是消息队列的意思，它主要用于存放所有通过Handler发送的消息。

每个线程中只会有一个MessageQueue对象（主线程中，都是有android系统底层管理着）。



`Looper`

Looper是每个线程中的`MessageQueue`的管家，调用Looper的loop()方法后，就会进入到一个无线循环当中，然后每当发现`MessageQueue`中存在一条消息，就会将它取出，并传递到`Handler`的handleMessage()方法中。每个线程中只会有一个`Looper`对象（主线程中，都是有android系统底层管理着）。

// 流程图待补充

// ...



------



`AsyncTask`

为了更加方便我们在子线程中对UI进行操作，Android提供了一些好用的工具，如`AsyncTask`。`AsyncTask`背后的实现原理也是基于异步消息处理机制的，只不过Android帮我们做了很好的封装。

基本用法，继承`AsyncTask`类并指定3个泛型参数，泛型参数用途如下：

`Params`：在执行`AsyncTask`时需要传入的参数，可用于在后台任务中使用。

`Progress`：后台任务执行时，如果需要在界面上显示当前的进度，则使用这里指定的泛型作为单位。

`Result`：当任务执行完毕后，如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型。

```java
// Void: 表示在执行AsyncTask的时候不需要传入参数给后天任务
// Integer: 表示使用整形数据来作为进度显示单位
// Boolean：表示使用布尔类型数据来反馈执行结果
class MyAsyncTask extends AsyncTask<Void, Integer, Boolean>{
    // 这个方法会在后台任务开始执行之前调用，主要界面上的初始化操作。
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 这个方法中的所有代码都会在子线程中运行，我们应该在这里去处理所有的耗时任务
    @Override
    protected Boolean doInBackground(Void... voids) {
        // 此方法中不能更新UI，需要更新UI要调用
        // 当此方法别调用时，会执行回调函数onProgressUpdate()
        publishProgress(Progress...);
        
        // 任务执行结果与AsyncTask第三个泛型参数对应
        return true;
    }

    // 当后台任务中调用publishProgress()方法后，onProgressUpdate()方法
    // 就会很快被调用,该方法中携带的参数就是在后台任务中传递过来的
    // 在方法中可以对UI进行操作
    @Override
    protected void onProgressUpdate()(Integer... values) {
        super.onProgressUpdate(values);
    }

    // 当后台任务执行完毕并通过return语句进行返回时，这个方法就很快会被调用。
    // 返回参数传递到此方法中，可以利用返回的数据来进行一些UI操作。
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
```



# 4 系统控件

​	Android系统为开发者提供了丰富的系统控件，使得我们可以很轻松地编写出漂亮的界面。当然如果你品位比较高，不满足于系统自带的控件效果，也完全可以制定属于自己的控件。



## 4.1 Menu

创建文件：res/menu/main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/add_item"
        android:title="add"/>
    <item
        android:id="@+id/remove_item"
        android:title="remove"/>
</menu>
```



重写活动的函数

```java
// 加载menu布局文件
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu, menu);      
	return super.onCreateOptionsMenu(menu);
}

// menu项的点击事件
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
	switch (item.getItemId()){
		case R.id.add_item:
			// ...
			break;
		case R.id.remove_item:
			// ...
			break;
		default:
	}
	return super.onOptionsItemSelected(item);
}
```



## 4.2 布局



## 4.3 控件

### 4.3.1 TextView

TextView它主要用于在界面上显示一段文本信息。

```xml
<TextView
	android:layout_width="wrap_content"//宽度
	android:layout_height="wrap_content"//高度
	android:text="@string/android"//文本内容
	android:textSize="@dimen/font_80"//字体大小
    android:textColor="@color/android_green"//设置文字颜色
    android:background="#FF0000FF" //控件的背景颜色，填充整个控件的颜色，可以是图片
    android:gravity="center"//设置控件中内容的对齐方向
    android:shadowColor="@color/android_green"//设置阴影颜色
    android:shadowRadius="@integer/shadow_radius_10"//设置阴影的范围
 	android:shadowDx="@integer/shadow_dx_10"//阴影的水平偏移量
	android:shadowDy="@integer/shadow_dy_10"//阴影的垂直偏移量
    android:textStyle="bold"//设置文字风格normal，bold，italic（斜体）
    android:textFontWeight="@integer/font_weight_800"//字体加粗
    android:fontFamily="serif"//字体样式
    android:typeface="normal"//字体格式normal,sans,serif,monospace />
```



### 4.3.2 Button

Button按钮是程序用于和用户进行交互的一个重要控件。

```xml
<Button
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:text="@string/android"
	android:textAllCaps="false" //所有英文自动进行大写转换
	android:clickable="false" //按钮是否允许点击/>
```



点击事件代码

```java
// 匿名类的方式
Button button = findViewById(R.id.id_bt);
button.setOnClickListener(new View.OnClickListener(){
	@Override
	public void onClick(View view) {
	}
});
```

```java
// 实现接口的方式
public class ViewActivity extends ActionBarActivity implements View.OnClickListener{
	// ...
    // 其他代码
    // ...
    
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_bt:
                // 在此处添加逻辑
                break;
        }
    }
}
```



## 4.4 自定义控件

​                   

## 4.5 碎片

碎片（Fragment）是一种可以嵌入在活动当中的UI片段。



### 4.5.1 静态注册

新建碎片布局my_fragment.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MyFragment">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/android"
        android:textSize="@dimen/font_80"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
    
</androidx.constraintlayout.widget.ConstraintLayout>
```



新建MyFragment类继承Fragment类。

```java
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(
        LayoutInflater inflater, 
        ViewGroup container,Bundle savedInstanceState) {
  		// 加载布局文件并返回
        return inflater.inflate(
            R.layout.my_fragment, container, false);
    }
    
}
```



xml中引入碎片

```xml
<!-- name为自定义MyFragment类完全类名 -->
<fragment
    android:id="@+id/id_fragment"
	android:name="com.lmy.androidstudy.fragment.MyFragment"
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	app:layout_constraintStart_toStartOf="parent"
	app:layout_constraintTop_toTopOf="parent" />
```



### 4.5.2 动态注册 

新建碎片布局my_fragment.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MyFragment">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/android"
        android:textSize="@dimen/font_80"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
    
</androidx.constraintlayout.widget.ConstraintLayout>
```



新建MyFragment类继承Fragment类。

```java
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(
        LayoutInflater inflater, 
        ViewGroup container,Bundle savedInstanceState) {
  		// 加载布局文件并返回
        return inflater.inflate(
            R.layout.my_fragment, container, false);
    }
    
}
```



xml中放置碎片位置的标签一般使用<FrameLayout>标签

```xml
<FrameLayout
	android:id="@+id/my_layout"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content">
</FrameLayout>
```



活动中进行动态添加碎片

```java
// 创建碎片实例
MyFragment myFragment = new MyFragment();
// 获取碎片管理器
FragmentManager fragmentManager = getSupportFragmentManager();
// 开启一个事务
FragmentTransaction fT = fragmentManager.beginTransaction();
// 向容器内添加或替换碎片，一般使用replace()方法
fT.replace(R.id.my_layout, myFragment);
// 把碎片添加到返回栈中
// null描述返回栈的状态，一般传入null
fT.addToBackStack(null);
// 提交事务
fT.commit();
```



### 4.5.3 碎片和活动通信

在活动中调用碎片里的方法

为了方便碎片和活动之间进行通信，`FragmentManager`提供了一个类似于`FindViewById()`的方法，专门用于从布局文件中获取碎片的实例（注意这里是针对静态添加碎片的方式，毕竟动态添加时本身就先获取了碎片实例）。

```java
// 获取碎片管理器
FragmentManager fragmentManager = getSupportFragmentManager();
// 获取碎片实例
// 就可以在活动中调用碎片的方法
MyFragment myFragment = fragmentManager.FindViewById(
    R.id.id_fragment);
```

------



在碎片中调用活动里的方法

每个碎片中都可以通过调用`getActivity()`方法来得到和当前碎片相关联的活动实例。

```java
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(
        LayoutInflater inflater, 
        ViewGroup container,Bundle savedInstanceState) {
        // 获取活动实例
        // 就可以调用活动的方法
        MainActivity activity = (MainActivity)getActivity();
        
        return inflater.inflate(
            R.layout.my_fragment, container, false);
    }
    
}
```



------



在碎片中调用另一个碎片的方法

首先在一个碎片中可以得到与它相关联的活动，然后再通过这个活动去获取另外一个碎片的实例。



### 4.5.4 碎片的生命周期

生命周期的流程图

// ...

// 等待补充

```java
class MyFragment extends Fragment{
    // 当碎片和活动建立关联的时候调用
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 为碎片加载布局时调用
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 确保与碎片相关联的活动一定已经创建完毕的时候调用
    // 已经过时使用onViewCreated代替
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    // 当与碎片关联的视图被移除的时候调用
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    // 当碎片和活动解除关联的时候调用
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
```



## 4.6 动态加载布局

程序根据设备的分辨率或屏幕大小在运行时来决定加载那个布局。

### 4.6.1 限定符

使用方式在`res`目录下创建文件夹`layout-限定符`，程序根据设备的分辨率或屏幕大小在运行时来决定加载那个布局。

| 屏幕特征 | 限定符    | 描述                                                         |
| -------- | --------- | ------------------------------------------------------------ |
| 大小     | small     | 提供给小屏幕设备的布局资源                                   |
| 大小     | normal    | 提供给中等屏幕设备的布局资源                                 |
| 大小     | large     | 提供给大屏幕设备的布局资源                                   |
| 大小     | xlarge    | 提供给超大屏幕设备的布局资源                                 |
| 分辨率   | ldpi      | 提供给低分辨率设备的布局资源（120dpi下）                     |
| 分辨率   | mdpi      | 提供给中等分辨率设备的布局资源（120dpi~160dpi）              |
| 分辨率   | hdpi      | 提供给高分辨率设备的布局资源（160dpi~240dpi）                |
| 分辨率   | xhdpi     | 提供给超高分辨率设备的布局资源（240dpi~320dpi）              |
| 分辨率   | xxgdpi    | 提供给超超高分辨率设备的布局资源（320dpi~480dpi）            |
| 方向     | land      | 提供给横屏设备的布局资源                                     |
| 方向     | port      | 提供给竖屏设备的布局资源                                     |
| 最小宽度 | sw[xxx]dp | 如：layout-sw600dp，当成程序运行在屏幕<br />大于等于600dp的设备上时，<br />会加载layout-sw600dp/下的资源否则默认加载layout/下的资源 |



## 4.7 动画



### 4.7.1 逐帧动画

// 等待补充...



### 4.8.2 补间动画

创建动画资源在：../res/anim/name_anim.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="600"> <!-- 动画时间 -->
    <alpha/><!-- 透明 -->
	<scale/> <!-- 缩放 -->
	<translate/><!-- 位移 -->
	<rotate/><!-- 旋转 -->
</set>
```



使用动画

```java
// 设置动画的view对象
RobotView robotView = findViewById(R.id.id_robot);
// 加载动画资源
Animation animIn = AnimationUtils.loadAnimation(this, R.anim.scale_anim_in);
Animation animOut = AnimationUtils.loadAnimation(this, R.anim.scale_anim_out);

// 给view对象设置进入时的动画
robotView.startAnimation(animIn);

// 保持动画结束时的状态
animOut.setFillAfter(true);
// 给view对象设置退出时的动画
robotView.startAnimation(animOut);

// 监听动画的各个状态
animOut.setAnimationListener(new Animation.AnimationListener() {
	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		IntroActivity.actionStart(MainActivity.this);
    }

	@Override
	public void onAnimationRepeat(Animation animation) {
	}
});
```



# 参考文件

（1）郭霖. 第一行代码 Android 第二版，人民邮电出版社，2016.12