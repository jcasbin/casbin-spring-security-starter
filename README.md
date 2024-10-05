# casbin-spring-security-starter
![License](https://img.shields.io/github/license/jcasbin/casbin-spring-security-starter)
[![Maven Central](https://img.shields.io/maven-central/v/org.casbin/casbin-spring-security-starter.svg)](https://central.sonatype.com/artifact/org.casbin/casbin-spring-security-starter)

## Installation

``` xml
<dependency>
    <groupId>org.casbin</groupId>
    <artifactId>casbin-spring-security-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## inform
    To skip the tedious and complicated login process of Spring Security, 
    we're using its built-in login mode, which allows us to focus on the model authentication module.

## DataBase 
    For the database, we're using mysql. 
    Remember to create a casbin database if you don't have one, and configure it in the application.yml file.

## Simple Example
    check file examples.rbac.policy.csv you can find out we are using rbac model
    I registered with the identity of Alice who can read data1
![img_1.png](img_1.png)

    Than test the  result
![img_2.png](img_2.png)

![img_3.png](img_3.png)
