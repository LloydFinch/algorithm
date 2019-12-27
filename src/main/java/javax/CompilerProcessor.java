package javax;

import javax.annotation.processing.*;
import javax.bean.ParseBean;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * 编译期注解扫描处理器
 */
public class CompilerProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Elements mElements;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.mMessager = processingEnv.getMessager();
        this.mElements = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();

        mMessager.printMessage(Diagnostic.Kind.NOTE, "init......");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "process......");

        //获取指定类型的注解集合
        Set<? extends Element> annotationSet = roundEnv.getElementsAnnotatedWith(ParseBean.class);
        for (Element element : annotationSet) {
            //这里的注解是定义在成员变量之伤的，转换成成员变量
            VariableElement variableElement = (VariableElement) element;
            //获取包裹这个注解的元素，这里的注解是成员变量，包裹它的就是类
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String className = classElement.getQualifiedName().toString();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "class name is " + className);

            //获取指定类型的注解
            ParseBean annotation = variableElement.getAnnotation(ParseBean.class);
            //获取注解的值
            int value = annotation.value();
            mMessager.printMessage(Diagnostic.Kind.NOTE, "ParseBean value is " + value);
            //根据类名和TypeElement构造文件
            try {
                JavaFileObject sourceFile = mFiler.createSourceFile(className, classElement);
                Writer writer = sourceFile.openWriter();
                writer.write("public static void main(String args[]) {System.out.println()}");

                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        /**
         * super尝试获取SupportedAnnotationTypes注解，有就取数组，否则返回空表
         */
        Set<String> supports = new HashSet<>();
        supports.add(ParseBean.class.getCanonicalName());
        return supports;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        /**
         * super的实现：尝试获取{@link SupportedSourceVersion} 注解，有就取注解值，
         * 否则就返回{@link SourceVersion.RELEASE_6}
         */
        return SourceVersion.latestSupported();
    }
}
