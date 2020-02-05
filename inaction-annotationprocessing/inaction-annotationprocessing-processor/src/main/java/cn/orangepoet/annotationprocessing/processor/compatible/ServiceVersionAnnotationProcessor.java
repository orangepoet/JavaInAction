package cn.orangepoet.annotationprocessing.processor.compatible;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 生成服务类型的代理类, 基于spring方式实现
 *
 * @Component
 * @Primary public class CustomerProxy implements CustomService {
 * <p>
 * }
 */
@AutoService(Processor.class)
public class ServiceVersionAnnotationProcessor extends AbstractProcessor {
    private static final String SUFFIX = "GeneratedProxy";


    private Messager messager;
    private Filer filer;
    private Elements elements;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(ServiceVersion.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elements = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Set<TypeElement> serviceTypes = new LinkedHashSet<>();
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(ServiceVersion.class)) {
                ServiceVersionCtx ctx = new ServiceVersionCtx(annotatedElement, elements);
                serviceTypes.add(ctx.getServiceType());
            }

            for (TypeElement serviceType : serviceTypes) {
                generateCode(serviceType);
            }
        } catch (ServiceVersionProcessException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.getElement());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        } finally {
        }
        return true;
    }

    /**
     * 生成ServiceProxy代码
     *
     * @param serviceType
     */
    private void generateCode(TypeElement serviceType) throws IOException {
        PackageElement pkgElement = elements.getPackageOf(serviceType);
        String packageName = pkgElement.getQualifiedName().toString();

        String serviceName = serviceType.getSimpleName().toString();
        String proxyClassName = serviceName + SUFFIX;

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(proxyClassName)
                .addAnnotation(Component.class)
                .addAnnotation(Primary.class)
                .addSuperinterface(ClassName.get(serviceType));

        // serviceMap field
        ParameterizedTypeName serviceVersionMapType = ParameterizedTypeName.get(ClassName.get(Map.class), TypeName.get(serviceType.asType()), ClassName.get(ServiceVersion.class));
        FieldSpec.Builder serviceMap = FieldSpec.builder(serviceVersionMapType, "serviceMap")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL);

        classBuilder.addField(serviceMap.build());

        // constructor
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(serviceType)), "services")
                .addStatement("this.serviceMap = getServiceMap(services);");

        classBuilder.addMethod(constructor.build());

        // private getServiceMap
        MethodSpec.Builder getServiceMapMethod = MethodSpec.methodBuilder("getServiceMap")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(serviceType)), "services")
                .returns(serviceVersionMapType)
                .beginControlFlow("if (services == null || services.isEmpty())")
                .addStatement("return $T.emptyMap()", Collections.class)
                .endControlFlow()
                .addStatement("Map<$T, ServiceVersion> result = new $T<>()", serviceType, HashMap.class)
                .beginControlFlow("for ($T service : services)", serviceType)
                .addStatement("ServiceVersion serviceVersion = service.getClass().getAnnotation(ServiceVersion.class)")
                .beginControlFlow(" if (serviceVersion != null) ")
                .addStatement("result.put(service, serviceVersion)")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return result");
        classBuilder.addMethod(getServiceMapMethod.build());

        // interface methods
        List<? extends Element> enclosedElements = serviceType.getEnclosedElements();
        for (Element e : enclosedElements) {
            if (e.getKind() != ElementKind.METHOD) {
                continue;
            }
            ExecutableElement ee = (ExecutableElement) e;
            MethodSpec.Builder method = MethodSpec.methodBuilder(ee.getSimpleName().toString())
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.get(ee.getReturnType()));

            List<String> parameterList = new ArrayList<>();
            for (VariableElement ve : ee.getParameters()) {
                String pn = ve.getSimpleName().toString();
                method.addParameter(TypeName.get(ve.asType()), pn);
                parameterList.add(pn);
            }

            method.addStatement("$T service = $T.getService(serviceMap)", serviceType, ServiceFactory.class);

            if (ee.getReturnType().getKind() == TypeKind.VOID) {
                method.addStatement("service.$N($L)", ee.getSimpleName().toString(), String.join(",", parameterList));
            } else {
                method.addStatement("return service.$N($L)", ee.getSimpleName().toString(), String.join(",", parameterList));
            }
            classBuilder.addMethod(method.build());
        }

        JavaFile.builder(packageName, classBuilder.build()).indent("    ").build().writeTo(filer);
    }

    private static class ServiceVersionCtx {
        private TypeElement annotationClass;
        private TypeElement serviceType;

        public ServiceVersionCtx(Element element, Elements elements) {
            if (element.getKind() != ElementKind.CLASS) {
                throw new ServiceVersionProcessException(element, "Only classes can be annotated with @%s",
                        ServiceVersion.class.getSimpleName());
            }
            this.annotationClass = (TypeElement) element;

            String serviceName;
            try {
                ServiceVersion serviceVersion = annotationClass.getAnnotation(ServiceVersion.class);
                serviceName = serviceVersion.serviceType().getCanonicalName();
            } catch (MirroredTypeException e) {
                DeclaredType classTypeMirror = (DeclaredType) e.getTypeMirror();
                TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
                serviceName = classTypeElement.getQualifiedName().toString();
            }
            TypeElement serviceType = elements.getTypeElement(serviceName);
            if (serviceType.getKind() != ElementKind.INTERFACE) {
                throw new ServiceVersionProcessException(annotationClass, "serviceType: %s is not interface", serviceType.getSimpleName());
            }
            this.serviceType = serviceType;
            //check();
        }

        @Deprecated
        private void check() {
            if (!annotationClass.getModifiers().contains(Modifier.PUBLIC)) {
                throw new ServiceVersionProcessException(annotationClass, "The class %s is not public.",
                        annotationClass.getQualifiedName().toString());
            }

            // Check if it's an abstract class
            if (annotationClass.getModifiers().contains(Modifier.ABSTRACT)) {
                throw new ServiceVersionProcessException(annotationClass,
                        "The class %s is abstract. You can't annotate abstract classes with @%",
                        annotationClass.getQualifiedName().toString(), ServiceVersion.class.getSimpleName());
            }

            // Check inheritance: Class must be childclass as specified in @ServiceVersion.serviceType();
            if (!annotationClass.getInterfaces().contains(this.serviceType.asType())) {
                throw new ServiceVersionProcessException(annotationClass,
                        "The class %s annotated with @%s must implement the interface %s",
                        annotationClass.getQualifiedName().toString(), ServiceVersion.class.getSimpleName(),
                        this.serviceType.getSimpleName().toString());
            }
        }

        /**
         * Get the full QualifiedTypeName
         *
         * @param serviceType
         * @return
         */
        private CharSequence getServiceName(Class<?> serviceType) {
            //
            try {
                return serviceType.getCanonicalName();
            } catch (MirroredTypeException mte) {
                DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
                return classTypeElement.getQualifiedName().toString();
            }
        }

        public TypeElement getAnnotationClass() {
            return annotationClass;
        }

        public TypeElement getServiceType() {
            return serviceType;
        }
    }
}
