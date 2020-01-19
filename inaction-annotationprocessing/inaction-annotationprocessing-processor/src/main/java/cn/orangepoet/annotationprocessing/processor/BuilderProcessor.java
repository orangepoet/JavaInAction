package cn.orangepoet.annotationprocessing.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chengzhi
 * @date 2020/01/17
 */
@SupportedAnnotationTypes("cn.orangepoet.annotationprocessing.processor.BuilderProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            for (TypeElement annotation : annotations) {
                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

                Map<Boolean, List<Element>> annotatedMethods = annotatedElements
                    .stream()
                    .collect(Collectors.partitioningBy(element ->
                        ((ExecutableType)element.asType()).getParameterTypes().size() == 1
                            && element.getSimpleName().toString().startsWith("set")));
                List<Element> setters = annotatedMethods.get(true);
                List<Element> otherMethods = annotatedMethods.get(false);

                if (setters.isEmpty()) {
                    continue;
                }

                otherMethods.forEach(element ->
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "@BuilderProperty must be applied to a setXxx method "
                            + "with a single argument", element));

                String className = ((TypeElement)setters.get(0)
                    .getEnclosingElement()).getQualifiedName().toString();

                String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
                String packageName = className.substring(0, className.lastIndexOf("."));

                Map<String, String> setterMap = setters.stream().collect(Collectors.toMap(
                    setter -> setter.getSimpleName().toString(),
                    setter -> ((ExecutableType)setter.asType())
                        .getParameterTypes().get(0).toString()
                ));

                Class<?> classType = Class.forName(className);
                FieldSpec instance = FieldSpec.builder(classType, "instance", Modifier.PUBLIC).build();

                TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className + "Builder")
                    .addField(instance);

                // build method
                typeSpecBuilder.addMethod(MethodSpec.methodBuilder("build").returns(classType).build());

                // fluent set method
                setterMap.entrySet().forEach(entry -> {
                    //entry.get
                    try {
                        MethodSpec methodSpec = MethodSpec.methodBuilder(entry.getValue())
                            .addCode(String.format("this.instance.setAge(age);"))
                            .returns(Class.forName(entry.getKey()))
                            .addModifiers(Modifier.PUBLIC)
                            .build();
                        typeSpecBuilder.addMethod(methodSpec);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                JavaFile javaFile = JavaFile.builder(packageName, typeSpecBuilder.build()).build();
                javaFile.writeTo(this.filer);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
