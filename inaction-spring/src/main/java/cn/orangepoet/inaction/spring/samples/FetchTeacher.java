package cn.orangepoet.inaction.spring.samples;

import org.springframework.stereotype.Component;

/**
 * @author chengzhi
 * @date 2020/03/25
 */
@Component
public class FetchTeacher implements FetchData<Teacher> {
    @Override
    public Teacher fetch() {
        return new Teacher();
    }
}
