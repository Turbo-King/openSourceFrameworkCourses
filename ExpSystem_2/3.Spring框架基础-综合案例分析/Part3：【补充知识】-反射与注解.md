# 【知识补充】反射与注解

> 学习要点和训练目标
>
> ✔了解什么是反射以及反射的作用
>
> ✔掌握反射常用类的使用
>
> ✔了解注解的概念及架构组成
>
> ✔掌握注解的定义及使用
>
> ✔训练目标：运用注解解决实际问题

# 1 反射

## 1.1 什么是反射？

Java 反射（Reflect）说的是在运行状态中，对于任何一个类，我们都能够知道这个类有哪些方法和属性。对于任何一个对象，我们都能够对它的方法和属性进行调用。我们把这种动态获取对象信息和调用对象方法的功能称之为反射机制。本节主要讲解 Java 反射机制中的常用类。

反射是 Java 编程语言的一个特性，它提供了在运行时检查和动态调用类、方法、属性的能力。

## 1.2 反射能做什么

反射可以在运行时检查类，接口，方法和变量。还可以实例化对象、调用方法、获取和设置变量值。比如当我们不知道一个类中是否拥有某个方法时，我们就可以使用反射来检查是否拥有这个方法。

## 1.3 反射常用类

### （1）常用类

- Class：Class 类表示正在运行的 Java 程序中的类和接口。
- Field：提供有关类或接口的单个域的信息和动态访问。如数据类型，访问修饰符，域的名称和值。
- Method：提供有关类或接口上的单个方法的信息和访问权限。如访问修饰符，返回类型，名称，参数类型和方法的异常类型。
- Constructor：提供有关类的单个构造函数的信息和访问权限。例如构造函数的访问修饰符，名称和参数类型。
- Modifier：提供了有关访问修饰符的信息。

### （2）Class

Class 类没有公共构造方法，可以通过以下方法获取 Class 实例。

- Object 提供的 `getClass()` 方法。
- `类名.Class`。
- `Class.forName(String className)` 方法，`className` 为类的全限定名。

Class 类常用方法：

| 方法                                                         | 描述                                              |
| ------------------------------------------------------------ | ------------------------------------------------- |
| String getName()                                             | 获取全限定名                                      |
| newInstance()                                                | 通过Class创建实例，相当于调用无参数构造器创建实例 |
| Field getField(String name)                                  | 获取指定的域对象                                  |
| Field[] getFields()                                          | 返回所有的公有域对象数组                          |
| Field[] getDeclaredFields()                                  | 获得所有域对象                                    |
| Method getDeclaredMethod(String name, Class<?>... parameterTypes) | 返回指定的方法对象                                |
| Method[] getMethods()                                        | 返回所有的公有方法对象数组                        |
| Method[] getDeclaredMethods()                                | 返回所有方法对象数组                              |
| Annotation[]  getDeclaredAnnotations()                       | 返回当前类中所有注解                              |



参考文档：[Class类API](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)

### （3）Field

Field没有构造器，只能通过Class获取。

重点方法：

​		class.getDeclaredFields()：获得所有字段，返回字段数组。

​		class.getDeclaredField("字段名")：根据字段名获得字段对象。

​		field.getName()：获得字段名。

​		field.getType()：获得字段类型。

​		field.set(Object obj, Object value)：给字段赋值，value即字段要赋的值，obj即字段的对象。

​		field.get(Object obj)：从字段上取值。obj表示字段的对象。

​		field.isAccessible()：判断字段的访问权限，可访问返回true，不可访问返回false。

​		field.setAccessible(true)：修改字段的访问权限，true表示可访问。

​       Annotation[]  getDeclaredAnnotations()    判断字段的访问权限

参考文档：[Field类API](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html)

### （4）Method

Method没有构造器，只能通过Class获取。

重点方法：

​		class.getDeclaredMethods()：获取所有方法。

​		class.getDeclaredMethod(String name, Class<?>... parameterTypes)：

​		根据方法名获取方法，name是方法名，parameterTypes即方法所需要的参数。

​        method.invoke(Object obj,  Object... args)：

​		调用方法，obj表示调用方法的对象，args表示调用该方法需要传递的参数。

