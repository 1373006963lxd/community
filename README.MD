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
java -jar 
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
[MybatisGenetate](http://mybatis.org/generator/)
## 工具
>> [Git](https://git-scm.com/download)   
[Visual Paradigm时序图](https://www.visual-paradigm.com)    
[Flyway 执行数据库脚本](https://flywaydb.org/getstarted/firststeps/maven)  
[Maven](https://mvnrepository.com/)
[Lombok](https://www.projectlombok.org)    
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
[Okhttp](https://squar**e.github.io/okhttp/)
### 前端资源下载
- 用于生产环境的 Bootstrap
- 下载没有解压的[jquery](https://jquery.com)
- [js-css](https://v3.bootcss.com)
- [小图标](https://www.iconfont.cn/)
## 脚本
```sql
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `bio` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
```
```sql
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  `view_count` int(11) DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `tag` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```sql
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `commentator` int(11) NOT NULL,
  `gmt_creator` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `like_count` bigint(20) unsigned zerofill NOT NULL,
  `cotent` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```sql
CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `notifier` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `outerId` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `status` int(10) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
另一种方式 run-edit Configuration-maven-添加以上命令-apply-run这个配置
```
### MVN参考资料
>> [Intellij IDEA添加一个“Run运行”选项，使用maven运行mybatis-generator-maven-plugin插件 ](https://www.cnblogs.com/liaojie970/p/7058543.html)<br/>
>> [MyBatisGenerator配置](https://blog.csdn.net/one_dayR/article/details/85098387)
>> [moment](http://momentjs.cn/)
>> [MarkDown](https://pandao.github.io/editor.md/)
>> [MarkDownGithub](https://github.com/pandao/editor.md)
## 更新日志
- 2019-7-30 修复 session 过期时间很短问题   
- 2019-8-2 修复因为*和+号产生的搜索异常问题  
- 2019-8-18 添加首页按照最新、最热、零回复排序  
- 2019-8-18 修复搜索输入 ? 号出现异常问题
- 2019-8-22 修复图片大小限制和提问内容为空问题
## 知识整理
- 2019-12-4 http:localhost:8887/hello?name=lxd [协议+域名+端口号（默认80可省略）+路径+?+参数]
- 带注解的文件只要和启动类在同一级或者在下一级都能够识别自动加载进来
- 授权登录setting->Personal access token->生成token 通过https://api.github.com/user?access_token=xxxxxxxxxxxx查看个人信息
- 引入jquery($)后，在前端控制台输入$回车返回一个方法--则成功
- 引入jquery之后可以方便查找某一个id标签$(".id")
- mybatisGenerate解决数据库中添加字段所引起的联动性的修改(model，Mapper映射)
- Mysql中字段类型为text的值, java使用selectByExample查询为null(检索的字段中包含大字段类型时，必须用selectByExampleWithBLOBs，不检索大字段时，用selectByExample就足够了。update同样如此)
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
- shift 长按  复制两个光标之间的内容
- ctrl 长按 点击某个方法名--查看引用方法的地方
- ctrl + alt + o 删除多于的依赖
- 右键-FindUseages 找到引用该方法的地方
- ctrl+alt+L 格式化
- ctrl+z ctrl + y 回撤，对立 
- ctrl+alt+ p 抽参数
- ctrl+ alt + v 抽变量
- ctrl+shift+t 浏览器页面回撤
- ctrl+shift+M 抽取方法的快捷键(选中要抽取的代码快，右键-Refactor-Extract-Method)
### Git
- git init
- git add README.md
- git commit -m "first commit"
- git remote add origin git@github.com:1373006963lxd/community.git  可以将项目添加到已经创建的厂库里面
- git push -u origin master
### Git注意事项
- 当团队协作的时候，在git push 的时候，一定要先git pull一下-拉取最新的代码
- git log 打印日志
- git show 展示做的什么操作
- git reset 提交记录码  当在git push之前又修改了东西，可以用这个命令
- git status 可以查看每一步操作后的状态
- git branch branch1(分支名)  创建分支
- git checkout branch1   切换到指定分支
- git托管，当代码改错地方时候，可以文件-右键-git-revert即可回去
- 项目-右键-git-showhistory-查看提交历史-双击即可查看对比修改的页面(类同SVN)
- 项目-右键-localhistory-查看历史记录
### 在idea中解决冲突
- 项目->右键->git->Resove->双击冲突文件->解决冲突()
- 两边分别是你和新的代码，中间是合并后的代码，根据你和冲突部分的选择，最后解决中间文件问题
- idea方便的原因是：冲突部分分别有左右箭头，根据需要可加入可不加入来解决冲突
### 前端样式
- 样式调整在后面追加即可
### Mysql语句
- alter table user drop column bio
- alter table user modify column avatar_url varchar(256);
### 异常处理(当不清楚到底什么异常的时候--无causeBy)
- 点击右上角debug->点击左下方debug(红色实心圈)->左上角＋号->java Exception xxx-->赋值(控制台抛的异常)->