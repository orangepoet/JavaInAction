package cn.orangepoet.inaction.spring.event

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class CustomSpringEventPublisher(var applicationEventPublisher: ApplicationEventPublisher) {
    private val log = LoggerFactory.getLogger(CustomSpringEventPublisher::class.java)

    fun doStuffAndPublishEvent(message: String) {
        val event = CustomSpringEvent(this, message)
        applicationEventPublisher.publishEvent(event)
        log.info("threadId: " + Thread.currentThread().id)
    }
}