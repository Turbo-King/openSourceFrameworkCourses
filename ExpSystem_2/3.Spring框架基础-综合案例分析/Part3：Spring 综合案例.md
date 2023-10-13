# Part3：Spring IOC & AOP综合案例分析

## 1、案例描述

例如，在一个业务系统中，通常会把业务系统划分成controller、service、dao（mapper）等几个层，在一些Java应用项目中，service层的一些方法需要不同的权限才能访问。如有些方法只有用户身份（即角色）为admin的才允许操作，其他身份不允许操作。本案例以商品信息的CRUD操作为例进行讲解。

**（1）传统的做法及存在的问题**

**做法：**在service层把用户身份判断的功能代码写入到每个业务流程中。**存在的问题：**这样会造成代码冗余，维护也非常麻烦，当需要修改用户身份功能时，就需要修改每个业务流程的代码，这种处理方式显然是不可取的。

**（2）Spring AOP的做法**

比较好的做法是把**用户身份判断**的功能抽取出来，形成独立的模块，当业务流程需要时，系统自动把登录功能切入到业务流程中。

## 2、案例实现

### 2.1 关键技术

**自定义注解+注解解析**+Spring AOP + Spring IOC

注解解析是通过Java反射技术实现的，有关Java反射技术见本章**补充知识1**。

### 2.2 实现思路

（1）自定义一个注解PrivilegeInfo，使用这个注解为service层中的方法进行权限配置；

（2）编写一个注解解析器PrivilegeInfoAnnoParse。解析注解@PrivilegeInfo(name=” *”)  ，注解解析器应该把@PrivilegeInfo中的name属性值解析出来；

（3）在AOP中根据PrivilegeInfo注解的值，判断用户是否拥有访问目标方法的权限，有则访问目标，没有则给 出提示。

### 2.3 实现过程

#### 2.3.1 新建一个项目

新建一个基于Maven的Java项目，项目结构如下图所示。

![](3.Spring 综合案例\项目结构.png)



#### 2.3.2 dao和service层类的设计

（1）GoodsDao接口

````java
public interface GoodsDao {
    public void add();
    public void update();
    public void delete();
    public void find();
}
````

（2）GoodsDaoImpl实现类

````java
import com.xzit.springboot.privilege.dao.GoodsDao;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsDaoImpl implements GoodsDao {
    public void add() {
        System.out.println("添加商品...");
    }
    public void update() {
        System.out.println("修改商品...");
    }
    public void delete() {
        System.out.println("删除商品...");
    }
    public void find() {
        System.out.println("查询商品...");
    }
}
````

（3）GoodsService接口

````java
public interface GoodsService {
    public void add();
    public void update();
    public void delete();
    public void find();
}
````

（4）GoodsServiceImpl实现类

````java
import com.xzit.springboot.privilege.dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public void add() {
        goodsDao.add();
    }
    public void update() {
        goodsDao.update();
    }
    public void delete() {
        goodsDao.delete();
    }
    public void find() {
        goodsDao.find();
    }
}
````

#### 2.3.3 创建注解类PrivilegeInfo

````java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义的注解
 */
//注解的作用目标：方法
@Target(ElementType.METHOD)
//注解传递存活时间，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivilegeInfo {
    //角色的名字
    String role() default "";
}
````

#### 2.3.4 创建注解解析器类PrivilegeInfoAnnoParse

````java
import java.lang.reflect.Method;
/**
 * 权限注解解析器
 * 作用：解析目标方法上如果有PrivilegeInfo注解，
 * 解析出这个注解中的role值（角色的值）
 */

public class PrivilegeInfoAnnoParse {
    /**
     * 解析注解 
     * @param targetClassName 目标类（Class形式）
     * @param methodName      目标方法（在客户端调用哪个方法，methodName就代表哪个方法）
     * @return
     * @throws Exception
     */
    public static String parse(Class targetClassName, String methodName) throws Exception {
        //获得目标方法
        Method method = targetClassName.getMethod(methodName);
        String methodAccess = "";
        //判断目标方法上面是否存在@PrivilegeInfo注解
        //@Privilege（role="admin"）
        if (method.isAnnotationPresent(PrivilegeInfo.class)) {
            //得到方法上的注解
            PrivilegeInfo privilegeInfo = method.getAnnotation(PrivilegeInfo.class);
            //得到注解中的name值
            methodAccess = privilegeInfo.role();
        }
        return methodAccess;
    }
}
````

