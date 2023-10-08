# `Part1` ：`Spring IoC` 初识

## 1 内容回顾

```java
public class MajorInfoServiceImpl implements MajorInfoService {

    //调用数据访问层的对象完成相关的数据操作
    //思考1：紧耦合 --->处理  (如Spring)
    private MajorinfoDao majorinfoDao = new MajorInfoDaoImpl();
}
```

````java

@WebServlet(urlPatterns = {"/majorServlet","/major"},loadOnStartup = 1)
public class MajorInfoServlet extends HttpServlet {

    //service层的组件
    private MajorInfoService majorInfoService = new MajorInfoServiceImpl();
}
````

### 1.1 `OCP`开闭原则

回顾之前的项目，其代码违背了开闭原则`OCP`。开闭原则是这样说的：**在软件开发过程中应当对扩展开放，对修改关闭。**也就是说，如果在进行功能扩展的时候，添加额外的类是没问题的，但因为功能扩展而修改之前运行正常的程序，这是忌讳的，不被允许的。因为一旦修改之前运行正常的程序，就会导致项目整体要进行全方位的重新测试。这是相当麻烦的过程。**导致以上问题的主要原因是：**代码和代码之间的耦合度太高。**上层**是依赖**下层**的。`MajorInfoServlet`依赖`MajorInfoServiceImpl`，而`MajorInfoServiceImpl`依赖`MajorInfoDaoImpl`，这样就会导致**下面只要改动**，**上面必然会受牵连（跟着也会改）**，所谓牵一发而动全身。这样也就同时违背了另一个开发原则：**依赖倒置原则**。

### 1.2 依赖倒置原则DIP

依赖倒置原则(`Dependence Inversion Principle`)，简称`DIP`，主要倡导面向抽象编程，面向接口编程，不要面向具体编程，让**上层**不再依赖**下层**，下面改动了，上面的代码不会受到牵连。这样可以大大降低程序的耦合度，耦合度低了，扩展力就强了，同时代码复用性也会增强。（**软件七大开发原则都是在为解耦合服务**）

上面的代码貌似已经面向接口编程：

```java
public class MajorInfoServiceImpl implements MajorInfoService {

    //调用数据访问层的对象完成相关的数据操作
    //思考1：紧耦合 --->处理  (如Spring)
    private MajorinfoDao majorinfoDao = new MajorInfoDaoImpl();
}
```

确实已经面向接口编程了，但对象的创建是：`new MajorInfoDaoImpl()`显然并没有完全面向接口编程，还是使用到了具体的接口实现类。什么叫做完全面向接口编程？什么叫做完全符合依赖倒置原则呢？请看以下代码：

```java
public class MajorInfoServiceImpl implements MajorInfoService {
    //调用数据访问层的对象完成相关的数据操作
    private MajorinfoDao majorinfoDao;
}
```

如果代码是这样编写的，才算是完全面向接口编程，才符合依赖倒置原则。

**问题是：**`majorinfoDao`是`null`，在执行的时候就会出现空指针异常。解决空指针异常的问题，其实就是解决两个核心的问题：

- 第一个问题：谁来负责对象的创建。【也就是说谁来：`new MajorInfoDaoImpl();`】
- 第二个问题：谁来负责把创建的对象赋到这个属性上。【也就是说谁来把上面创建的对象赋给`majorinfoDao`属性】

如果把以上两个问题解决了，就可以做到既符合`OCP开闭原则`，又符合`依赖倒置原则`。

**Spring框架可以做到**:在Spring框架中，它可以帮助程序员`new`对象，并且它还可以将`new`出来的对象赋到属性上。换句话说，Spring框架可以帮助程序员创建对象，并且可以帮助程序员维护对象和对象之间的关系。

很显然，这种方式是将对象的创建权/管理权交出去了，不再使用硬编码的方式了。同时也把对象关系的管理权交出去了，也不再使用硬编码的方式了。像这种把对象的创建权交出去，把对象关系的管理权交出去，被称为控制反转。

### 1.3 控制反转`IoC`

控制反转（`Inversion of Control`，缩写为`IoC`），是面向对象编程中的一种设计思想，可以用来降低代码之间的耦合度，符合依赖倒置原则。

控制反转的核心是：**将对象的创建权交出去，将对象和对象之间关系的管理权交出去，由第三方容器来负责创建与维护**。

控制反转常见的实现方式：依赖注入（`Dependency Injection`，简称`DI`）

通常，依赖注入的实现由包括两种方式：

- set方法注入

- 构造方法注入

而`Spring框架`就是一个实现了`IoC`思想的框架。

`IoC`可以认为是一种**全新的设计模式**，但是理论和时间成熟相对较晚，并没有包含在`GoF`中。（`GoF`指的是23种设计模式）

## 2 Spring概述

### 2.1 Spring简介

Spring 是一个企业级开源框架，为解决企业级项目开发过于复杂而创建的，框架的主要优势之一就是分层架构，允许开发者自主选择组件。

百度百科:

> Spring是一个开源框架，它由Rod Johnson创建。它是为了解决企业应用开发的复杂性而创建的。
>
> 从简单性、可测试性和松耦合的角度而言，任何Java应用都可以从Spring中受益。
>
> **Spring是一个轻量级的控制反转(`IoC`)和面向切面(`AOP`)的容器框架。**
>
> **Spring最初的出现是为了解决`EJB`臃肿的设计，以及难以测试等问题。**
>
> **Spring为简化开发而生，让程序员只需关注核心业务的实现，尽可能的不再关注非业务逻辑代码（事务控制，安全日志等）。**

### 2.2 Spring 8大模块

注意：`Spring5`版本之后是8个模块。在`Spring5`中新增了`WebFlux`模块。

![img](1.Spring IoC初识\spring5体系结构.png)

1.`Spring Core`模块

