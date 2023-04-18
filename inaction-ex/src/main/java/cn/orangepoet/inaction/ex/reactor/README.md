# Reactor 响应式

## 两种编程模式

JDK中提供了两种异步编程的模型：

第一种是Callbacks，异步方法可以通过传入一个Callback参数的形式来在Callback中执行异步任务。比较典型的像是java
Swing中的EventListener。

    callback的问题就在于回调地狱。熟悉JS的朋友应该很理解这个回调地狱的概念。

第二中就是使用Future了。我们使用Callable来提交一个任务，然后通过Future来拿到它的运行结果。

    而Future主要是对一个异步执行的结果进行获取，它的 get()实际上是一个block操作。并且不支持异常处理，也不支持延迟计算。

    当有多个Future的组合应该怎么处理呢？JDK8 实际上引入了一个CompletableFuture类，这个类是Future也是一个CompletionStage，CompletableFuture支持then的级联操作。不过CompletableFuture提供的方法不是那么的丰富，可能满足不了我的需求。

## Linux 五种 IO 模型

(1) 阻塞 IO (Blocking IO)
(2) 非阻塞 IO (Non-blocking IO)
(3) 多路复用 IO (IO multiplexing)
(4) 信号驱动 IO (signal driven I/O (SIGIO))
(5) 异步 IO (asynchronous I/O)
前四种都是同步的，只有最后一种才是异步 IO。

## Flux

Reactor提供了两个非常有用的操作，他们是 Flux 和 Mono。 其中Flux 代表的是 0 to N 个响应式序列，而Mono代表的是0或者1个响应式序列。

### 关于回压

https://lxdd.gitbook.io/spring-webflux/reactor/yuan-yuan


