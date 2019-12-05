## lxd社区

## 快速运行
1. 安装必备工具  
JDK，Maven
2. 克隆代码到本地  
3. 运行命令创建数据库脚本
```sh
mvn flyway:migrate
```
4. 运行打包命令
```sh
mvn package
```
5. 运行项目  
```sh
java -jar target/community-0.0.1-SNAPSHOT.jar
```
6. 访问项目
```
http://localhost:8887
```


## 资料
>> [对照elasticsearch网站开发](https://elasticsearch.cn/explore/)
>> [Spring 文档](https://spring.io/guides)    
>> [Spring Web](https://spring.io/guides/gs/serving-web-content/)   
[es](https://elasticsearch.cn/explore)    
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
[Bootstrap](https://v3.bootcss.com/getting-started/)    
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
[Mysql菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)    
[H2数据库](http://www.h2database.com/html/main.html)
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
[Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[Markdown 插件](http://editor.md.ipandao.com/)   
[UFfile SDK](https://github.com/ucloud/ufile-sdk-java)  
[Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)  

## 工具
>> [Git](https://git-scm.com/download)   
[Visual Paradigm时序图](https://www.visual-paradigm.com)    
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Maven](https://mvnrepository.com/)
[Lombok](https://www.projectlombok.org)    
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
[Okhttp](https://square.github.io/okhttp/)
### 前端资源下载
- 用于生产环境的 Bootstrap
## 脚本
```sql
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
```
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

## 更新日志
- 2019-7-30 修复 session 过期时间很短问题   
- 2019-8-2 修复因为*和+号产生的搜索异常问题  
- 2019-8-18 添加首页按照最新、最热、零回复排序  
- 2019-8-18 修复搜索输入 ? 号出现异常问题
- 2019-8-22 修复图片大小限制和提问内容为空问题
## 知识整理
- 2019-12-4 http:localhost:8887/hello?name=lxd [协议+域名+端口号（默认80可省略）+路径+?+参数]
- 带注解的文件只要和启动类在同一级或者在下一级都能够识别自动加载进来
- 授权登录
>> github->setting->Developer setting->community
## 快捷键
- ctrl+p 参数显示
- ctrl+shift+R 进入某个文件
- ctrl+shift+F12 将编辑页面最大化
- alt+shift+R 更改文件名
- alt+insert 插入set get方法
- ctrl+alt+v 将参数中的对象从外面new
- shift + enter 将光标移动到下一行的开始
- ctrl + shift + n 打开一个匿名窗口（清空缓存）
- ctrl + e 快速切换窗口
- shift + F6 快速修改变量名
### Git
- git init
- git add README.md
- git commit -m "first commit"
- git remote add origin git@github.com:1373006963lxd/community.git  可以将项目添加到已经创建的厂库里面
- git push -u origin master