这是Spring框架最基础的部分，它提供了依赖注入（`DependencyInjection`）特征来实现容器对Bean的管理。核心容器的主要组件是 `BeanFactory`，`BeanFactory`是工厂模式的一个实现，是任何Spring应用的核心。它使用`IoC`将应用配置和依赖从实际的应用代码中分离出来。

2.`Spring Context`模块

如果说核心模块中的`BeanFactory`使`Spring`成为容器的话，那么上下文模块就是`Spring`成为框架的原因。

这个模块扩展了`BeanFactory`，增加了对国际化（`I18N`）消息、事件传播、验证的支持。另外提供了许多企业服务，例如电子邮件、`JNDI`访问、`EJB`集成、远程以及时序调度（`scheduling`）服务。也包括了对模版框架例如`Velocity`和`FreeMarker`集成的支持。

3.`Spring AOP`模块

Spring在它的`AOP`模块中提供了对面向切面编程的丰富支持，Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用` Spring AOP`，不用依赖组件，就可以将声明性事务管理集成到应用程序中，可以自定义拦截器、切点、日志等操作。

4.`Spring DAO`模块

提供了一个JDBC的抽象层和异常层次结构，消除了烦琐的JDBC编码和数据库厂商特有的错误代码解析，用于简化JDBC。

5.`Spring ORM`模块

Spring提供了ORM模块。Spring并不试图实现它自己的ORM解决方案，而是为几种流行的ORM框架提供了集成方案，包括Hibernate、JDO和iBATIS SQL映射，这些都遵从 Spring 的通用事务和 DAO 异常层次结构。

6.`Spring Web MVC`模块

Spring为构建Web应用提供了一个功能全面的MVC框架。虽然Spring可以很容易地与其它MVC框架集成，例如Struts，但Spring的MVC框架使用IoC对控制逻辑和业务对象提供了完全的分离。

7.`Spring WebFlux`模块

Spring Framework 中包含的原始 Web 框架 Spring Web MVC 是专门为 Servlet API 和 Servlet 容器构建的。反应式堆栈 Web 框架 Spring WebFlux 是在 5.0 版的后期添加的。它是完全非阻塞的，支持反应式流(Reactive Stream)背压，并在Netty，Undertow和Servlet 3.1+容器等服务器上运行。

8.`Spring Web`模块

Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文，提供了Spring和其它Web框架的集成，比如Struts、WebWork。还提供了一些面向服务支持，例如：实现文件上传的multipart请求。

### 2.3 Spring特点

1.轻量

- 1）从大小与开销两方面而言Spring都是轻量的。完整的Spring框架可以在一个大小只有1MB多的JAR文件里发布。并且Spring所需的处理开销也是微不足道的。

- 2）Spring是非侵入式的：Spring应用中的对象不依赖于Spring的特定类。

2.控制反转

1. 1）Spring通过一种称作控制反转（IoC）的技术促进了松耦合。当应用了IoC，一个对象依赖的其它对象会通过被动的方式传递进来，而不是这个对象自己创建或者查找依赖对象。你可以认为IoC与JNDI相反——不是对象从容器中查找依赖，而是容器在对象初始化时不等对象请求就主动将依赖传递给它。

3.面向切面

1. 1）Spring提供了面向切面编程的丰富支持，允许通过分离应用的业务逻辑与系统级服务（例如审计（auditing）和事务（transaction）管理）进行内聚性的开发。应用对象只实现它们应该做的——完成业务逻辑——仅此而已。它们并不负责（甚至是意识）其它的系统级关注点，例如日志或事务支持。

4.容器

1. 1）Spring包含并管理应用对象的配置和生命周期，在这个意义上它是一种容器，可以配置每个bean如何被创建——基于一个可配置原型（prototype），bean可以创建一个单独的实例或者每次需要时都生成一个新的实例——以及它们是如何相互关联的。然而，Spring不应该被混同于传统的重量级的EJB容器，它们经常是庞大与笨重的，难以使用。

5.框架

1. 1）Spring可以将简单的组件配置、组合成为复杂的应用。在Spring中，应用对象被声明式地组合，典型地是在一个XML文件里。Spring也提供了很多基础功能（事务管理、持久化框架集成等等），将应用逻辑的开发留给了你。

所有Spring的这些特征使得程序员能够编写更干净、更可管理、并且更易于测试的代码。它们也为Spring中的各种模块提供了基础支持。

## 3  `IoC`和`DI`概述

**`IoC`**是 `Spring` 框架的灵魂，非常重要，理解了` IoC `才能掌握 `Spring` 框架如何使用。

**`IoC `也叫控制反转**，首先从字面意思理解，什么叫控制反转？反转的是什么？

**控制反转（`IOC `，Inversion of Control） ，是一个概念，是一种思想。指将传统上由程序代码直接操控的对象调用权交给容器 ，通过容器来实现对象的装配和管理。控制反转就是对对象控制权的转移，从程序代码本身反转到了外部容器。 **但是，需要注意，`IoC`  也是有局限性的，其不能使用在分布式系统中。 即其所依赖的反转到的外部容器，必须要与控制权出让方同处于一个`JVM`中。

`IoC`是一个概念，是一种思想，其实现方式多种多样。当前比较流行的实现方式有两种：依赖注入和依赖查找。依赖注入方式应用更为广泛。

**依赖查找**：`Dependency Lookup ，DL` ，容器提供回调接口和上下文环境给组件，程序代码则需要提供具体的查找方式。比较典型的是依赖于`JNDI`系统的查找。

**依赖注入：Dependency Injection ，DI ，程序代码不做定位查询，这些工作由容器自行完成。依赖注入 DI  是指程序运行过程中，若需要调用另一个对象协助时，无须在代码中创建被调用者，而是依赖于外部容器，由外部容器创建后传递给程序。**Spring的依赖注入对调用者与被调用者几乎没有任何要求，完全支持` POJO`之间依赖关系的管理。

