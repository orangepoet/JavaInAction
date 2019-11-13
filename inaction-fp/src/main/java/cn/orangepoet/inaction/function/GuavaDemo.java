package cn.orangepoet.inaction.function;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author chengzhi
 * @date 2019/09/24
 */
public class GuavaDemo {
    public static void main(String[] args) {
        Long[] taskIds = new Long[] {1L, 2L, 3L};
        List<Long> lst = Lists.newArrayList(taskIds);
        //lst.add(1L);
        //lst.add(2L);
        //lst.add(3L);
        String partions = Joiner.on(",").join(lst);
        System.out.println(partions);
    }
}
