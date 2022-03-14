package cn.orangepoet.inaction.parallel.ttl

import com.alibaba.ttl.TransmittableThreadLocal
import kotlinx.coroutines.*
import mu.KotlinLogging

/**
 * @author chengzhi
 * @date 2022/03/11
 */
val log = KotlinLogging.logger {}

fun main() {
//    testMultipleThread()
    testCorutine()
}

private fun testMultipleThread() {
    val ttl: TransmittableThreadLocal<String?> = object : TransmittableThreadLocal<String?>() {
        init {
            set("hello, world")
        }

        override fun afterExecute() {
            remove()
        }
    }

    val tl = ThreadLocal<String>()
    with(tl) {
        set("minemine")
    }

    val t = Thread {
        log.info("tl1: {}", ttl.get())
        log.info("tl2: {}", tl.get())
    }
    t.start()

    try {
        t.join()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    log.info("end, ttl: {}", ttl.get())
}

fun testCorutine() {
    val ttl: TransmittableThreadLocal<String?> = object : TransmittableThreadLocal<String?>() {
        init {
            set("hello, world")
        }

        override fun afterExecute() {
            remove()
        }
    }

    val tl = ThreadLocal<String>()
    with(tl) {
        set("minemine")
    }

    runBlocking {
        repeat(100_000) {
            async { doJob() }
        }
    }
    log.info("end, thread-name:${Thread.currentThread().name}")
}

suspend fun doJob() = coroutineScope {
    launch {
        delay(1000)
    }
    log.info("finish job, thread-name:${Thread.currentThread().name}")
}
