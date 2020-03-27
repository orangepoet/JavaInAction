package cn.orangepoet.inaction.spring.samples;

import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2020/03/25
 */
@Component
public class FetchStudent implements FetchData<StudentData> {
    @Override
    public StudentData fetch() {
        return new StudentData();
    }
}
