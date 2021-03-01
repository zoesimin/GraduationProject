#部署智能导学系统后端
# SpringBoot Restful API后端

数据库采用 Neo4j 图数据库

## 项目生成 jar 包

- 进入项目根目录下，target目录下生成jar包

```
mvn clean package
```

- jar 包上传到服务器上，执行命令，正常运行：

```
java -jar mindmap-backend-0.0.1-SNAPSHOT.jar
```
 
 ## 使用start.sh脚本在云上部署

在云上新建一个单独的文件夹，将打包的mindmap-backend-0.0.1-SNAPSHOT.jar重名为mindmap_backend.jar放入此文件夹中，同时将start.sh脚本放在此文件中<br/>
执行start.sh脚本
```
./start.sh start
```
看到启动成功的文字，说明后端已经部署成功！

## 学习地址
本项目在github的位置：[https://github.com/mind-edu](https://github.com/mind-edu)<br/>
参考学习地址：[https://github.com/PhysicalElective2/test](https://github.com/mind-edu)


 