依赖注入是目前最优秀的解耦方式 。依赖注入让 Spring 的 的 Bean  之间以配置文件的方式组织在一起，而不是以硬编码的方式耦合在一起的。

**spring官网**：https://spring.io/projects/spring-framework



## 4 程序开发举例

在传统的程序开发中，当需要调用对象时，通常由调用者来创建被调用者的实例，即对象是由调用者主动 new 出来的。但在 Spring 框架中创建对象的工作不再由调用者来完成，而是**交给 IoC 容器来创建，再推送给调用者**，整个流程完成反转，因此是控制反转。

**举例：**创建Department对象

### 4.1 传统方式

（1）创建 Department类：

```java
public class Department {
    private int id;
    private String name;
  
    //无参构造方法
    public Department() { }
    //两个参数的构造方法
    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }
    //相应setter/getter方法
}
```

（2）在测试方法中调用构造函数创建对象：

````java
Department department1 = new Department(1,"信息工程学院");
//department1.setId(1);
//department1.setName("信电工程学院");
//通过构造方法给成员变量赋初值
Department department2 = new Department(2,"机电工程学院");
````

### 4.2  Spring 程序开发

**环境搭建：**

- 在IDEA下创建基于Maven的Java项目
- maven 添加 Spring jar 依赖 

​    如何找到依赖关系：

​    网址：https://search.maven.org/，输入spring --->org.springframework (group ID) -->spring context

   ```xml
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.3.30</version>
</dependency>
   ```

**程序思路：**

- 创建配置文件`applicationContext.xml`,也可以是其它自定义配置文件，如`spring-beans.xml`
- 调用 `API`获取Spring容器创建的bean对象

**程序代码：**

（1）编写spring-beans 配置文件（基于`xml`，见spring官网文档介绍）

````xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!--配置Department对象：通过设值注入-->
    <bean id="department1" class="com.xzit.springboot.Department">
         <property name="id" value="1"/>
         <property name="name" value="信息工程学院"/>
    </bean>
</beans>
````

​       bean标签的含义：

- id：对象名称
- class：对象所属类的全限定名
- property：属性   
  - name：属性名称
  - value：给属性赋的值

​         通过property给属性赋值，这属于**设置注入**：Spring 通过调用每个属性的 setter 方法来完成属性的赋值，因此实体类必须有 setter 方法，否则加载时报错，getter 方法可省略。

（2）编写测试程序，调用`API`获取对象

````java
    @Test
    public void getDepartment(){
        //1.加载 spring.xml 配置文件
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-beans.xml");
        //2.通过 id 值获取对象
        Department department1 = (Department) applicationContext.getBean("department1");
        System.out.println(department1);
    }
````

测试程序执行过程：

​	1）程序加载 `spring-beans.xml `配置文件，生成 `ApplicationContext` 对象。程序在加载 `spring-beans.xml `时已创建 `department`对象，其内部是通过反射机制调用无参构造函数，所有要求交给 `IoC `容器管理的类必须有无参构造函数。当然可以有带参数的构造方法。

​	2）调用 `applicationContext `的 `getBean `方法获取对象，参数为配置文件中的id 值。

知识点分析：

​	**1）`ApplicationContext`接口及实现类**

​	`ApplicationContext`用于加载 Spring的配置文件，在程序中充当“**容器**”的角色。其实现类有两个。在`IntelliJ IDEA`下通过 `Ctrl +H`查看。

​	如Spring配置文件存放在项目的类路径下，则使用 **`ClassPathXmlApplicationContext`**实现类进行加载。如Spring  配置文件存放在本地磁盘目录中，则使用 **`FileSystemXmlApplicationContext`**  实现类进行加载。如Spring配置文件存放在项目的根路径下 ，同样使用 **`FileSystemXmlApplicationContext`**实现类进行加载。即该配置文件与`src`目录同级，而非在` src`中 。

​	**2）`ApplicationContext`容器中对象的装配时机**

 	`ApplicationContext`容器，会在容器对象初始化时，将其中的所有对象一次性全部装配好。以后代码中若要使用到这些对象，只需从内存中直接获取即可。执行效率较高。但占用内存。

## 5 Bean装配与注入

Bean的装配，即 Bean对象的创建。容器根据代码要求创建 Bean对象后再传递给代码的过程，称为 Bean的装配。Spring启动时读取应用程序提供的Bean配置信息，并在Spring容器中生成一份相应的Bean配置注册表，然后根据这张注册表实例化Bean，装配好Bean之间的依赖关系，为上层应用提供准备就绪的运行环境。

Bean配置信息是Bean的元数据信息，它由以下4个方面组成：

（1）Bean的实现类

（2）Bean的属性信息，如用户名、密码等

（3）Bean的依赖关系，Spring根据依赖关系配置完成Bean之间的装配

（4）Bean的行为配置，如生命周期范围及生命周期各过程的回调函数等。

Bean元数据信息在Spring容器中的内部对应物是由一个个`BeanDefinition`形成的Bean注册表，Spring实现了Bean元数据信息内部表示和外部定义的解耦。Spring支持多种形式的Bean配置支持。Spring 1.0仅支持XML配置，**Spring 2.0 新增基于注解配置**，Spring 3.0新增基于Java类配置，Spring 4.0新增基于Groovy动态语言配置支持。

下图描述了Spring容器、Bean配置信息、Bean实现类以及应用程序四者的相应关系。

![](1.Spring IoC初识\bean装配.png)

### 5.1 **依赖注入方式**

Bean 实例在调用无参构造器创建了空值对象后，就要对 Bean对象的属性进行初始化。初始化是由容器自动完成的， 称为注入。根据注入方式的不同， 常用的有两类：**设值注入** 、**构造注入** 。还有另外一种， 实现特定接口注入, 由于这种方式采用侵入式编程，污染了代码，所以几乎不用。

