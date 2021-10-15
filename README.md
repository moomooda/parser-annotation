# parser-annotation
本工具用于组内依存标注，现有的能力是支持现代汉语的语义依存标注，接下来的目标是支持古代汉语的语义依存标注

## 工具开发
-   本地安装jdk1.8，安装集成开发环境 Jetbrains IntelliJ IDEA
-   安装git
-   安装mysql客户端工具 推荐 Jetbrains DataGrip

控制台如图所示，就是项目导入并且启动成功

![微信截图_20211015084307](C:\Users\maodazhan\Desktop\微信截图_20211015084307.png)

并且打开浏览器 输入 localhost:8080/annotation/index

![微信截图_20211015090857](C:\Users\maodazhan\Desktop\微信截图_20211015090857.png)



## 代码开发流程

1)基于master分支创建本地分支，比如创建dev分支

git checkout -b dev

2)本地开发代码测试完毕后

3) git add .

4) git commit -m "{增加的功能信息}"

5) git push origin dev

6) 然后从origin dev分支pull request 到origin master分支

最后由工具管理员负责merge代码，并且完成部署到线上的操作

##  数据库表相关

user:用户表

corpus：语料表

corpus_status:将数字和语料状态对应

batch_info:一个批次的语料为一条数据

batch_user:将batch_user和batch_info对应起来，

  如：法律语料（batch_info id为5）需要珮珮（user id为6）和黄恬（user id为7）标注，

   则增加两条数据：  5，6；5,7

### 测试数据库地址

jdbc:mysql://175.24.14.16:3306/annotation_dev

### 线上数据库地址

jdbc:mysql://parser:3306/annotation

## 语料导入导出相关

python目录下的两个python文件daochu.py、tosql.py分别对应导入导出

CoNLL格式语料转sql文件：tosql.py

平台语料导出为CoNLL格式文件：daochu.py

### 导入数据库

1. 执行“插入批次”查询语句，修改‘name’字段为导入批次名称；

2. 执行“插入批次和用户”查询语句，修改‘batch_id’和‘user_id’两个字段，将用户和批次进行对应；

3. 将CoNLL格式语料转换为SQL语句导入数据库，运行tosql.py文件，有以下几个变量需要更改：

   1. fi = open("fine_drama.txt", "r", encoding='utf-8') 语料文件
   2. name：生成的sql文件名称
   3. batch_id：语料对应的批次
   4. user_id：语料对应的用户

   结果就是将语料导入到该用户下。

4. 执行生成的SQL文件即可；

   ![image-20211015090253954](C:\Users\maodazhan\AppData\Roaming\Typora\typora-user-images\image-20211015090253954.png)

![image-20211015090300696](C:\Users\maodazhan\AppData\Roaming\Typora\typora-user-images\image-20211015090300696.png)

### 导出数据库

运行daochu.py文件，修改查询语句和输出文件名称。

![image-20211015090337795](C:\Users\maodazhan\AppData\Roaming\Typora\typora-user-images\image-20211015090337795.png)