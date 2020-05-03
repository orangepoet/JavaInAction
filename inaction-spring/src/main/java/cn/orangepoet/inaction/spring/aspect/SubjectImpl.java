package cn.orangepoet.inaction.spring.aspect;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author orangecheng
 */
@Component
public class SubjectImpl implements Subject {
    private Logger logger = LoggerFactory.getLogger(Subject.class);
    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        Preconditions.checkNotNull(observer);
        observerList.add(observer);
    }

    @Override
    public void onMessage() {
        for (Observer observer : observerList) {
            logger.info("result={}", observer.update());
        }
    }

    void onMessage1() {

    }
}
