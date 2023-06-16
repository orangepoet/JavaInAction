package cn.orangepoet.inaction.spring.event

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class CustomSpringEventListener1 : ApplicationListener<CustomSpringEvent> {
    private val log = LoggerFactory.getLogger(CustomSpringEventListener1::class.java)
    override fun onApplicationEvent(event: CustomSpringEvent) {
        log.info("CustomSpringEventListener1 Received spring custom event - " + event.message)
        log.info("threadId: " + Thread.currentThread().id)
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        throw RuntimeException("failed")
    }
}

@Component
class CustomSpringEventListener2 : ApplicationListener<CustomSpringEvent> {
    private val log = LoggerFactory.getLogger(CustomSpringEventListener2::class.java)
    override fun onApplicationEvent(event: CustomSpringEvent) {
        log.info("CustomSpringEventListener2 Received spring custom event - " + event.message)
        log.info("threadId: " + Thread.currentThread().id)
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        throw java.lang.RuntimeException("failed")
    }
}

@Component
open class CustomSpringEventListener3 {
    private val log = LoggerFactory.getLogger(CustomSpringEventListener3::class.java)

    @Async
    @EventListener
    open fun handleContextStart(event: CustomSpringEvent) {
        log.info("CustomSpringEventListener3 Received spring custom event - " + event.message)
        log.info("threadId: " + Thread.currentThread().id)
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        //throw new RuntimeException("failed");
    }
}

@Component
open class CustomSpringEventListener4 {
    private val log = LoggerFactory.getLogger(CustomSpringEventListener4::class.java)

    @Async
    @EventListener
    open fun handleContextStart(event: CustomSpringEvent) {
        log.info("CustomSpringEventListener4 Received spring custom event - " + event.message)
        log.info("threadId: " + Thread.currentThread().id)
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        throw java.lang.RuntimeException("failed")
    }
}