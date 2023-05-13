# esay-push

> 多场景信息推送解决方案

## 0.项目背景

在日常开发中，我们经常需要向多个渠道进行数据推送，例如：

- 发送xx消息到某个钉钉群
- 发送xx邮件到某个邮箱
- 发送xx短信给某个客户
- ...

作为开发者，我们不可避免的需要去查阅渠道方的技术文档，再根据文档去书写对应的代码，这是一件十分枯燥且繁琐的事情。

`easy-push`项目的初衷，就是为了解决这个令人烦恼的问题。

## 1.修改历史



## 2.接入指南

### 2.1 Java体系

一行代码实现发送消息到钉钉

```java
MessageContext messageContext = new SendMessageServiceImpl().singleSend(new DingTextMessage("abcdefghijk"));
```

#### 2.1.1 Java SE

##### 1.导入pom

##### 2.构建信息实体

> 根据场景构建对应的信息实体

```java
// 钉钉-文本类型消息
Message message = new DingTextMessage("123");
```

##### 3.创建信息发送对象

```java
SendMessageService sendMessageService = new SendMessageServiceImpl();
```

##### 4.执行发送方法

```java
// 传入message实体 
MessageContext messageContext = sendMessageService.singleSend(message);
```

##### 5.从返回中获取信息

```java
Boolean state = messageContext.getState();
```

#### 2.1.2 Spring Boot

