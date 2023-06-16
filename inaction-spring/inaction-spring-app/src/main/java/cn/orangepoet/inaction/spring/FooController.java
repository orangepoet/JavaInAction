package cn.orangepoet.inaction.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foo")
public class FooController {
    @GetMapping("/{name}")
    public String hello(@PathVariable("name") String name) {
        return "hello, " + name;
    }
}
