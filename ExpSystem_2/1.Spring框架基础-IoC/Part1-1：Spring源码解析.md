## spring源码分析

## 1、从Spring容器启动分析其工作流程

（AbstractApplicationContext抽象类里的refresh方法）

### 1.1 阅读源码进行debug调试的方法

```java
@Test
public void beas(){
    //创建Spring IoC容器 ApplicationContext
    ApplicationContext context = 
        new ClassPathXmlApplicationContext("spring-beans.xml");
    //从容器中获得对象
    Department department1 = (Department) context.getBean("department1");
    System.out.println(department1);
}
```

从上面第5行`new  ClassPathXmlApplicationContext `开始进入下面的方法:

```java
public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
    this(new String[]{configLocation}, true, (ApplicationContext)null);
}
```

点击第2行`this`方法进入下面的方法：

```java
public ClassPathXmlApplicationContext(
    String[] configLocations, boolean refresh, 
    @Nullable ApplicationContext parent)
    throws BeansException {

    super(parent);
    setConfigLocations(configLocations);
    if (refresh) {
        refresh();
    }
}
```

第7行是设置配置文件，就是传递的classpath:spring-beans.xml
第9行是调用refresh方法，也就是spring容器启动的入口所在，该方法中共有13个子方法

```java
public void refresh() throws BeansException, IllegalStateException {
   synchronized (this.startupShutdownMonitor) {
      // Prepare this context for refreshing.
      /*（1）完成前期的准备工作（其实这一步什么都没有处理，只是准备一些数据）
      准备要刷新的上下文：
           设置启动日期和激活标志，以便执行任意属性来源的初始化
           初始化上下文环境中的占位符属性来演
           获取环境信息并校验必传参数
           准备早期的应用程序监听器
           准备早期应用监听事件，一旦多播器可用就将早期的应用事件发布到多播器中*/
      prepareRefresh();

      // Tell the subclass to refresh the internal bean factory.
      //（2）创建容器对象：DefaultListableBeanFactory
      //加载xml配置文件的属性值到当前工厂中，最重要的就是BeanDefinition
      //获得一个新的bean工厂对象，来装载具体的bean对象
      //让子类刷内置的bean工厂，返回的是ConfigurableListableBeanFactory的子类对象DefaultListableBeanFactory
      //注意：BeanFactory和ApplicationContext的区别：前者在加载文件时不创建对象，后者在加载文件时就创建好bean对象
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

      // Prepare the bean factory for use in this context.
      //（3）beanFactory的准备工作，对各种属性进行填充
      //此时只是开辟了空间,还没有赋值,新的bean对象中有很多属性值都是默认的0,在这里要进行赋值
      prepareBeanFactory(beanFactory);

      try {
         // Allows post-processing of the bean factory in context subclasses.
         //（4）子类覆盖方法做额外的处理，此处我们自己一般不做任何扩展工作，但是可以查看web中的代码，是有具体实现的
         //空方法,交给子类来实现
         postProcessBeanFactory(beanFactory);

         // Invoke factory processors registered as beans in the context.
         //（5）调用各种beanFactory处理器
         //实例化并且调用所有的BeanFactoryPostProcessor接口,对bean的定义信息进行加强修改
         invokeBeanFactoryPostProcessors(beanFactory);

         // Register bean processors that intercept bean creation.
         //（6）准备好监听器/监听事件等等,实例化并且注册所有的BeanPostProcessor
         //但是此刻不会执行,只是为后续流程做准备工作
         registerBeanPostProcessors(beanFactory);

         // Initialize message source for this context.
         //（7）做国际化处理
         initMessageSource();

         // Initialize event multicaster for this context.
         //（8）初始化应用事件的多播器,用来发布监听事件
         initApplicationEventMulticaster();

         // Initialize other special beans in specific context subclasses.
         //（9）能够被覆盖的模板方法，用来添加特定上下文的更新工作，在特殊bean进行初始化或者单例bean进行实例化时被调用，在该类中是一个空实现
         //三个子类中都是调用UiApplicationContextUtils.initThemeSource(this)方法
         onRefresh();

         // Check for listener beans and register them.
         //（10）注册监听器,这一步就完成在实例化bean对象之前完成监听器和监听事件的准备工作
         //在所有注册的bean中查找Listener bean,注册到消息广播器中，即向监听器发布事件
         registerListeners();

         // Instantiate all remaining (non-lazy-init) singletons.
         //（11）实例化剩下的所有的非懒加载的单例对象
         //对非延迟初始化的单例进行实例化，一般情况下的单例都会在这里就实例化了，这样的好处是，在程序启动过程中就可以及时发现问题
         finishBeanFactoryInitialization(beanFactory);

         // Last step: publish corresponding event.
         //（12）最后一步：完成刷新过程，通知生命周期处理器lifecycleProcessor刷新过程
         finishRefresh();
      }
      
      catch (BeansException ex) {
         if (logger.isWarnEnabled()) {
            logger.warn("Exception encountered during context initialization - " +
                  "cancelling refresh attempt: " + ex);
         }

         // Destroy already created singletons to avoid dangling resources.
         //当发生异常时销毁已经创建的单例
         destroyBeans();

         // Reset 'active' flag.
         //重置active标识为false
         cancelRefresh(ex);

         // Propagate exception to caller.
         throw ex;
      }

      finally {
         // Reset common introspection caches in Spring's core, since we
         // might not ever need metadata for singleton beans anymore...
         //（13）清空所有的缓存，因为单例bean是在容器启动时初始化完毕，所以不需要保留它们的元数据信息
         resetCommonCaches();
      }
   }
}
```

