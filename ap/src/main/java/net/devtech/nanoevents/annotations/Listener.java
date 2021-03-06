package net.devtech.nanoevents.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * an annotation for declaring a listener
 * additional parameters may be declared by adding more annotations
 * the documentation should cover this
 */
@Target(ElementType.METHOD)
public @interface Listener {
	/**
	 * the id of the event
	 */
	String value();

	/**
	 * todo fix this ugly hack!
	 * the parameter annotations for the event, you must decorate your method with the annotations of the types provided in this array
	 */
	Class<? extends Annotation>[] args() default {};
}
