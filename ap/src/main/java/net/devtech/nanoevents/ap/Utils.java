package net.devtech.nanoevents.ap;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.Map;

public class Utils {
	public static AnnotationMirror getAnnotationMirror(Element typeElement, TypeMirror clazz) {
		String clazzName = clazz.toString();
		for(AnnotationMirror m : typeElement.getAnnotationMirrors()) {
			if(m.getAnnotationType().toString().equals(clazzName)) {
				return m;
			}
		}
		return null;
	}

	public static String getClassName(Element element) {
		if(element.getEnclosingElement() instanceof TypeElement) {
			String name = element.toString();
			int index = name.lastIndexOf('.');
			return name.substring(0, index) + '$' + name.substring(index + 1);
		}
		return element.toString();
	}
}
