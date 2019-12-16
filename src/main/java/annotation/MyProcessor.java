package annotation;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.function.Consumer;

@SupportedAnnotationTypes({"annotation.PrintMe"})
public class MyProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Messager messager = processingEnv.getMessager();
        annotations.forEach((Consumer<TypeElement>) typeElement -> {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(typeElement);
            elements.forEach((Consumer<Element>) element -> {
                messager.printMessage(Diagnostic.Kind.NOTE, element.toString());
            });
        });
        return false;
    }
}
