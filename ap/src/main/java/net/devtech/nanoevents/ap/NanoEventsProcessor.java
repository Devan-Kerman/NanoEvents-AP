package net.devtech.nanoevents.ap;

import net.devtech.nanoevents.annotations.Listener;
import net.devtech.nanoevents.api.annotations.Invoker;
import org.ini4j.Ini;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NanoEventsProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) return true;
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "NanoEvents Annotation processor running!");
        // class name -> ini file
        Map<Element, Ini> eventConfigs = new HashMap<>();
        Map<Element, Ini> listenerConfigs = new HashMap<>();
        for (TypeElement annotation : annotations) {
            if(annotation.getQualifiedName().contentEquals(Invoker.class.getCanonicalName())) {
                HookHandler.handle(this.processingEnv, roundEnv, eventConfigs);
            } else if(annotation.getQualifiedName().contentEquals(Listener.class.getCanonicalName())) {
                ListenerHandler.handle(roundEnv, listenerConfigs);
            }
        }

        eventConfigs.forEach((e, i) -> this.makeIni("nanoevents.evt", e, i));
        listenerConfigs.forEach((e, i) -> this.makeIni("nanoevents.lst", e, i));
        return true;
    }

    private void makeIni(String pkg, Element cls, Ini ini) {
        try {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating '" + pkg + '/' + cls + ".ini'");
            FileObject object = this.processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, pkg, cls + ".ini", cls);
            ini.store(object.openOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> strings = new HashSet<>();
        for (Class<?> cls : new Class[]{Invoker.class, Listener.class}) {
            strings.add(cls.getName());
        }
        return strings;
    }
}