**（1）设值注入**

设值注入是指过 ，通过 setter  方法传入被调用者的实例。这种注入方式简单、直观，因而在 Spring 的依赖注入中大量使用。前面的案例中都是基于这种方式，在此不再举例说明。

当指定 bean的某属性值为另一bean的实例时，通过ref指定它们间的引用关系。ref的值必须为某 bean的id。

**（2）构造注入**

构造注入是指，在构造调用者实例的同时，完成被调用者的实例化,即使用构造器设置依赖关系。

​	 1）修改spring-beans.xml文件

````xml
    <!--配置Department对象：通过构造注入-->
    <bean id="department2" class="com.xzit.springboot.Department">
        <constructor-arg index="0" value="2" type="int"/>
        <constructor-arg index="1" value="机电工程学院" type="java.lang.String"/>
    </bean>
````

​          IoC 容器会根据 constructor-arg 标签去加载对应的有参构造函数，创建对象并完成属性赋值。

 constructor-arg 标签中参数说明：

- name 构造方法中的参数名称，其值需要与有参构造的形参名对应，有index可以不用name属性
- value 是对应的值。
- index 指明该参数对应着构造器的第几个参数，从0开始。不过，该属性不要也行，但要注意，若参数类型相同，或之间有包含关系，则需要保证赋值顺序要与构造器中的参数顺序一致。比如上面的配置，因为参数顺序与构造方法顺序不一致，因此必须有index。
- type属性用于指定其类型。基本类型直接写类型关键字即可，非基本类型需要写全限定性类名。

````xml
    <!--配置Department对象：通过构造注入-->
    <bean id="department2" class="com.xzit.springboot.Department">
        <constructor-arg name="id" value="2"/>
        <constructor-arg name="name" value="机电工程学院"/>
    </bean>
````

​	2）编写测试程序，调用API获取对象

````java
   Department department2 = (Department) applicationContext.getBean("department2");
   System.out.println(department2);
````

​	3）运行结果

![](1.Spring IoC初识\运行结果1.png)

（3）**集合属性注入**

````java
public class CollectionPropertyDI {
    private String[] strs;
    private List<Department> departmentList;
    private Set<String> stringSet;
    private Map<String,String> map;
    private Properties p;
    //省略setter/getter方法
}
````

​	为集合属性注入值

````xml
<bean id="collectionPropertyDI" class="com.xzit.springboot.CollectionPropertyDI">
        <!--为数组注入值-->
        <property name="strs">
            <list>
                <value>徐州工程学院</value>
                <value>信电工程学院</value>
                <value>计算机科学与技术</value>
            </list>
        </property>
        <!--为List注入值-->
        <property name="departmentList">
            <list>
                <ref bean="department1"/>
                <ref bean="department2"/>
            </list>
        </property>
        <!--为Set注入值-->
        <property name="stringSet">
            <set>
                <value>徐州工程学院</value>
                <value>信电工程学院</value>
                <value>计算机科学与技术</value>
            </set>
        </property>
        <!--为Map注入值-->
        <property name="map">
            <map>
                <entry key="myname" value="胡局新"/>
                <entry key="mydepartment" value="信电工程学院"/>
            </map>
        </property>
        <!--为Properties注入值-->
        <property name="p">
            <props>
                <prop key="driverClassName">com.mysql.jdbc.Driver</prop>
                <prop key="url">jdbc:mysql://localhost/xzit_xdxy</prop>
                <prop key="username">root</prop>
            </props>
        </property>
    </bean>
````

**设值注入和构造注入方式的对比**

![](1.Spring IoC初识\注入方式对比.png)

（4） p 命名空间

使用前需要在spring配置文件中引入p命名空间

````xml
xmlns:p="http://www.springframework.org/schema/p"
````

````xml
<bean id="teacher2" class="com.xzit.springboot.Teacher" 
          p:id="3" p:name="胡局新" p:twno="xzit1007" p:department-ref="department"/>
````

**p标签注入规则：**

​	对于直接量（基本数据类型、字符串）属性： p:属性名=“属性值”

​	对于引用Bean的属性： p:属性名-ref=“Bean的id”

### 5.2 对于域属性的自动注入（理解）

举例说明：教师（Teacher）属于哪个部门（Department），其对象模型如下：

````java
public class Teacher {
    private int id;
    private String twno;
    private String name;
    private Department department;
    //省略setter/getter方法  has a
}
````

按5.1中设值注入，如果指定department的值，其配置文件如下：

```xml
<!--配置Department对象：通过构造注入-->
<bean id="department" class="com.xzit.springboot.Department">
  <constructor-arg index="0" value="2" type="int"/>
  <constructor-arg index="1" value="信电工程学院" type="java.lang.String"/>
</bean>  
<!--配置Teacher对象：通过设值注入-->
<bean id="teacher1" class="com.xzit.springboot.Teacher">
  <property name="department" ref="department"/>
  <!--ref:表示引用另一个对象-->
</bean>
```

对于域属性的注入，也可不在配置文件中显示的注入。可以通过为<bean/> 标签设置`autowire`属性值，为域属性进行隐式自动注入。根据自动注入判断标准的不同，可分为两种：

- `byName` ：根据名称自动注入


- `byType`  ：根据类型自动注入

#### （1）`byName`方式自动注入

当配置文件中被调用者 Bean的id值与代码中调用者Bean类的属性名相同时，可使用`byName`方式，让容器自动将被调用者Bean注入给调用者Bean 。容器是通过调用者的Bean类的属性名与配置文件的被调用者bean的id进行比较而实现自动注入的。

````xml
<!--配置Teacher对象：通过设值注入-->
<bean id="teacher1" class="com.xzit.springboot.Teacher" autowire="byName"/>
````

将自动将bean id="department"的引用注入给Teacher中的department属性

![](1.Spring IoC初识\autowire_byName.png)

#### （2）`byType`方式自动注入

