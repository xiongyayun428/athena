
# bootstrap和application的区别
```
bootstrap.yml（bootstrap.properties）先加载
application.yml（application.properties）后加载

Spring Cloud 构建于 Spring Boot 之上，在 Spring Boot 中有两种上下文，一种是 bootstrap,另外一种是 application,
application 配置文件这个容易理解，主要用于 Spring Boot 项目的自动化配置。
bootstrap 是应用程序的父上下文，也就是说 bootstrap 加载优先于 applicaton。
bootstrap 主要用于从额外的资源来加载配置信息，还可以在本地外部配置文件中解密属性。
这两个上下文共用一个环境，它是任何Spring应用程序的外部属性的来源。
bootstrap 里面的属性会优先加载，它们默认也不能被本地相同配置覆盖。
boostrap 由父 ApplicationContext 加载，比 applicaton 优先加载

boostrap 里面的属性不能被覆盖
```