### 1.2 refresh方法主要完成工作思维导图

![](1.1Spring源码解析\1.refresh方法.png)

### 1.3 spring工作流程图

![](1.1Spring源码解析\2.spring-workflow.png)

### 1.4 spring设计思想图

![](1.1Spring源码解析\3.spring-source-framework.png)

## 2、spring源码的debug过程

带着下面的问题去实现debug
（1）图解spring IOC容器的核心实现原理
（2）spring的扩展实现一：BeanFactoryPostProcessor接口详解
（3）spring的扩展实现二：BeanPostProcessor接口详解
（4）必知必会的13个Bean生命周期处理机制
（5）spring bean实现Aware接口的意义
（6）BeanFactory和FactoryBean的接口对比

### 2.1 首先是测试方法的入口【ApplicationContext】

```java
@Test
public void beas(){
    //创建Spring IoC容器 ApplicationContext
    ApplicationContext context = 
        new ClassPathXmlApplicationContext("spring-beans.xml");   
}
```

### 2.2 读取xml配置文件

通过setConfigLocations方法指定xml文件

![](1.1Spring源码解析\5.readxml.png)

读取xml配置文件的过程如下:

![](1.1Spring源码解析\5.readxml-p.png)

找到加载xml配置文件的方法:

![](1.1Spring源码解析\6.loadxml.png)

在最终实现的地方，先把xml文件通过IO流转成resource文件，

![](1.1Spring源码解析\7.loadxml.png)

然后点进去开始对resource进行处理，把resource转成document，再把document转成node父子节点

![](1.1Spring源码解析\8.loadxml.png)

点进来，先获得子节点然后遍历子节点，判断是以默认元素进行解析还是以用户自定义元素进行解析

![](1.1Spring源码解析\9.loadxml.png)

### 2.3 构造器中调用【refresh】方法

在ApplicationContext构造方法里调用到refresh方法。先调用父类构造方法，然后设置配置文件的路径，最后判断调用refresh方法，也就是进入了重点的13个方法

![](1.1Spring源码解析\10.loadxml.png)

### 2.4 为创建bean工厂做准备工作【prepareRefresh】

接下来的第一步应该是创建bean工厂，有了BeanFactory才能有地方存放bean对象，进入【prepareRefresh】方法。这个方法里准备好一系列的准备工作，但是实际什么都没有执行。

```java
protected void prepareRefresh() {
		// Switch to active.
		//设置启动时间
		this.startupDate = System.currentTimeMillis();
		//设置容器关闭为false
		this.closed.set(false);
		//设置容器活跃为true
		this.active.set(true);

		if (logger.isInfoEnabled()) {
			logger.info("Refreshing " + this);
		}

		// Initialize any placeholder property sources in the context environment.
		//初始化一些属性资源，方法主体是空的，交给子类去实现
		initPropertySources();

		// Validate that all properties marked as required are resolvable:
		// see ConfigurablePropertyResolver#setRequiredProperties
		//获取环境对象，并且验证属性值
		getEnvironment().validateRequiredProperties();

		// Store pre-refresh ApplicationListeners...
		//创建一些集合
		if (this.earlyApplicationListeners == null) {
			this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);
		}
		else {
			// Reset local application listeners to pre-refresh state.
			this.applicationListeners.clear();
			this.applicationListeners.addAll(this.earlyApplicationListeners);
		}

		// Allow for the collection of early ApplicationEvents,
		// to be published once the multicaster is available...
		this.earlyApplicationEvents = new LinkedHashSet<>();
	}
```

### 2.5 开始创建bean工厂和bean对象加载【obtainFreshBeanFactory】

上面准备工作做完后开始创建bean工厂，进入【obtainFreshBeanFactory】方法，进入【refreshBeanFactory】方法，首先判断是否已存在bean工厂，如果已存在就销毁，并且重新调用【createBeanFactory】方法创建bean工厂

![](1.1Spring源码解析\11.loadxml.png)

