import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {

    Thread(Runnable {
        Thread.sleep(3000)
        println("Thread run end")
    }).start()


    runBlocking {
        val result = performTaskInThread()
        println("result=$result")
    }

    println("all run end")


}


// 在 suspend 函数中调用 Thread 执行任务，并将结果返回
suspend fun performTaskInThread(): String {
    return withContext(Dispatchers.Default) {
        var result = ""
        val thread = Thread {
            // 模拟耗时操作
            Thread.sleep(2000)
            result = "Task completed"
        }
        thread.start()
        thread.join()  // 等待线程执行完毕
        result  // 返回任务结果
    }
}