​		method.getName()：获得方法名。

​        Annotation[] method.getAnnotations():获得注解

### （5）Constructor

① 获取Constructor方法：

clazz.getConstructors()：所有"**公有的**"构造方法

clazz.getDeclaredConstructors()：

获取**所有的**构造方法(包括私有、受保护、默认、公有)

clazz.getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：

clazz.getDeclaredConstructor(Class... parameterTypes):

获取"某个构造方法"可以是私有的，或受保护、默认、公有；

② 调用构造方法：

​		constructor.getName() ：以字符串形式返回此构造函数的名称。

​		constructor.getParameterCount()：构造器中参数个数

​		constructor.newInstance(Object... initargs)：

使用构造器创建对象，initargs为可变参，传入构造器的参数

## 1.4 案例分析

新建一个Java项目ReflectDemo，在src/main/java下创建一个类ReflectDemo

```java
package com.xzit.spring;

public class ReflectDemo {
    //成员变量
    private int a;
    public int b;
    //构造方法
    public ReflectDemo(){a = 10;b=20;}
    public ReflectDemo(int a,int b){
        this.a = a;
        this.b = b;
    }
    //成员方法
    private void method1(){
        System.out.println("a=" + a + ",b=" + b);
    }

    public  void method2(){
        System.out.println("a+b=" + (a+b));
    }
}
```

新建测试程序：

```java
@Test
public void reflect1() throws Exception{
        //根据类全限名字符串获取Class对象
        Class clazz = Class.forName("com.xzit.spring.ReflectDemo");
        System.out.println(clazz.getName());
        //创建实例
        ReflectDemo reflectDemo = (ReflectDemo) clazz.newInstance();
        reflectDemo.method2();
}
```

测试，返回Class对象所声明的字段

```java
@Test
public void reflect2() throws Exception{
    //根据类全限名字符串获取Class对象
    Class clazz = Class.forName("com.xzit.spring.ReflectDemo");
    //返回Class对象所声明的字段
    Field[] fields = clazz.getDeclaredFields();
    for(Field field:fields){
        //输出字段的名字
        System.out.println(field.getName()+"->" + field.getType());
    }

}
```

测试，返回Class对象所声明的方法

```java
@Test
public void reflect3() throws Exception{
    //根据类全限名字符串获取Class对象
    Class clazz = Class.forName("com.xzit.spring.ReflectDemo");        
    //返回Class对象所声明的字段
    Method[] methods = clazz.getDeclaredMethods();
    for(Method method:methods){
        //输出方法的名字
        System.out.println(method.getName());  
    } 
}
```

测试，返回Class对象所声明的构造方法

```java
@Test
public void reflect4() throws Exception{
    //根据类全限名字符串获取Class对象
    Class clazz = Class.forName("com.xzit.spring.ReflectDemo");
    //返回Class对象所声明的字段
    Constructor[] constructors = clazz.getDeclaredConstructors();
    for(Constructor constructor:constructors){
        //输出方法的名字
        System.out.println(constructor.getName());
        //构造方法的参数
        Type[] types = constructor.getGenericParameterTypes();
        if(types==null||types.length==0){
            System.out.println("无参构造方法");
        }else {
            System.out.print("带有" + types.length+"个参数的构造方法（");
            for (Type type : types) {
                System.out.print(type.getTypeName() + " ");
            }
            System.out.println(")");
        } 
    }
}
```

# 2 注解

## 2.1 注解概念及作用

Java 注解（Annotation）又称 Java 标注，是 JDK5.0 引入的一种注释机制。大部分框架(如Spring)都用注解简化代码并提高编码的效率。

Java 语言中的**类、方法、变量、参数和包**等都可以被标注。和 Javadoc 不同，Java 标注可以通过**反射**获取标注内容。在编译器生成类文件时，标注可以被嵌入到字节码中。Java 虚拟机可以保留标注内容，在运行时可以获取到标注内容 。 当然它也支持自定义 Java 标注。

Java 中内置的注解：

**作用在代码的注解**

- @Override - 检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。
- @Deprecated - 标记过时方法。如果使用该方法，会报编译警告。
- @SuppressWarnings - 指示编译器去忽略注解中声明的警告。

