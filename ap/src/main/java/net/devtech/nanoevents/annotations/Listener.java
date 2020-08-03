package net.devtech.nanoevents.annotations;

/**
 * an annotation for declaring a listener
 * additional parameters may be declared by adding more annotations
 * the documentation should cover this
 */
public @interface Listener {
	/**
	 * the id of the event
	 */
	String value();
}
