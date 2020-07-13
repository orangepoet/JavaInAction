package cn.orangepoet.inaction.spring.base.aspect.sample.traffic

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

/** 使用Spock的示例, 结合Spring
 * @author chengzhi*
 * @date 2019/09/27
 */
@ContextConfiguration(classes = Application.class)
class TrafficTest extends Specification {

    @Autowired
    private TrafficFactory trafficFactory;

    def "test traffic go"() {
        expect:
        trafficFactory.getTraffic(x) == y

        where:
        x                     | y
        TrafficWay.FLIGHT_WAY | "Flight"
        TrafficWay.TRAIN_WAY  | "Train"
    }
}
