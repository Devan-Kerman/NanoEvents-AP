package net.devtech.nanoevents.ap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * an annotation for declaring an invoker for the AP to auto-complete
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Hook {
	/**
	 *
	 */
	Class<?> value();
}
