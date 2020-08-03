package net.devtech.nanoevents.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.devtech.nanoevents.api.event.EventApplyManager;

/**
 * an annotation for declaring an invoker for the AP to auto-complete
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Hook {
	/**
	 * @return the id of the event
	 */
	String value();

	/**
	 * the event apply manager for this invoker
	 */
	Class<? extends EventApplyManager> applyManager() default EventApplyManager.Default.class;
}
