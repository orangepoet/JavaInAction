package cn.orangepoet.inaction.demo.core.reflection;

//import statements

import java.io.Serializable;

public class ClassgetAnnotatedInterfaceExample1 {

    public static void main(String[] args) {
        Class<?>[] interfaces = MysubClass.class.getInterfaces();
        System.out.println(interfaces);
//        for (AnnotatedType annotatedType : MysubClass.class.getAnnotatedInterfaces()) {
//            Type tp = annotatedType.getType();  //get annotated type
//            System.out.println(" Anotated Type :" + tp);
//
//            Annotation[] antn = annotatedType.getAnnotations(); //get annotations
//            System.out.println("Annotations     :" + Arrays.toString(antn));
//
//            Annotation[] decAntn = annotatedType.getDeclaredAnnotations(); //getdeclared annotations
//            System.out.println("Declared Annotations     :" + Arrays.toString(decAntn));
//        }
    }

    private interface MyInterface extends Serializable {

    }

    private static class MysubClass implements MyInterface {
    }
}
