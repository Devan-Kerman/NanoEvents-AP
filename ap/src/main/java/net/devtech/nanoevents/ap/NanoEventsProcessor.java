package net.devtech.nanoevents.ap;

import net.devtech.nanoevents.annotations.Hook;
import net.devtech.nanoevents.annotations.Listener;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NanoEventsProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) return true;
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "NanoEvents Annotation processor running!");
        roundEnv.getElementsAnnotatedWith(Hook.class).forEach(e -> {
            Hook hook = e.getAnnotation(Hook.class);

        });
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> strings = new HashSet<>();
        for (Class<?> cls : new Class[]{Hook.class, Listener.class}) {
            strings.add(cls.getName());
        }
        return strings;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }


}
