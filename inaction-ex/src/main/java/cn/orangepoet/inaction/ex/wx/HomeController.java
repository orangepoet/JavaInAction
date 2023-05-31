package cn.orangepoet.inaction.ex.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/")
public class HomeController {
    @Autowired
    private MpService mpService;

    @RequestMapping("/sync")
    public Mono<Double> getSyncProc() {
        return mpService.getSyncProgress("mockAppid");
    }

}