使用 `byType`方式自动注入 ，要求：配置文件中被调用者 bean 的 class属性指定的类，要与代码中调用者 Bean类的某域属性类型同源。即要么相同，要么有 is-a关系（子类，或用是实现类）。**但这样的同源的被调用 bean只能有一个。多于一个，容器就不知该匹配哪一个了，运行时就会报错**。

#### （3）使用`SPEL`注入。Spring 4.0新增的特性。

`SPEL，Spring Expression Language` ，即Spring EL  表达式语言。 即在Spring配置文件中为Bean的属性注入值时，可直接使用`SPEL`表达式计算的结果。`SPEL`表达式以# 开头，后跟一对大括号。用法：<bean id="abc" value="#{…}"/> 。其文档中有其用法举例。在 Spring框架解压目录`\docs\spring-framework-reference\htmlsingle\index.html`中。`Ctrl+F`对 `SpEL`进行检索。第一个检索结果中 9.4.1  所链接的位置即有用法举例。

## 6 Bean的生命周期（理解）

#### 6.1 `SpringBean` 生命周期概述

Spring的生命周期是指**实例化Bean时所经历的一系列阶段**，即通过`getBean()`获取bean对象及设置对象属性时，Spring框架做了哪些事。Bean的生命周期从Spring容器实例化Bean到销毁Bean。

**当使用注解或者bean标签，将对象的创建工作交给spring处理以后，该对象的生命周期就由spring容器来管理。**

#### 6.2 `SpringBean` 生命周期过程

![](1.Spring IoC初识\SpringBean 生命周期过程.png)

| ![](1.Spring IoC初识\beanfactory-lc.png)   |      |
| ---------------------------------------- | ---- |
| ![](1.Spring IoC初识\applicationcontext-lc.png) |      |

（1）实例化 Bean 对象

- 调用实例化 Bean 对象之前的 InstantiationAware BeanPostProcessorAdapter 接口的 postProcessBeforeInstantiation 方法

- Bean 的创建，由 BeanFactory 读取 Bean 定义文件，并生成各个实例
- 调用实例化Bean对象之后的 InstantiationAwareBeanPostProcessorAdapter 接口的 postProcessAfterInstantiation 方法

（2）设置 Bean 对象属性

- 执行在 Bean 设置属性时的 InstantiationAwareBeanPostProcessorAdapter 接口的 postProcessPropertyValues 方法，设置pvs值

- Setter 注入，执行 Bean 的属性依赖注入

（3）将容器和 bean 本身的信息暴露出来便于使用过程

- 实现 BeanNameAware 接口，重写并执行 setBeanName()。

- 实现 BeanFactoryAware接口，重写并执行 setBeanFactory()。

（4）初始化 Bean 对象过程

- 调用初始化 Bean 对象之前的InstantiationAwareBeanPostProcessorAdapter 接口的 postProcessBeforeInitialization 方法

- 继承 InitializingBean 类，重写 afterPropertiesSet()，完成初始化。
- 在 xml 文件中的 Bean 标签中使用 init-method 可以设置自定义初始化方法。
- 调用初始化 Bean 对象之后的 InstantiationAwareBeanPostProcessorAdapter 接口的 postProcessAfterInitialization 方法。

（5）销毁 Bean 对象过程

- 实现 DisposableBean 接口，重写 destroy()，在容器关闭时，如果 Bean 类实现了该接口，则执行它的 destroy() 方法


- 在 xml 文件中的 Bean 标签中定义 destroy-method，在容器关闭时，可以在 xml 文件中的 Bean 标签中使用“destory-method”设置自定义的对象销毁方法的方法

#### 6.3 `SpringBean` 生命周期举例说明

（1）Student 实体类
该类分别实现 **`BeanNameAware`** 接口、**`BeanFactoryAware`** 接口、**`InitializingBean`** 接口、**`DisposableBean`** 接口

````java
package com.xzit.springboot;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class Student implements BeanNameAware,BeanFactoryAware,InitializingBean,DisposableBean {
    private String name;
    private int age;

    //无参构造
    public Student() {
        System.out.println("- 执行了无参构造");
    }
    //有参构造
    public Student(String name, int age) {
        System.out.println("- 执行了有参构造name=" + name + " age=" + age);
        this.name = name;
        this.age = age;
    }
    //get、set方法
    public String getName() {
        System.out.println("- 执行了getName()方法");
        return name;
    }
    public void setName(String name) {
        System.out.println("- 执行了setName()方法,调用了setter方法通过有参构造完成了属性注入，name=" 
                           + name);
        this.name = name;
    }
    public int getAge() {
        System.out.println("- 执行了getAge()方法");
        return age;
    }
    public void setAge(int age) {
        System.out.println("- 执行了setAge()方法,调用了setter方法通过有参构造完成了属性注入，age=" + 
                           age);
        this.age = age;
    }
    //已定义show()方法
    public void show() {
        System.out.println("- 执行了自定义的show()方法，name=" + name);
    }
    //自定义初始化对象方法init()
    public void init() {
        System.out.println("- 执行了自定义的初始化对象方法init()");
    }
    //自定义销毁对象方法des()
    public void des() {
        System.out.println("- 执行了自定义的销毁对象方法des()");
    }
    //实现BeanNameAware接口，重写setBeanName()方法
    @Override
    public void setBeanName(String beanName) {
        System.out.println("- 执行了BeanNameAware接口的setBeanName()方法，将bean的id暴露出来，当前对象在容器中的beanid=" + beanName);
    }
    //实现BeanFactoryAware接口，重写setBeanFactory()方法
    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        System.out.println("- 执行了BeanFactoryAware接口的setBeanFactory()方法，将工厂对象暴露出来，通过该工厂可以获取容器中的对象");
        System.out.print("this==factory.getBean(“student”)的值=");
        System.out.println(this==factory.getBean("student"));
    }
    //实现InitializingBean接口，重写afterPropertiesSet()方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("- 执行了InitializingBean接口的afterPropertiesSet()方法，初始化完成，完成了属性的注入，name = " + name);
    }
    //实现DisposableBean接口，重写destroy()方法
    @Override
    public void destroy() throws Exception {
        System.out.println("- 执行了DisposableBean接口的destroy()方法，容器被关闭，对象即将被销毁，执行Spring自带的销毁方法");
    }
}
````

