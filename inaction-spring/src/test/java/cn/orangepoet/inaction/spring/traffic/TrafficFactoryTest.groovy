package cn.orangepoet.inaction.spring.traffic

import cn.orangepoet.inaction.spring.traffic.impl.Flight
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**
 * @author chengzhi* @date 2020/04/26
 */
@ContextConfiguration(classes = Application.class)
@SpringBootTest
class TrafficFactoryTest extends Specification {

    @Autowired
    private TrafficFactory factory;

    def "GetTraffic"() {
        when:
        def traffic = factory.getTraffic(TrafficWay.FLIGHT_WAY)

        then:
        traffic != null && traffic instanceof Flight
    }
}
