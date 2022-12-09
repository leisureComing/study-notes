# Git笔记

2022年11月11号开始记录



# 1 安装Git

下载网址：https://git-scm.com/download/win



# 2 配置身份

```shell
# 配置名称
git config --global user.name "leisure"

# 配置邮箱
git config --global user.email "1610001894@qq.com"
```



# 3 创建本地仓库

```sh
# 在项目的根目录下运行
git init
```



# 4 提交到本地仓库

```shell
# 添加文件
git add 文件名

# 添加目录
git add 目录名称

# 添加当前目录的全部文件
git add .

# 提交已添加的文件
git commit -m "提交日志."
```



# 5 忽略文件

git会检查`.gitignore`文件，会把文件里的文件和目录排除在版本控制之外。

```shell
*.iml
.gradle
/local.properties
/.idea/caches
/.idea/libraries
/.idea/modules.xml
/.idea/workspace.xml
/.idea/navEditor.xml
/.idea/assetWizardSettings.xml
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
local.properties
```



# 6 查看修改内容

```shell
# 粗看是否有修改
git status

# 详细查看修改内容
git diff
# 具体查看那个文件
git diff Git笔记.md
```



# 7 撤销未提交的修改

```shell
git checkout Git笔记.md
```



# 8 查看提交的记录

```shell
# 查看全部
git log

# 查看具体一条记录
git log 8ee7cc0257a49d31f11e0b81c17535db1cc5fc4b -1

# 查看具体一条记录的修改内容
git log 8ee7cc0257a49d31f11e0b81c17535db1cc5fc4b -1 -p
```



# 9 分支

```shell
# 查看全部分支
git branch

# 创建分支
git branch version1

# 切换分支
git checkout version1

# 合并分支
# 需要回到主分支
# 合并时可能出现代码冲突，需要静心慢慢解决这些冲突
git checkout master
git merge version1

# 删除分支
git branch -D version1
```



# 10 远程仓库

```shell
# 克隆远程仓库到本地
git clone 远程仓库地址

# 把本地文件推送到远程仓库
# origin代表远程仓库地址
# master指同步到的具体分支
git push origin master

# 在已经克隆远程仓库到本地的前提下
# 把远程仓库的最新代码拉取到本地
git pull origin master
```

