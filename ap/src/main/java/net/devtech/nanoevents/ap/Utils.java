package net.devtech.nanoevents.ap;

import javax.lang.model.element.*;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;

public class Utils {
	public static String getDesc(ExecutableElement method) {
		StringBuilder builder = new StringBuilder("(");
		for (VariableElement param : method.getParameters()) {
			builder.append(getDesc(param.asType()));
		}
		builder.append(')');
		builder.append(getDesc(method.getReturnType()));
		return builder.toString();
	}

	public static String getDesc(TypeMirror mirror) {
		switch (mirror.getKind()) {
			case ARRAY:
				return '[' + getDesc(((ArrayType)mirror).getComponentType());
			case INT:
				return "I";
			case BYTE:
				return "B";
			case CHAR:
				return "C";
			case LONG:
				return "J";
			case VOID:
				return "V";
			case FLOAT:
				return "F";
			case SHORT:
				return "S";
			case NULL:
				return "Lnull;";
			case DOUBLE:
				return "D";
			case BOOLEAN:
				return "Z";
			case DECLARED:
				return 'L' + mirror.toString().replace('.', '/') + ';';
			default:
				throw new IllegalArgumentException("wat");
		}
	}


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