#### 2.3.4 为service层方法添加注解

通过上面的操作，自定义的注解和解析器有了，现在应将对应的Service层中，在要使用访问权限的方法上添加这个注解。一般情况下，Service层都包含接口和实现类，注解应该添加到实现层的某个方法上。本案例将上面的注解添加到商品添加（add）方法上。

````java
//配置PrivilegeInfo注解
@PrivilegeInfo(role = "admin")
public void add() {
    goodsDao.add();
}
````

#### 2.3.5 创建切面类GoodsPrivilegeAspect

该切面使用的是环绕通知，在调用目标方法之前，我们需要在程序中做以下处理：

现有目标方法上的PrivilegeInfo注解配置的权限，与用户拥有的权限进行匹配。

​	a) 如果匹配成功，则认为用户有这个权限使用该目标方法

​	b)如果匹配失败，则给出无权访问的提示信息

````java
package com.xzit.springboot.privilege.aspect;

import com.xzit.springboot.privilege.annotation.PrivilegeInfoAnnoParse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.*;

@Aspect //Aspect注册
@Component //Bean注册
public class GoodsPrivilegeAspect {
    //用户角色：一个用户可能拥有多个角色，所以使用集合
    private List<String> userRoles = new ArrayList<String>();
    public List getUserRoles() { return userRoles; }

    //定义切入点
    @Pointcut("execution(* com.xzit.springboot.privilege.service.serviceimpl.*.*(..))")
    private void rolePointCut() {
    }
    //环绕通知
    @Around("rolePointCut()")
    public Object accessMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * * 得到目标类的class形式
         * * 得到目标方法 */
        Class targetClass = joinPoint.getTarget().getClass();
        String targetMethodName = joinPoint.getSignature().getName();
        String methodAccess = PrivilegeInfoAnnoParse.parse(targetClass, targetMethodName);
        boolean flage = false;
        for (String role : userRoles) {
            if(methodAccess.equals(role)){
                flage = true; break;
            }
        }
        if(flage){
            //如果匹配成功，则认为用户有这个权限使用该目标方法
            return joinPoint.proceed();
        } else {
            //如果匹配失败，则给出无权访问的提示信息
            System.out.println("权限不足");
            return null;
        }
    }
}
````

#### 2.3.6 创建配置文件applicationContext.xml

````xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--扫描含com.mengma包下的所有注解-->
    <context:component-scan base-package="com.xzit.springboot.privilege"/>
    <!-- 使切面开启自动代理 -->
    <aop:aspectj-autoproxy></aop:aspectj-autoproxy>

</beans>
````

#### 2.3.7 编写测试类

````java
@Test
public void privilegeTest(){
  ApplicationContext applicationContext =
    new ClassPathXmlApplicationContext("applicationContext1.xml");
  //初始化用户角色
  GoodsPrivilegeAspect goodsPrivilegeAspect =
    (GoodsPrivilegeAspect)applicationContext.getBean(GoodsPrivilegeAspect.class);
  List<String> roles = goodsPrivilegeAspect.getUserRoles();
  roles.add("user");
  System.out.println(goodsPrivilegeAspect);
  //获得service对象
  GoodsService goodsService = 
    (GoodsService)applicationContext.getBean(GoodsService.class);
  System.out.println("开始执行添加商品操作...");
  goodsService.add();
  System.out.println("添加商品操作执行完毕...");
}
````

运行结果：

````reStructuredText
开始执行添加商品操作...
权限不足
添加商品操作执行完毕...
````

上面这段代码中,因为用户的角色为user，无权完成操作。

修改测试程序，再添加一个admin的用户角色

````java
roles.add("user");
roles.add("admin"); //再添admin角色
````

再次执行测试程序：

````reStructuredText
开始执行添加商品操作...
添加商品...
添加商品操作执行完毕...
````



