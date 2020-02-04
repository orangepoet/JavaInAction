package cn.orangepoet.annotationprocessing.processor.compatible;

import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("cn.orangepoet.annotationprocessing.processor.compatible.ServiceVersion")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
@Slf4j
public class ServiceVersionAnnotationProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            // collect concrete classes, group by type;
            for (Element element : annotatedElements) {
                TypeElement typeElement = (TypeElement) element;
                Class<?>[] interfaces = typeElement.getClass().getInterfaces();
//                if (!ArrayUtils.contains(interfaces, ServiceVersion.class)) {
//                    continue;
//                }
//                TypeSpec.Builder typeSpecBuilder = TypeSpec
//                        .classBuilder(builderType)
//                        .addField(
//                                FieldSpec.builder(typeName, "instance", Modifier.PRIVATE, Modifier.FINAL)
//                                        .initializer("new " + simpleClassName + "()").build())
//                        .addMethod(
//                                MethodSpec.methodBuilder("build").addModifiers(Modifier.PUBLIC)
//                                        .addCode(String.format("return this.instance;%n"))
//                                        .returns(typeName).build());
//                JavaFile javaFile = JavaFile.builder("cn.orangepoet.annotationprocessing.processor.handler", typeSpecBuilder.build()).indent("    ").build();
//                javaFile.writeTo(this.filer);

            }
        }


        return false;
    }
}
