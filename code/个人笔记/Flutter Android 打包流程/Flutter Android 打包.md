# Flutter Android 打包



## 获取签名证书

```bash
# [~/key.jks]路径+签名证书[*.jks]
# [-alias key] 别名
keytool -genkey -v -keystore ~/key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias key
```



## 创建key.properties

```makefile
storePassword=<生成keystore时设置的密码>
keyPassword=<生成keystore时设置的密码>
keyAlias=<生成keystore时的别名>
storeFile=<生成文件的key.jks的文件路径>
```

![image-20221018144927862](.\image-20221018144927862.png)



## 修改build.gradle

app/build.gradle

```Groovy
//...

def keystorePropertiesFile = rootProject.file("key.properties")
def keystoreProperties = new Properties()
keystoreProperties.load(new FileInputStream(keystorePropertiesFile))

android {
	//...
    signingConfigs {
        release {
            keyAlias keystoreProperties['keyAlias']
            keyPassword keystoreProperties['keyPassword']
            storeFile file(keystoreProperties['storeFile'])
            storePassword keystoreProperties['storePassword']
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release
        }
    }
}
//...
```

![image-20221018145222466](.\image-20221018145222466.png)



## 运行打包命令

```bash
flutter build apk
```