创建的bean工厂实际的名字就是DefaultListableBeanFactory

![](1.1Spring源码解析\12.loadxml.png)

创建完成bean工厂后开始设置属性值



![](1.1Spring源码解析\13.loadxml.png)

完成属性值设置后，可以先看一下工厂里是否有bean对象，此时存储的量都是0，所以此时是没有bean对象的

![](1.1Spring源码解析\14.loadxml.png)

在完成属性值的设置接下来开始读取xml/注解等配置文件来装载BeanDefinition，完成加载后再看就发现已经加载了bean对象了

![](1.1Spring源码解析\15.loadxml.png)

### 2.6 bean工厂里众多属性值需要赋值【prepareBeanFactory】

方法内部有众多的set方法，可以给bean工厂设置一些属性值

![](1.1Spring源码解析\16.loadxml.png)

### 2.7 通过增强器对bean进行处理【postProcessBeanFactory】

这个方法点进去是空的，交给具体的接口实现类来实现，真正使用的时候可以通过实现接口来增强处理

![](1.1Spring源码解析\17.loadxml.png)

在BeanFactory接口里是没有getBeanDefinition方法来获取BeanDefinition对象的，但是BeanFactory有很多实现类和子接口，而在子类ConfirurableListableBeanFactory中有getBeanDefinition方法可以获取BeanDefinition对象。所以在上面方法中可以用这个类的对象beanFactory来调用方法获取BeanDefinition对象，并且对对象进行修改增强，然后返回BeanDefinition对象。

![](1.1Spring源码解析\18.loadxml.png)

### 2.8 实例化并调用BeanFactoryPostProcessor方法【invokeBeanFactoryPostProcessors】

上面那一步只是准备方法，实际调用增强器BeanFactoryPostProcessor是在这一步

有一点值得注意的是，在前面读取xml配置文件的时候并且注入BeanDefinition对象的时候，文件里的值都是直接装进去的，并没有对原始值做任何的修改，包括配置文件中的一些变量占位符

![](1.1Spring源码解析\19.loadxml.png)

当这一步执行完毕后，这些占位符就已经完成了替换，这就是通过调用增强器BeanFactoryPostProcessor来实现的

![](1.1Spring源码解析\20.loadxml.png)

上面的实现是spring框架中自带的BeanFactoryPostProcessor实现类完成的，如果我们想对BeanDefinition对象进行一些其他的修改，也可以通过自己实现BeanFactoryPostProcessor接口，然后调用对应的方法来实现修改。

### 2.9 实例化并注册BeanPostProcessor【registerBeanFactoryProcessors】

在这里只是先进行实例化和注册，但是此时并不会实际的使用，只是为后面调用BeanPostProcessor做好准备，因为后面需要用到观察者模式等等，这些都要在开始实例化bean对象之前都做好准备。

### 2.10 做国际化处理【initmessageSource】

### 2.11 初始化应用事件多播器，方便后面的发布监听事件

### 2.12 onFresh方法

空的，一些Tomcat和severlet的扩展功能就是在这里实现

### 2.13 创建监听器，方便后面使用【registerListener】

### 2.14 【bean生命周期】开始实例化剩下的非懒加载单例bean对象【finishBeanFactoryInitialization】

从这里开始其实就是开始了bean的生命周期

![](1.1Spring源码解析\21.loadxml.png)

#### （1）把所有的beanName放进List，然后开始遍历完成实例化bean对象

![](1.1Spring源码解析\22.loadxml.png)

#### （2）开始实例化bean对象

首先判断是不是抽象的，是不是单例的，是不是懒加载的（true）,然后判断工厂里是不是有这个对象（false）

![](1.1Spring源码解析\23.loadxml.png)

因为这个false，所以走判断的另一个分支，会调用到getBean方法，接着走到doGetBean方法

![](1.1Spring源码解析\24.loadxml.png)

首先是调用getSingleton方法尝试从工厂里获取bean对象，结果为null，走判断的另一个分支

![](1.1Spring源码解析\25.loadxml.png)

然后就是走循环依赖的那一个步骤，判断有没有依赖属性，然后从三级缓存里尝试获取bean对象。如果都没有找到，那就走到createBean方法来创建这个bean对象。在createBean方法里找到实际干活的doCreateBean方法，然后走到createBeanInstance方法，走到最后可以看到实例化的核心也就是通过反射来实例化对象。

![](1.1Spring源码解析\26.loadxml.png)

确认一下bean对象是否完成实例化，此时已经有了对象A

![](1.1Spring源码解析\27.loadxml.png)

#### （3）开始填充属性（用户自定义属性赋值）

![](1.1Spring源码解析\28.loadxml.png)

执行完populateBean方法后，name属性是有值的了，完成了属性填充

