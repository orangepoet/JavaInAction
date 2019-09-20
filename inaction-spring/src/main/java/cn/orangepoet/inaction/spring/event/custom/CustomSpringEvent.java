package cn.orangepoet.inaction.spring.event.custom;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author chengzhi
 * @date 2019/09/20
 */
@Getter
public class CustomSpringEvent extends ApplicationEvent {
    private final String message;

    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
