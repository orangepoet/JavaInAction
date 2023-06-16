package cn.orangepoet.inaction.spring.event

import org.springframework.context.ApplicationEvent

class CustomSpringEvent(source: Any, val message: String) : ApplicationEvent(source)


