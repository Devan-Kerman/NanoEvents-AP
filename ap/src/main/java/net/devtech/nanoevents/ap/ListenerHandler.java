package net.devtech.nanoevents.ap;

import net.devtech.nanoevents.annotations.Listener;
import net.devtech.nanoevents.api.annotations.Name;
import org.ini4j.Ini;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import java.util.Map;
import java.util.function.Supplier;

public class ListenerHandler {
	public static void handle(RoundEnvironment environment, Map<Element, Ini> listeners) {
		for (Element method : environment.getElementsAnnotatedWith(Listener.class)) {
			handle(method, listeners);
		}
	}

	private static void handle(Element method, Map<Element, Ini> listeners) {
		if (!(method.getModifiers().contains(Modifier.STATIC) && method.getModifiers().contains(Modifier.PUBLIC))) {
			throw new IllegalArgumentException(method + " must be public and static!");
		}

		Listener listener = method.getAnnotation(Listener.class);
		String id = listener.value();
		int colon = id.indexOf(':');
		if (colon == -1 || colon != id.lastIndexOf(':')) {
			throw new IllegalArgumentException(method.getEnclosingElement() + "#" + method + " has invalid id: '" + id + "'!");
		}

		Ini ini = listeners.computeIfAbsent(method.getEnclosingElement(), s -> new Ini());
		ini.put(id, "listeners", Utils.getClassName(method.getEnclosingElement()) + "::" + method.getSimpleName());
		load(method, listener::args, ini, id);
	}

	public static void load(Element method, Supplier<Class<?>[]> paramGetter, Ini ini, String id) {
		try {
			paramGetter.get();
		} catch (MirroredTypesException e) {
			for (TypeMirror annotationClass : e.getTypeMirrors()) {
				AnnotationMirror anno = Utils.getAnnotationMirror(method, annotationClass);
				if (anno == null) continue;
				anno.getElementValues().forEach((m, v) -> {
					Name name = m.getAnnotation(Name.class);
					ini.put(id, name == null ? m.getSimpleName().toString() : name.value(), v.getValue().toString());
				});
			}
		}
	}


	private static boolean isClass(Class<?> cls) {
		while (cls.isArray()) cls = cls.getComponentType();
		return cls == Class.class;
	}
}
