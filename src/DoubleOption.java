package menu;

import processing.core.*;

/* A MenuItem that stores a floating-point value.
 * Can have a minimum and a maximum value, as well as a default.
 */
public class DoubleOption extends MenuItem {

	// Increment for < and >
	static final double INCR_SMALL = 0.01;
	// Increment for , and .
	static final double INCR_MED = 0.1;
	// Increment for LEFT ARROW and RIGHT ARROW
	static final double INCR_BIG = 1;

	// The current value of this option
	private double value;
	// The minimum and maximum values
	private final double min, max;
	// initial value of the option to be used as a default when reset
	private final double defaultValue;

	/* Constructor.
	 * nameInit is the name of the option, valueInit is the initial (or default) value,
	 * minInit and maxInit are the minimum and maximum of the value. This option's
	 * value will not be permitted to exceed the maximum or go below the minimum.
	 */
	public DoubleOption(PApplet p, String nameInit, double valueInit, double minInit, double maxInit) {
		super(p, nameInit);
		value = valueInit;
		min = minInit;
		max = maxInit;
		defaultValue = valueInit;
	}

	/* Implementation of MenuItem.toString().
	 * Returns the string that should be displayed for this item in the menu.
	 */
	public String toString() {
		return name + " = " + String.format("%.2f", value);
	}

	/* Implementation of MenuItem.action().
	 * Perform some action on keypress and return true iff it did anything.
	 * 
	 * Available actions on a DoubleOption:
	 * LEFT ARROW and RIGHT ARROW: decrement or increment the value by one
	 * , and . : decrement or increment the value by 0.1
	 * < and > : decrement or increment the value by 0.01
	 * - and = : set the value to its min or its max, respectively
	 * ENTER/RETURN: reset the value to its initial/default value
	 * Forward Slash (/): round the value to the nearest integer.
	 * (if fractional part is 0.5, rounds up)
	 */
	protected boolean action(char key, int keyCode) {
		if (key == CODED) {
			if (keyCode == RIGHT)
				return add(INCR_BIG);
			else if (keyCode == LEFT)
				return add(-INCR_BIG);
		} else if (key == ENTER || key == RETURN)
			return set(defaultValue);
		else if (key == '-')
			return set(min);
		else if (key == '=')
			return set(max);
		else if (key == ',')
			return add(-INCR_MED);
		else if (key == '.')
			return add(INCR_MED);
		else if (key == '<')
			return add(-INCR_SMALL);
		else if (key == '>')
			return add(INCR_SMALL);
		else if (key == '/')
			return roundValue();
		return false;
	}

	/* Returns the current value of this option. */
	public double get() {
		return value;
	}

	/* Set the value of this option and return true iff the value changed. */
	public boolean set(double toSet) {
		double oldValue = value;
		value = Math.max(Math.min(toSet, max), min);
		return oldValue != value;
	}

	/* Increment the value by some amount. May be negative. 
	 * Returns true iff the value changed.
	 */
	public boolean add(double toAdd) {
		return set(get() + toAdd);
	}

	/* Round the value to the nearest integer. Returns true iff
	 * the value changed. If the fractional part is 0.5, the value is
	 * rounded up.
	 */
	public boolean roundValue() {
		double oldValue = value;
		value = Math.round((float)value);
		return oldValue != value;
	}
}