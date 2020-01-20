package cn.orangepoet.inaction.demo.core;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author chengzhi
 * @date 2020/01/20
 */
public class MyClassLoader extends URLClassLoader {
    public MyClassLoader(URL[] urls) {
        super(urls);
    }
}
