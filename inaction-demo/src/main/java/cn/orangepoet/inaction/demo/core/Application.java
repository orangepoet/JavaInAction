package cn.orangepoet.inaction.demo.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author chengzhi
 * @date 2020/01/20
 */
public class Application {
    public static void main(String[] args) {
        try {
            System.out.println(Action.A.ordinal());

            File jarFile = new File(
                "/Users/orangecheng/codes/JavaInAction/inaction-annotationprocessing/inaction-annotationprocessing"
                    + "-processor/target/inaction-annotationprocessing-processor-1.0.0-SNAPSHOT.jar");
            URL url = jarFile.toURI().toURL();
            MyClassLoader myClassLoader = new MyClassLoader(new URL[] {url});

            Class<?> clazz = myClassLoader.loadClass("cn.orangepoet.annotationprocessing.processor.BuilderProcessor");
            System.out.println(clazz == null ? "load filed" : "load success");
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    enum Action {
        A,
        B,
        C
    }
}
