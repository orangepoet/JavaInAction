package cn.orangepoet.inaction.spring.base.aspect.traffic;

/**
 * Created by Orange on 2017/1/11.
 */
public interface Traffic {
    void go();

    /**
     * Created by Orange on 2017/1/11.
     */
    enum TrafficWay {
        UNKNOWN, FLIGHT_WAY, TRAIN_WAY
    }
}
