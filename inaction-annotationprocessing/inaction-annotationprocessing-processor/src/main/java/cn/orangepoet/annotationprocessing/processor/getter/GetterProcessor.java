package cn.orangepoet.annotationprocessing.processor.getter;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

/**
 * @author chengzhi
 * @date 2020/03/28
 */
@SupportedAnnotationTypes("cn.orangepoet.annotationprocessing.processor.getter.Getter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class GetterProcessor extends AbstractProcessor {
    private AtomicInteger rounds = new AtomicInteger();

    private JavacTrees trees;
    private Messager messager;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        JavacProcessingEnvironment javacProcessingEnvironment = (JavacProcessingEnvironment)processingEnv;
        Context context = javacProcessingEnvironment.getContext();

        // JSR269的一个工具类，用于联系程序元素和树节点
        trees = JavacTrees.instance(processingEnv);

        // 编译器的内部组件，是用于创建树节点的工厂类
        treeMaker = TreeMaker.instance(context);

        // message reporter: 在注解处理过程中打印消息的
        messager = processingEnv.getMessager();

        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
            "GetterProcessor process, round: " + rounds.incrementAndGet());

        for (Element rootElement : roundEnv.getRootElements()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "GetterProcessor root elements",
                rootElement);
        }

        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : elements) {
                JCTree jcTree = trees.getTree(element);

                // JCTree利用的是访问者模式，将数据与数据的处理进行解耦，TreeTranslator就是访问者，这里我们重写访问类时的逻辑
                jcTree.accept(new TreeTranslator() {
                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                        try {

                            // 获取成员变量列表
                            List<JCTree.JCVariableDecl> jcVariableDecls = List.nil();
                            for (JCTree tree : jcClassDecl.defs) {
                                if (tree.getKind() == Tree.Kind.VARIABLE) {
                                    jcVariableDecls = jcVariableDecls.append((JCTree.JCVariableDecl)tree);
                                }
                            }

                            //对每个成员变量生成相应的Getter方法
                            jcVariableDecls.forEach(jcVariableDecl -> {
                                messager.printMessage(Diagnostic.Kind.NOTE,
                                    jcVariableDecl.getName() + " has been processed");
                                jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                            });
                        } catch (Exception e) {
                            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                        }
                        super.visitClassDef(jcClassDecl);
                    }
                });
            }
        }
        return true;
    }

    /**
     * 生成Getter方法的语法树节点
     *
     * @param jcVariableDecl
     * @return
     */
    private JCTree makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(
            treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()))
        );
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName()),
            jcVariableDecl.vartype, List.nil(), List.nil(), List.nil(), body, null);
    }

    /**
     * Getter方法名
     *
     * @param name
     * @return
     */
    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1));
    }
}
