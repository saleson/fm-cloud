# fm-cloud
## fm-cloud-bamboo: 基于spring cloud的接口多版本控制

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
        at rx.Observable.unsafeSubscribe(Observable.java:10211) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.internal.operators.OnSubscribeConcatMap.call(OnSubscribeConcatMap.java:94) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.internal.operators.OnSubscribeConcatMap.call(OnSubscribeConcatMap.java:42) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:48) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:30) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:48) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.internal.operators.OnSubscribeLift.call(OnSubscribeLift.java:30) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.Observable.subscribe(Observable.java:10307) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.Observable.subscribe(Observable.java:10274) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.observables.BlockingObservable.blockForSingle(BlockingObservable.java:445) ~[rxjava-1.1.10.jar:1.1.10]
        at rx.observables.BlockingObservable.single(BlockingObservable.java:342) ~[rxjava-1.1.10.jar:1.1.10]
        at com.netflix.client.AbstractLoadBalancerAwareClient.executeWithLoadBalancer(AbstractLoadBalancerAwareClient.java:117) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        at com.netflix.client.AbstractLoadBalancerAwareClient$$FastClassBySpringCGLIB$$c930f31.invoke(<generated>) ~[ribbon-loadbalancer-2.2.2.jar:2.2.2]
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:738) ~[spring-aop-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:157) ~[spring-aop-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at com.fm.cloud.bamboo.BambooExtConfigration$ExecuteWithLoadBalancerMethodInterceptor.invoke(BambooExtConfigration.java:72) ~[classes/:na]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:168) ~[spring-aop-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:673) ~[spring-aop-4.3.9.RELEASE.jar:4.3.9.RELEASE]
        at org.springframework.cloud.netflix.ribbon.apache.RibbonLoadBalancingHttpClient$$EnhancerBySpringCGLIB$$eb0cd50d.executeWithLoadBalancer(<generated>) ~[spring-cloud-netflix-core-1.3.0.RELEASE.jar:1.3.0.RELEASE]
        at org.springframework.cloud.netflix.zuul.filters.route.support.AbstractRibbonCommand.run(AbstractRibbonCommand.java:105) ~[spring-cloud-netflix-core-1.3.0.RELEASE.jar:1.3.0.RELEASE]
      ```
      
 * fm-eureka-client2 
    
    这个demo中有使用RestTemplate和Feign两种方式进行访问fm-dureka-client中的接口。
    