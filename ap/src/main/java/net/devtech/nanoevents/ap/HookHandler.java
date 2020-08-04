package net.devtech.nanoevents.ap;

import net.devtech.nanoevents.api.annotations.Invoker;
import org.ini4j.Ini;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import java.util.Map;

public class HookHandler {
	public static void handle(ProcessingEnvironment environment, RoundEnvironment roundEnv, Map<Element, Ini> hooks) {
		for (Element method : roundEnv.getElementsAnnotatedWith(Invoker.class)) {
			handle(environment, method, hooks);
		}
	}

	private static void handle(ProcessingEnvironment environment, Element method, Map<Element, Ini> hooks) {
		Invoker invoker = method.getAnnotation(Invoker.class);
		Ini ini = hooks.computeIfAbsent(method.getEnclosingElement(), s -> new Ini());
		String eventId = invoker.value();
		int colon = eventId.indexOf(':');
		if (colon == -1 || colon != eventId.lastIndexOf(':')) {
			throw new IllegalArgumentException(method.getEnclosingElement() + "#" + method + " has invalid id: '" + eventId + "'!");
		}

		String applier = "null";
		try {
			invoker.applyManager();
		} catch (MirroredTypeException e) {
			applier = Utils.getClassName(environment.getElementUtils().getTypeElement(String.valueOf(e.getTypeMirror())));
		}
		ini.put(eventId, "applier", applier);
		ini.put(eventId, "invoker", Utils.getClassName(method.getEnclosingElement()));

		ListenerHandler.load(method, invoker::args, ini, eventId);
	}

}
