# <font color="red">代码迁移到https://github.com/SpringCloud/spring-cloud-gray</font>




# fm-cloud
## fm-cloud-bamboo: 基于spring cloud的接口多版本控制

fm-cloud-bamboo支持RestTemplate、Feign、网关(Zuul)、断路器（hystrix,包括线程隔离）。
在服务消费方的pom.xml文件中引入fm-cloud-starter-bamboo就可以集成了。

``` xml 
    <dependency>
        <groupId>com.fm</groupId>
        <artifactId>fm-cloud-starter-bamboo</artifactId>
    </dependency>
```

在fm-cloud-demo下有三个demo项目
 * fm-zuul-server 
    注册中心和网关，网关hystrix使用线程隔离，集成了fm-cloud-bamboo实现多版本控制
 
 * fm-eureka-client
    服务提供方，需要配置服务实例支持哪些版本,以下配置支持两个版本，分别是1和2
    ``` yaml
        eureka:
          client:
            register-with-eureka: true
            fetch-registry: true
            serviceUrl:
              defaultZone: http://localhost:10002/eureka/
          instance:
            metadata-map:
              versions: 1,2
    ```
    启动服务后就可以访问不同版本的接口和服务实例
    如访问http://localhost:10002/gateway/client/api/test/get?version=2
    会返回数据
     ``` json
        {
            "test": "success.",
            "serverPort": "10101"
        }
     ```
 
     如果访问http://localhost:10002/gateway/client/api/test/get?version=3
     会报错， 因为找不到支持版本3的服务实例
     ``` java
    Caused by: com.netflix.client.ClientException: Load balancer does not have available server for client: eureka-client
        at com.netflix.loadbalancer.LoadBalancerContext.getServerFromLoadBalancer(LoadBalancerContext.java:483) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        at com.netflix.loadbalancer.reactive.LoadBalancerCommand$1.call(LoadBalancerCommand.java:184) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        at com.netflix.loadbalancer.reactive.LoadBalancerCommand$1.call(LoadBalancerCommand.java:180) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        ...
        at com.netflix.client.AbstractLoadBalancerAwareClient.executeWithLoadBalancer(AbstractLoadBalancerAwareClient.java:117) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        at com.netflix.client.AbstractLoadBalancerAwareClient$$FastClassBySpringCGLIB$$c930f31.invoke(<generated>) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:738) ~[spring-aop-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) ~[spring-aop-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at com.fm.cloud.bamboo.BambooExtConfigration$ExecuteWithLoadBalancerMethodInterceptor.invoke(BambooExtConfigration.java:72) ~[classes/:na]
        ...
      ```
      
 * fm-eureka-client2 
    
    这个demo中也引入fm-cloud-bamboo，支持RestTemplate和Feign两种方式进行访问fm-dureka-client中的接口。
    
    
    
## fm-cloud-graybunny: 基于spring cloud的灰度管理
灰度发布是在多版本控制的基础上进一步扩展实现出来的项目 -> fm-cloud-graybunny，抽象出灰度服务、灰度服务实例、灰度策略、灰度决策等。支持A/B test, 金丝雀 test。 灰度策略可以从request ip, request patameter, request header等方面进行去创建，也可以根据bamboo的LoadBalanceRequestTrigger结合graybuanny的接口去扩展灰度策略和灰度决策。

设计6个接口，4个模型对象：
###### 对象
    
* GrayService
灰度服务
    
* GrayInstance
灰度实例，有状态属性

* GrayPolicyGroup
灰度策略组，有状态属性

* GrayPolicy
灰度策略


###### 接口

* GrayManager
灰度客户端管理器，维护灰度列表，维护自身灰度状态，创建灰度决策对象。抽象实现类AbstractGrayManager实现了基础的获取灰度列表， 创建灰度决策对象的能力。BaseGrayManger在期基础上进行了扩展，将灰度列表缓存起来，定时从灰度服务端更新灰度列表。