**作用在其他注解的注解(或者说 元注解)**

- @Retention - 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问。
- @Documented - 标记这些注解是否包含在用户文档中。javadoc-->html
- @Target - 标记这个注解应该是哪种 Java 成员。
- @Inherited - 标记这个注解是继承于哪个注解类(默认 注解并没有继承于任何子类)

## 2.2 Annotation 架构

![](3.Spring 综合案例\annotation-1.jpg)

从中，我们可以看出：

**1 个 Annotation 和 1 个 RetentionPolicy 关联。**

可以理解为：每1个Annotation对象，都会有唯一的RetentionPolicy属性。

**1 个 Annotation 和 1~n 个 ElementType 关联。**

可以理解为：对于每 1 个 Annotation 对象，可以有若干个 ElementType 属性。

**Annotation 有许多实现类，包括：Deprecated, Documented, Inherited, Override 等等。**

Annotation 的每一个实现类，都 "和 1 个 RetentionPolicy 关联" 并且 " 和 1~n 个 ElementType 关联"。

## 2.3 Annotation 组成部分

java Annotation 的组成中，有 3 个非常重要的主干类。它们分别是：

- Annotation.java
- ElementType.java
- RetentionPolicy.java

说明：

**Annotation 就是个接口。**

"每 1 个 Annotation" 都与 "1 个 RetentionPolicy" 关联，并且与 "1～n 个 ElementType" 关联。可以通俗的理解为：每 1 个 Annotation 对象，都会有唯一的 RetentionPolicy 属性；至于 ElementType 属性，则有 1~n 个。

**ElementType 是 Enum 枚举类型，它用来指定 Annotation 的类型。**

"每 1 个 Annotation" 都与 "1～n 个 ElementType" 关联。当 Annotation 与某个 ElementType 关联时，就意味着：Annotation有了某种用途。例如，若一个 Annotation 对象是 METHOD 类型，则该 Annotation 只能用来修饰方法。

**RetentionPolicy 是 Enum 枚举类型，它用来指定 Annotation 的策略。通俗点说，就是不同 RetentionPolicy 类型的 Annotation 的作用域不同。**

"每 1 个 Annotation" 都与 "1 个 RetentionPolicy" 关联。

- a) 若 Annotation 的类型为 SOURCE，则意味着：Annotation 仅存在于编译器处理期间，编译器处理完之后，该 Annotation 就没用了。 例如，" @Override" 标志就是一个 Annotation。当它修饰一个方法的时候，就意味着该方法覆盖父类的方法；并且在编译期间会进行语法检查！编译器处理完后，"@Override" 就没有任何作用了。
- b) 若 Annotation 的类型为 CLASS，则意味着：编译器将 Annotation 存储于类对应的 .class 文件中，它是 Annotation 的默认行为。
- c) 若 Annotation 的类型为 RUNTIME，则意味着：编译器将 Annotation 存储于 class 文件中，并且可由JVM读入。

这时，只需要记住"每 1 个 Annotation" 都与 "1 个 RetentionPolicy" 关联，并且与 "1～n 个 ElementType" 关联。

## 2.4 注解的定义

### (1)Annotation 通用定义

```java
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation1 {
    
}
```

说明：

上面的作用是定义一个 Annotation，它的名字是 MyAnnotation1。定义了 MyAnnotation1 之后，我们可以在代码中通过 "@MyAnnotation1" 来使用它。 其它的，@Documented, @Target, @Retention, @interface 都是来修饰 MyAnnotation1 的。下面分别说说它们的含义：

**@interface**

使用 @interface 定义注解时，意味着它实现了 java.lang.annotation.Annotation 接口，即该注解就是一个Annotation。定义 Annotation 时，@interface 是必须的。

注意：它和我们通常的 implemented 实现接口的方法不同。Annotation 接口的实现细节都由编译器完成。通过 @interface 定义注解后，该注解不能继承其他的注解或接口。

**@Documented**

类和方法的 Annotation 在缺省情况下是不出现在 javadoc 中的。如果使用 @Documented 修饰该 Annotation，则表示它可以出现在 javadoc 中。定义 Annotation 时，@Documented 可有可无；若没有定义，则 Annotation 不会出现在 javadoc 中。