![](1.1Spring源码解析\29.loadxml.png)

#### （4）开始调用Aware接口（容器对象赋值）

在完成对象的属性设置之后，需要调用一些类如setBeanFactory和setApplicationContext的方法，这些方法肯定不是我们自己调用，而是交给容器去调用，但是在启动的时候容器是不知道调用哪个set方法的，所以这里就可以定义统一的规范接口来实现这个功能.

![](1.1Spring源码解析\30.loadxml.png)

Aware接口是空的，但是Aware接口有很多实现子类

```java
package org.springframework.beans.factory;
public interface Aware {
}
```

```java
public interface BeanFactoryAware extends Aware {
    void setBeanFactory(BeanFactory var1) throws BeansException;
}
```

```java
public interface ApplicationContextAware extends Aware {

	/**
	 * Set the ApplicationContext that this object runs in.
	 * Normally this call will be used to initialize the object.
	 * <p>Invoked after population of normal bean properties but before an init callback such
	 * as {@link org.springframework.beans.factory.InitializingBean#afterPropertiesSet()}
	 * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
	 * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
	 * {@link MessageSourceAware}, if applicable.
	 * @param applicationContext the ApplicationContext object to be used by this object
	 * @throws ApplicationContextException in case of context initialization errors
	 * @throws BeansException if thrown by application context methods
	 * @see org.springframework.beans.factory.BeanInitializationException
	 */
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
```

通过实现这些接口，就可以调用统一的方法来实现给容器的对象赋值

![](1.1Spring源码解析\31.loadxml.png)

#### （5）开始初始化前置处理【BeanpostProcessor:before】

先是调用Aware接口

![](1.1Spring源码解析\32.loadxml.png)

然后调用BeanpostProcessor:before方法

![](1.1Spring源码解析\33.loadxml.png)

在这个方法里对之前已经注册的BeanPostProcessor进行遍历放进一个集合，然后调用实际的before方法

![](1.1Spring源码解析\34.loadxml.png)

调用实际的before方法，这个方法里其实没有任何实现

![](1.1Spring源码解析\35.loadxml.png)

#### （6）开始执行初始化调用init方法

找到invokeInitMethods

![](1.1Spring源码解析\36.loadxml.png)

进入invokeInitMethods方法，首先判断是否实现了InitializingBean接口，如果实现了的话就调用afterPropertiesSet方法，这个方法是对象完成初始化之前最后一个入口可以修改对象的属性值

![](1.1Spring源码解析\37.loadxml.png)

#### （7）开始初始化后置处理【BeanpostProcessor:after】

![](1.1Spring源码解析\38.loadxml.png)

接着调用实际的after方法，在方法中首先获取bean的key，然后判断当前的bean是否正在被代理，如果没有被代理且需要被代理，那么就封装指定的bean

![](1.1Spring源码解析\39.loadxml.png)

点进去找到createProxy方法来创建代理

![](1.1Spring源码解析\40.loadxml.png)

点进去看到工厂模式创建代理对象，通过代理工厂创建代理对象

![](1.1Spring源码解析\41.loadxml.png)

点进去看到

![](1.1Spring源码解析\42.loadxml.png)

查找getProxy方法的具体实现，就看到了两种代理方式的方法Jdk和Cglib

![](1.1Spring源码解析\43.loadxml.png)

到这里也就是通过代理对象实现了AOP功能，所以说AOP功能就是IOC功能的一个扩展实现，是在增强器中实现的。

执行完成后返回这个对象，结束这一次的循环，接着遍历下一个beanName

### 2.15 bean生命周期概述

（1）实例化bean对象
通过反射的方式进行对象的创建，此时的创建只是在堆空间中申请空间，属性都是默认值
（2）设置对象属性
给对象中的属性进行值的设置工作
（3）检查Aware相关接口并设置相关依赖
如果对象中需要引用容器内部的对象，那么需要调用Aware接口的子类方法来进行统一的设置
（4）BeanPostProcessor的前置处理
对生成的bean对象进行前置的处理工作
（5）检查是否是initializingBean的子类来决定是否调用afterPropertiesSet方法
判断当前bean对象是否设置了initializingBean接口，然后进行属性的设置等基本工作
（6）检查是否配置有自定义的init-method方法
如果当前bean对象定义了初始化方法，那么在此处调用初始化方法
（7）BeanPostProcessor后置处理
对生成的bean对象进行后置的处理工作
（8）注册必要的Destruction相关回调接口
为了方便对象的销毁，在此处调用注销的回调接口，方便对象进行销毁操作

![](1.1Spring源码解析\44.loadxml.png)



![](1.1Spring源码解析\45.loadxml.png)



![](1.1Spring源码解析\46.loadxml.png)



![](1.1Spring源码解析\4.loadxml.png)