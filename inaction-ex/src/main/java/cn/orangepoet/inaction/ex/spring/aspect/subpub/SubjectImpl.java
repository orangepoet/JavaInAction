package cn.orangepoet.inaction.ex.spring.aspect.subpub;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author orangecheng
 */
@Component
public class SubjectImpl implements Subject {
    private final Logger logger = LoggerFactory.getLogger(Subject.class);
    private final List<Observer> observerList = new ArrayList<>();

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