（2）继承InstantiationAwareBeanPostProcessorAdapter类的MyBeanProcessor类

````java
package com.xzit.springboot;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import java.beans.PropertyDescriptor;

public class MyBeanProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    //实例化之前
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("- 执行了实例化Bean对象之前的InstantiationAwareBeanPostProcessorAdapter接口的postProcessBeforeInstantiation方法");
        return null;
    }
    //实例化之后
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("- 执行了实例化Bean对象之后的InstantiationAwareBeanPostProcessorAdapter接口的postProcessAfterInstantiation方法");
        return true;
    }
    //Bean设置属性
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        System.out.println("- 执行了在Bean设置属性时的InstantiationAwareBeanPostProcessorAdapter接口的postProcessPropertyValues方法，设置pvs值=" + pvs);
        return pvs;
    }
    //初始化之前
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Student student = (Student) bean;
        System.out.println("- 执行了初始化Bean对象之前的InstantiationAwareBeanPostProcessorAdapter接口的postProcessBeforeInitialization方法");
        student.setName("李四");
        return bean;
    }
    //初始化之后
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Student student = (Student) bean;
        System.out.println("- 执行了初始化Bean对象之后的InstantiationAwareBeanPostProcessorAdapter接口的postProcessAfterInitialization方法");
        student.setAge(10);
        return bean;
    }
}
````

（3）bean配置（基于xml ）

````xml
<bean id="student" class="com.xzit.springboot.Student" init-method="init" destroy-method="des">
  <property name="name" value="李传亮"></property>
  <property name="age" value="24"></property>
</bean>
<bean class="com.xzit.springboot.MyBeanProcessor"></bean>
````

（4）测试 类

````java
@Test
public void beanLycyle(){
  //1.加载 spring.xml 配置文件
  ApplicationContext applicationContext = 
       new ClassPathXmlApplicationContext("spring-beans2.xml");
  //2.通过 id 值获取对象
  Student student = (Student)applicationContext.getBean("student");
  student.show();
}
````

（5）运行结果

![](1.Spring IoC初识\bean生命周期举例执行结果.png)

## 7 Spring 注解(掌握)

上面我们讲到XML 或注解配置方式，都是表达 Bean 定义的载体，其实质都是为 Spring 容器提供 Bean 定义的信息 。 基于注解的配置方式，从`Spring2.0 `开始引入， `Spring2.5 `完善， `Spring4.0` 得到了进一步的增强 。对于DI使用注解，将不再需要在Spring配置文件中声明Bean实例。

### 7.1 注解环境配置

打开官网：<https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-annotation-config>

（1）声明 context 命名空间和 schema 文件。**需要添加context名称空间**。

（2）通过 context 命名空间的 component-scan 的 base-package 属性指定一个需要扫描的基类包， Spring 容器会扫描这个基类包里的所有类，并从类的注解信息中获取 Bean 的定义信息 。

````xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    <!--添加context名称空间-->
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        <!--添加context schema-->
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--（2）需要在Spring配置文件中配置组件扫描器，用于在指定的基本包中扫描注解。-->
    <context:component-scan base-package="org.example"/>

</beans>
````

如果希望扫描特定的类，那么可以使用 resouce-pattern 属性过滤出特定的类：

````xml
<context:component-scan base-package="net.deniro.spring4"
                        resource-pattern="anno/*.class"/>
````

**resource-pattern** 属性会按照资源名称对基类包中的类进行过滤。但默认情况下 **resource-pattern** 属性的值为 `**./*.class`, 即基类包里所有的类 。 上面的设置，让它仅扫描基类包中的 anno 子包下的类。

`<context:component-scan>` 提供了 include-filter 与 exclude-filter 子元素，它们可以对需要过滤的包进行更精细的控制。

````xml
<context:component-scan base-package="net.deniro.spring4"
                        resource-pattern="anno/*.class">
    <!-- 需要包含的类-->
    <context:include-filter type="aspectj" expression="com.xzit.spring4..*Service+"/>
    <!-- 排除的类-->
    <context:exclude-filter type="regex" expression="com\.xzit\.spring4\.anno.*"/>
</context:component-scan>
````

- **`<context:include-filter>`** 表示要包含的目标类。指定的类路径必须在 base-package 所指定的基本路径下。


- `<context:exclude-filter>` 表示要排除的目标类。


- 一个 `<context:component-scan>` 下可以有多个 `<context:include-filter>` 和 `<context:exclude-filter>` 元素。

这两个过滤元素支持多种类型的过滤表达式：

| 类别         | 示例                                   | 说明                                       |
| ---------- | ------------------------------------ | ---------------------------------------- |
| annotation | com.xzit.spring4.XxxAnnotation       | 表示所有标注了 XxxAnnotation 的类。                |
| assignable | com.xzit.spring4.XxService           | 所有继承或扩展 XXXService 的类                    |
| aspectj    | com.xzit.spring4..*Service+          | 所有类名以 Service 结束的类及继承或者扩展它们的类，使用的是 AspectJ 表达式。 |
| regex      | com\.xzit\.spring4\.anno.*           | 所有 com\.xzit\.spring4.auto 类包下的类，使用的是正则表达式。 |
| custom     | com.xzit\.spring4.auto.XxxTypeFilter | 使用代码方式实现过滤，这个类必须实现 `org.springframework.core.type.TypeFilter` 接口。 |


