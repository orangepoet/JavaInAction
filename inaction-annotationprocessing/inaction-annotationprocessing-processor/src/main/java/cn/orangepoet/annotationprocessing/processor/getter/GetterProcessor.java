package cn.orangepoet.annotationprocessing.processor.getter;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
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
@AutoService(Processor.class)
@SupportedAnnotationTypes("cn.orangepoet.annotationprocessing.processor.getter.Getter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class GetterProcessor extends AbstractProcessor {
    private JavacTrees trees;
    private Messager messager;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        trees = JavacTrees.instance(processingEnv);
        JavacProcessingEnvironment javacProcessingEnvironment = (JavacProcessingEnvironment)processingEnv;
        Context context = javacProcessingEnvironment.getContext();
        treeMaker = TreeMaker.instance(context);
        messager = processingEnv.getMessager();
        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.WARNING, "GetterProcessor");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : elements) {
                JCTree jcTree = trees.getTree(element);
                //jcTree.accept(new JCTree.Visitor() {
                //    @Override
                //    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                //        //List<JCTree.JCVariableDecl> jcVariableDecls = List.nil();
                //        //
                //        //for (JCTree tree : jcClassDecl.defs) {
                //        //    if (tree.getKind() == Tree.Kind.VARIABLE) {
                //        //        jcVariableDecls.add((JCTree.JCVariableDecl)tree);
                //        //    }
                //        //}
                //        //
                //        //for (JCTree.JCVariableDecl jcVariableDecl : jcVariableDecls) {
                //        //    messager.printMessage(Diagnostic.Kind.NOTE,
                //        //        jcVariableDecl.getName() + " has been processed");
                //        //    jcClassDecl.defs = jcClassDecl.defs.prepend(makeGetterMethodDecl(jcVariableDecl));
                //        //}
                //
                //        super.visitClassDef(jcClassDecl);
                //    }
                //});
                jcTree.accept(new TreeTranslator() {
                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                        try {
                            List<JCTree.JCVariableDecl> jcVariableDecls = List.nil();

                            for (JCTree tree : jcClassDecl.defs) {
                                if (tree.getKind() == Tree.Kind.VARIABLE) {
                                    jcVariableDecls = jcVariableDecls.append((JCTree.JCVariableDecl)tree);
                                }
                            }

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

    private JCTree makeGetterMethodDecl(JCTree.JCVariableDecl jcVariableDecl) {
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();
        statements.append(
            treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.getName()))
        );
        JCTree.JCBlock body = treeMaker.Block(0, statements.toList());
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC), getNewMethodName(jcVariableDecl.getName()),
            jcVariableDecl.vartype, List.nil(), List.nil(), List.nil(), body, null);
    }

    private Name getNewMethodName(Name name) {
        String s = name.toString();
        return names.fromString("get" + s.substring(0, 1).toUpperCase() + s.substring(1));
    }
}