**@Target(ElementType.TYPE)**

@Target(ElementType.TYPE) 的意思就是指定该 Annotation 的类型是 ElementType.TYPE。这就意味着，MyAnnotation1 是来修饰"类、接口（包括注释类型）或枚举声明"的注解。

定义 Annotation 时，@Target 可有可无。若有 @Target，则该 Annotation 只能用于它所指定的地方；若没有 @Target，则该 Annotation 可以用于任何地方。

 **@Retention(RetentionPolicy.RUNTIME)**

@Retention(RetentionPolicy.RUNTIME) 的意思就是指定该 Annotation 的策略是 RetentionPolicy.RUNTIME。这就意味着，编译器会将该 Annotation 信息保留在 .class 文件中，并且能被虚拟机读取。

定义 Annotation 时，@Retention 可有可无。若没有 @Retention，则默认是 RetentionPolicy.CLASS。

### (2)注解的属性

注解的属性也叫做成员变量。注解只有成员变量，没有方法。

注解的成员变量在注解的定义中以“**无形参的方法**”形式来声明，

其**方法名**定义了该成员变量的名字，其返回值定义了该成员变量的类型。

注解中属性可以有默认值，默认值需要用 default 关键值指定。

```java
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation1 {
    String value();
	int sex() default 1;
}
```

应用注解时，注解中有多个属性，赋值的方式：括号内以 value=""，多个属性之前用 ，隔开。

```java
@MyAnnotation1(value="3",sex=0)
public class Test {

}
```

有默认值且不想改值，无需在 @MyAnnotation1后的括号中为sex进行赋值了。如一个注解内仅只有一个成员变量且名字为 value 的属性时，应用这个注解时可直接写属性值到括号内。注解没有任何属性,括号都可以省略。

## 2.5 案例分析

### (1)新建注解ActionLog

该注解作用于方法上，该注解代码如下：

```java
package com.xzit.spring.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLog{
     /** 要执行的操作类型比如：add操作 **/
     public String operationType() default "";

     /** 要执行的具体操作比如：添加用户 **/
     public String operationName() default "";
}
```

### (2)新建UserService类

该类有对User执行的一些方法。

```java
package com.xzit.spring.services;

public class UserService {

    public void insert(String username){
        System.out.println("添加用户...");
    }

    public void update(String username){
        System.out.println("添加用户...");
    }

    public void delete(String username){
        System.out.println("添加用户...");
    }

    public String query(String username){
       System.out.println("添加用户...");
       return "用户信息";
    }
}
```

修改UserServices类，为每个方法增加ActionLog注解，代码如下：

````java
package com.xzit.spring.services;

import com.xzit.spring.annotation.ActionLog;

public class UserService {
    @ActionLog(operationType = "insert操作",operationName = "添加用户")
    public void insert(String username){
        System.out.println("添加用户...");
    }

    @ActionLog(operationType = "update操作",operationName = "更新用户")
    public void update(String username){
        System.out.println("更新用户...");
    }

    @ActionLog(operationType = "delete操作",operationName = "删除用户")
    public void delete(String username){
        System.out.println("删除用户...");
    }

    @ActionLog(operationType = "query操作",operationName = "查询用户")
    public String query(String username){
        System.out.println("查询用户...");
        return "用户信息";
    }
}
````

### (3)新建测试类ActionLogTest

```java
package com.xzit.spring;

import com.xzit.spring.annotation.ActionLog;
import org.junit.Test; 
import java.lang.reflect.Method;

public class ActionLogTest {
    @Test
    public void actionLogAnno() throws Exception {
        Class clazz = Class.forName("com.xzit.spring.services.UserService");
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(ActionLog.class)){
                ActionLog annotation = method.getAnnotation(ActionLog.class);
                String operationType = annotation.operationType();
                String operationName = annotation.operationName();
                System.out.println(operationType + "," + operationName);
            }
        }
    }
}

```

运行结果：

```re
update操作,更新用户
delete操作,删除用户
insert操作,添加用户
query操作,查询用户
```