这些过滤类型中，除了 custom 类型外， aspectj 的过滤表达能力是最强的，可以轻易地实现其他类型所表达的过滤规则 。

`<context:component-scan>`  元素有一个 **use-default-filters** 属性，它的默认值为 **true**, 表示会扫描标注了 @Component、@Controller、@Service、@Repository 的 Bean。

### 7.2 Spring 常用注解

#### 7.2.1 定义 Bean

````java
@Component(value="teacherDao")  //其中(value="teacherDao")可以省略
public class TeacherDao {
    //通过工号查询教师信息
    public Teacher findByTwno(String twno){
        System.out.println("通过工号(" + twno + ")查询教师信息");
        return null;
    }
}
````

使用 @Component 注解在 TeacherDao 类声明处对它进行标注，这样它就可以被 Spring 容器识别， 并把这个类转换为容器管理的 Bean。它和下面的 XML 配置是等效的：

````xml
<bean id="teacherdao" class="com.xzit.springboot.dao.TeacherDao"/>
````

测试：

````java
@Test
    public void componentAnno(){
        //1.加载 spring.xml 配置文件
        ApplicationContext applicationContext = 
               new ClassPathXmlApplicationContext("spring-beans3.xml");
        //2.通过 id 值获取对象
        TeacherDao  teacherDao = (TeacherDao)applicationContext.getBean("teacherDao");
        teacherDao.findByTwno("xzit1007");
    }

````

除了 @Component 注解外， Spring 还提供了 3 个功能与 @Component 等效的注解：

| 注解          | 说明                 |
| ----------- | ------------------ |
| @Repository | 标注 DAO 实现类         |
| @Service    | 标注 Service 实现类。    |
| @Controller | 标注 Controller 实现类。 |

之所以提供了这 3 个特定的注解，是为了让标注类本身的用途更清晰，所以，推荐使用这些特定注解来标注功能类。后面应用中我们还会讲到。

#### 7.2.2 自动装载 Bean

##### (1)@Autowired 自动注入

````java
@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    //通过工号查询教师信息
    public Teacher findByTwno(String twno){
        return teacherDao.findByTwno(twno);
    }
}
````

@Autowired 默认按类型（byType） 匹配的方式在容器中查找，如果有且仅有一个匹配的 Bean 时，Spring 会将其注入到标注了 @Autowired 的变量中。如果有多个类型相同的Bean时需要通过@Qualifier按byName指定。

测试：

````java
@Test
public void autowiredAnno(){
  //1.加载 spring.xml 配置文件
  ApplicationContext applicationContext = 
    new ClassPathXmlApplicationContext("spring-beans3.xml");
  //2.通过 id 值获取对象
  TeacherService teacherService = 
    (TeacherService)applicationContext.getBean(TeacherService.class);
  teacherService.findByTwno("xzit1007");
}
````

##### (2)@Auotwired 的 required 属性

如果容器中没有一个和标注变量类型匹配的 Bean ，那么 Spring 启动时会抛出 NoSuchBeanDefinitionException 异常 。  如果希望 Spring 即使找不到匹配的 Bean 不需要抛出异常，那么就可以使用 `@Autowired(required=false)` 对变量进行标注 。 如：

````java
 @Autowired(required=false)
 private TeacherDao teacherDao;
````

##### (3)@Qualifier

如果容器中有一个以上匹配的 Bean 时，则可以通过 @Qualifier 注解限定 Bean 的名称 。

假设在 Spring容器中存在两个类型都为 TeacherDao的 Bean：

````xml
<bean id="teacherdao1" class="com.xzit.springboot.dao.TeacherDao"/>
<bean id="teacherdao2" class="com.xzit.springboot.dao.TeacherDao"/>
````

那么通过 @Qualifier 注解指定需要注入的 Bean：

````java
@Autowired
@Qualifier("teacherdao1")
private TeacherDao teacherDao;
````

一般情况下， Spring 容器中大部分的 Bean 是单实例的，所以一般无需通过 @Repository、@Service 等注解的 value 属性指定 Bean 的名称，也无须使用 @Qualifier 注解指定名称进行注入 。

##### (4)标注类方法

虽然 Spring 支持在属性和方法上标注自动注入注解 @Autowired, 但在实践中建议采用在方法上标注 @Autowired 的方式，因为这样更加 “ 面向对象 ” ，也方便编写单元测试， 如果把注解标注在私有属性上，则在单元测试阶段就很难用编程的方式来设置属性值 。

@Autowired 即可以标注类成员变量，也可以标注方法的入参。

````java
private TeacherDao teacherDao;

/**
  * 标注方法，这里会自动将 LogDao 传给方法入参。
  * @param teacherDao
  */
@Autowired
public void setTeacherDao(TeacherDao teacherDao) {
  this.teacherDao = teacherDao;
}
````

如果一个方法拥有多个入参，在默认情况下，Spring 将自动注入匹配入参类型的 Bean。可以对方法入参标注 @Qualifier 用于指定注入 Bean 的名称 。

````java
@Autowired
public void setTeacherDao(@Qualifier("teacherDao") TeacherDao teacherDao) {
  this.teacherDao = teacherDao;
}
````

##### (5)标注集合类

如果使用 @Autowired 标注了类中的集合类型的变量或者方法入参，那么 Spring 会将容器中所有类型匹配的 Bean 都注入进来 。

````java
public interface BaseDao {}
````

它有两个实现类：

````java
@Repository
public class DaoImpl1 implements BaseDao {  }
````

````java
@Repository
public class DaoImpl2 implements BaseDao {  }
````

把所有实现了 BaseDao接口的类都注入到下面的 MyOperation类的变量中：

````java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import java.util.*;

@Service
public class MyOperation {
    /**
     * 把所有类型为 BaseDao 的 Bean 都注入这个 List
     */
    @Autowired(required = false)
    private List<BaseDao> baseDaos;