* InformationClient
该接口主要是负责和灰度服务端进行通信，获取灰度列表，编辑灰度实例等能力。其实现类HttpInformationClient默认使用http方式访问灰度服务端。
子类InformationClientDecorator是一个适配器类，RetryableInformationClient继承了InformationClientDecorator类，实现了重试的功能。
* GrayDecision
该接口是灰度决策，用来判断请求是否匹配灰度策略。实现了ip匹配、request parameter匹配、request header匹配、BambooRequestContext中的参数匹配器以及合并匹配等多个匹配能力。

* GrayDecisionFactory
灰度决策的工厂类，其默认实现类支持上述几种灰度决策的创建。

* GrayServiceManager
灰度服务管理类，属于服务端的类。主要是编辑服务实例，编辑灰度策略，以及维护最新的灰度列表。

* GrayBunnyServerEvictor
如果灰度服务实例下线后， 由于意外情况，没有向灰度服务端发送删除请求， 服务端会每隔一段时间调用该接口的方法，检查灰度列表中的实例是否下线，如果实例已下线，就将其从灰度列表中删除。

###### 使用指导
灰度管理的配置和bamboo的配置是一样的， 配置方式差别不大。下面先说gray-server的配置。

**Gray-Server:**
在项目的pom.xml加入spring-boot相关的依赖，再加入bamboo-start、graybunny-server-starter，然后启动就可以了。
``` xml
    <dependencies>
        <dependency>
            <groupId>...</groupId>
            <artifactId>...</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fm</groupId>
            <artifactId>fm-cloud-starter-bamboo</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fm</groupId>
            <artifactId>fm-cloud-starter-graybunny-server</artifactId>
        </dependency>

    </dependencies>
```
在启动类中，需要雇用服务发现。
``` java
@SpringBootApplication
@EnableDiscoveryClient
public class GrayBunnyServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        new SpringApplicationBuilder(GrayBunnyServerApplication.class).web(true).run(args)
    }
}
```

启动后，可以访问http://localhost:10202/swagger-ui.html#/service-gray-resouce查看接口列表，也可以调用其中的接口。

![这里写图片描述](http://img.blog.csdn.net/20180123105938302?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvTXJfcmFpbg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

以上介绍完了gray-server的配置，下面再看gray-client的配置。

**Gray-Client**

 1. 在pom.xml中加入gm-cloud-graybunny。
 ``` xml
    <dependencies>
        <dependency>
            <groupId>...</groupId>
            <artifactId>...</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fm</groupId>
            <artifactId>fm-cloud-starter-bamboo</artifactId>
        </dependency>
        <dependency>
            <groupId>com.fm</groupId>
            <artifactId>fm-cloud-graybunny</artifactId>
        </dependency>
    </dependencies>
 ```
 
 2. 在application.yaml中加入灰度配置。
 ``` yaml
 graybunny:
  instance:
    grayEnroll: true #是否在启动后自动注册成灰度实例
  serverUrl: http://localhost:10202 #灰度服务端的url
 ```
 
 3. 在启动类中加入灰度客户端的注解@EnableGrayBunny
``` java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableGrayBunny
public class EurekaClient2Application {


    public static void main(String[] args) throws UnknownHostException {
        new SpringApplicationBuilder(EurekaClient2Application.class).web(true).run(args);
    }
}
```


这样灰略度的服务端和客户端都配置好了， 只要在灰度服务端开启灰度实例和灰度策，在灰度客户端就会自动进行灰度路由。

###### 不足
graybunny目前只有灰度管理的基本功能， 像数据持久化，高可用，推送灰度调整消息等， 都没有实现。 也没有界面化， 仅仅只有接口列表。


###### 扩展思考
graybunny目前仅仅只支持spring cloud eureka， 但是在spring cloud中，eureka只是做为其中一个注册中心， 如果要做spring cloud的灰度管理， 就还需要兼容其中的注册中心， 比如zookeeper, consul等。
