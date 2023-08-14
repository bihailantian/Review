# kotlin 协程

### 关键字：suspend
suspend 的中文意思是挂起，可以理解为把当前线程暂时挂起，稍后自动切回来到原来的线程上，可用于修饰普通的方法，表示这个方法是一个耗时操作，只能在协程的环境下才能调用，又或者在另一个 suspend 方法中调用。


**协程常见的三个操作符号**

- runBlocking：中文意思是运行阻塞，顾名思义，会阻塞当前线程执行

- launch：中文意思是启动，不会阻塞当前线程，但是会异步执行代码

- async：中文意思是异步，跟 launch 相似，唯一不同的是


**协程的线程调度器**
介绍一下线程调度器的类型，总共有四种：

- Dispatchers.Main：主线程调度器，人如其名，会在主线程中执行

- Dispatchers.IO：工作线程调度器，人如其名，会在子线程中执行

- Dispatchers.Default：默认调度器，没有设置调度器时就用这个，经过测试效果基本等同于 Dispatchers.IO

- Dispatchers.Unconfined：无指定调度器，根据当前执行的环境而定，会在当前的线程上执行，另外有一点需要注意，由于是直接拿当前线程执行，经过实践，协程块中的代码执行过程中不会有延迟，会被立马执行，除非遇到需要协程被挂起了，才会去执行协程外的代码，这个也是跟其他类型的调度器不相同的地方


> 需要注意的是 withContext 只能在协程的操作符或者被 suspend 修饰的方法中才能调用，具体的用法有两种，如下：

``` kotlin
suspend fun getUserName(userId: Int): String  {
    return withContext(Dispatchers.IO) {
        delay(20000)
        return@withContext "Android 轮子哥"
    }
}
```

``` kotlin
suspend fun getUserName(userId: Int): String = withContext(Dispatchers.IO) {
    delay(20000)
    return@withContext "Android 轮子哥"
}
```


### 协程的启动模式
介绍一下线程调度器的模式，总共有四种：

- CoroutineStart.DEFAULT：默认模式，会立即执行

- CoroutineStart.LAZY：懒加载模式，不会执行，只有手动调用协程的 start 方法才会执行

- CoroutineStart.ATOMIC：原子模式，跟 CoroutineStart.DEFAULT 类似，但协程在开始执行之前不能被取消，需要注意的是，这是一个实验性的 api，后面可能会发生变更。

- CoroutineStart.UNDISPATCHED：未指定模式，会立即执行协程，经过实践得出，会导致原先设置的线程调度器失效，一开始会在原来的线程上执行，类似于 `Dispatchers.Unconfined`，但是一旦协程被挂起，再恢复执行，会变成线程调度器的设置的线程上面去执行。


- 说了那么多，那么到底怎么用呢？拿 CoroutineStart.LAZY 举例，具体用法如下：

``` kotlin
val job = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {

}
job.start()
```

这里额外这里介绍一下协程几个函数的用法：

- job.start：启动协程，除了 lazy 模式，协程都不需要手动启动

- job.cancel：取消一个协程，可以取消，但是不会立马生效，存在一定延迟

- job.join：等待协程执行完毕，这是一个耗时操作，需要在协程中使用

- job.cancelAndJoin：等待协程执行完毕然后再取消




### 协程设置执行超时
协程在执行的时候，我们其实可以给它设置一个执行时间，如果执行的耗时时间超过规定的时间，那么协程就会自动停止，具体的用法如下

``` kotlin
GlobalScope.launch() {
    try {
        withTimeout(300) {
            // 重复执行 5 次里面的内容
            repeat(5) { i ->
                println("测试输出 " + i)
                delay(100)
            }
        }
    } catch (e: TimeoutCancellationException) {
        // TimeoutCancellationException 是 CancellationException 的子类
        // 其它的不用我说了吧？文章前面有介绍 CancellationException 类作用的
        println("测试协程超时了")
    }
}
```

``` console
23:52:48.415 System.out: 测试输出 0
23:52:48.518 System.out: 测试输出 1
23:52:48.618 System.out: 测试输出 2
23:52:48.715 System.out: 测试协程超时了
```


除了 withTimeout 这个用法，还有另外一个用法，那就是 withTimeoutOrNull，这个和 withTimeout 最大的不同的是不会超时之后不会抛 TimeoutCancellationException 给协程，而是直接返回 null，如果没有超时则会返回协程体里面的结果，具体用法如下：

``` kotlin
GlobalScope.launch() {
    val result = withTimeoutOrNull(300) {
        // 重复执行 5 次里面的内容
        repeat(5) { i ->
            println("测试输出 " + i)
            delay(100)
        }
        return@withTimeoutOrNull "执行完成了"
    }
    println("测试输出结果 " + result)
}
```

``` console
23:56:02.462 System.out: 测试输出 0
23:56:02.569 System.out: 测试输出 1
23:56:02.670 System.out: 测试输出 2
23:56:02.761 System.out: 测试输出结果 null
```



### 协程的生命周期控制
通过**GlobalScope** 、**CoroutineScope** 开启的协程是全局的，也就是不会跟随组件（例如 Activity）的生命周期

那么为了解决这一问题，Jetpack 中其实有提供关于 Kotlin 协程的一些扩展组件，例如 LifecycleScope 和 ViewModelScope，集成的方式如下：

``` groovy
dependencies {
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.3.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1'
}
```


如果是在 LifecycleOwner 的子类（AppCompatActivity 和 Fragment 都是它的子类）中使用，这样写出来的协程会在 Lifecycle 派发 destroy 事件的时候 cancel 掉

``` kotlin
class TestActivity : AppCompatActivity() {

    fun test() {
    
        lifecycleScope.launch {
            
        }
    }
}
```

如果是在 ViewModel 的子类中使用，这样写出来的协程会在 ViewModel 调用 clear 方法的时候 cancel 掉

``` kotlin
class TestViewModel : ViewModel() {
    
    fun test() {
    
        viewModelScope.launch() {

        }
    }
}
```