    /**
     * 把所有类型为 BaseDao 的 Bean 都注入这个 Map
     * key：Bean 名称；value：所有实现了 BaseDao 接口的 Bean
     * 注入 Map 集合，是 Spring 4 提供的新特性。
     */
    @Autowired
    private Map<String, BaseDao> baseDaoMap;

    public List<BaseDao> getPlugins() {
        return baseDaos;
    }

    public Map<String, BaseDao> getPluginMap() {
        return baseDaoMap;
    }
}
````

单元测试：

````java
@Test
public void collectionsAutowiredAnno(){
  //1.加载 spring.xml 配置文件
  ApplicationContext applicationContext = 
    new ClassPathXmlApplicationContext("spring-beans3.xml");
  //2.通过 id 值获取对象
  MyOperation myOperation = (MyOperation)applicationContext.getBean("myOperation");
  System.out.println(myOperation.getPlugins());
  System.out.println(myOperation.getPluginMap());
}
````

运行结果：

![](1.Spring IoC初识\集合注入.png)

在默认情况下，这两个 bean 的加载顺序是不确定，在 Spring4 中可以通过 @Order 注解或者实现 Ordered 接口来决定 Bean 加载的顺序，值越小，表示优先级越高 （只对有序的集合类型有效）。

````java
@Repository
@Order(value = 1)
public class DaoImpl1 implements BaseDao {
}
````

##### (6)延迟依赖注入

Spring4 支持延迟依赖注入，即在 Spring 容器时，对标注了 @Lazy 和 @Autowired 注解的 Bean 属性，不会立即注入， 而是到调用该属性时才会注入 。

````java
@Lazy
@Repository
public class TeacherDao { ... }

@Lazy
@Autowired
public void setTeacherDao(TeacherDao teacherDao) {
  this.teacherDao = teacherDao;
}
````

**注意：**对 Bean 实施延迟依赖注入，@Lazy 注解必须同时标注在属性及目标的 Bean 上 。

#### 7.2.3 标准注解

##### (1)@Resource和 @Inject 注解

Spring 还支持` JSR-250 `中定义的 @Resource 和` JSR-330` 中定义的 @Inject 注解，这两个标准注解和 `@Autowired `注解的功能相似，都能对类的变更及方法入参提供自动注入功能 。

@Resource注解要求提供一个 Bean 名称的属性，如果属性为空，则自动采用标注处的变量名或者方法名作为 Bean 的名称 。

````java
private ResourceDao resourceDao;

@Resource(name = "resourceDao")
public void setResourceDao(ResourceDao resourceDao) {
    this.resourceDao = resourceDao;
}
````

如果 @Resource 未指定 name 属性，则会根据属性方法得到需要注入的 Bean 名称 。    @Autowired 默认按照类型匹配注入 bean ，  @Resource 默认按照名称匹配注入 Bean。 而 @Inject 和 @Autowired 一样也是按照类型匹配注入 Bean 的，只不过它没有 required 属性 。

可见不管是 @Resource 或者 @Inject 注解，它们的功能都没有 @Autowired 来的丰富，因此除非必要，否则可以忽略这两个注解。

##### (2)`@PostConstruct `和 `PreDestory `注解

在使用 `<bean>` 进行配置时，可以通过` init-method` 和 `destory-method` 属性指定 Bean 的初始化及容器销毁前执行的方法 。Spring 从 2.5 开始支持 JSR-250 中定义的` @PostConstruct `和 `PreDestory` 注解 。  在 Spring 中它们相当于 `init-method` 和 `destroy-method` 属性的功能 。不过注解方式更强大，我们可以在一个 bean  中定义多个 `@PostConstruct `和 `@PreDestory `方法 。

````java
@Repository(value = "daoImpl3")
public class DaoImpl3 implements BaseDao {
    @PostConstruct
    private void init1(){
        System.out.println("init1");
    }

    @PostConstruct
    private void init2(){
        System.out.println("init2");
    }

    @PreDestroy
    private void destroy1(){
        System.out.println("destroy1");
    }

    @PreDestroy
    private void destroy2(){
        System.out.println("destroy2");
    }
}
````

测试程序：

````java
@Test
    public void postConstructAnno(){
        //1.加载 spring.xml 配置文件
        ApplicationContext applicationContext = 
              new ClassPathXmlApplicationContext("spring-beans3.xml");
        //2.通过 id 值获取对象
        BaseDao baseDao = (BaseDao)applicationContext.getBean("daoImpl3");
        //3.bean销毁
        ((ClassPathXmlApplicationContext) applicationContext).destroy();
    }
````

测试结果：

````text
init1
init2

destroy1
destroy2
````

可以看出，Spring 先调用了类的构造函数实例化了 Bean ，然后再执行 @Autowired 进行自动注入，接着执行标注了 @PostConstruct 的方法，最后在容器关闭时，执行标注了 @PreDestroy 的方法 。

#### 7.2.4 @Scope 注解

通过注解配置的 Bean 和通过 `<bean>` 配置的 Bean 是一样的，默认的作用范围都是 singleton。 Spring 为注解配置提供了一个 `@Scope` 注解，可以通过它显式地指定 Bean 的作用范围 。

````java
@Scope("prototype")
@Component
public class CommonBean {

}
````

## 8 小结

- spring是一个容器框架，它可以接管web层，业务层，`dao`层，持久层的各个组件，并且可以配置各种bean， 并可以维护bean与bean的关系，当我们需要使用某个bean的时候，我们可以直接`getBean(id)`，使用即可。
- `ioc（inverse of control）`控制反转：所谓反转就是把创建对象（bean）和维护对象（bean）的关系的权利从程序转移到spring的容器（`spring-config.xml`）。
- `di（dependency injection）`依赖注入：实际上`di`和`ioc`是同一个概念，spring的设计者，**认为`di`更准确的表示spring的核心**。