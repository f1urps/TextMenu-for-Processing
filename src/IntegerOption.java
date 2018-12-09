package menu;

import processing.core.*;

/*  Menu item that wraps an integer value.
 *  A minimum and a maximum must be specified, and the value will be
 *  restricted to those bounds.
 *  The value can be altered in the menu with left and right arrow keys,
 *  which decrement and increment, respectively. Additionally, enter/return
 *  will reset the option, and - or = will set it to its minimum or maximum.
 */
public class IntegerOption extends MenuItem {

	// The current value of this option
	private int value;

	// The initial value of this option, so it can be reset
	private final int defaultValue;

	// Upper and lower bounds on the value of this option
	private final int max, min;

	/* Constructor.
	 * nameInit is the name of this option.
	 * valInit is what this option should be initialized to, and will also serve
	 * as the default value if this option gets reset.
	 * minInit and maxInit are desired lower and upper bounds on this option.
	 */
	public IntegerOption(PApplet p, String nameInit, int valInit, int minInit, int maxInit) {
		super(p, nameInit);
		if (maxInit < minInit) {
			throw new IllegalArgumentException("Max cannot be smaller than min.");
		} else if (valInit < minInit || valInit > maxInit) {
			throw new IllegalArgumentException("Initial value not in specified range.");
		}
		value = valInit;
		max = maxInit;
		min = minInit;
		defaultValue = valInit;
	}

	/* Implementation of MenuItem.toString().
	 * Returns the String that should be displayed for this option.
	 */
	public String toString() {
		return name + " = " + value;
	}

	/* Implementation of MenuItem.action().
	 * Perform an action on key input when this option is selected.
	 * 
	 * right arrow: increments value by 1
	 * left arrow: decrements value by 1
	 * enter/return: resets option to initial value
	 * dash/minus (-): sets option to the minimum
	 * equals sign (=): sets option to the maximum
	 */
	protected boolean action(char key, int keyCode) {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				return add(1);
			} else if (keyCode == LEFT) {
				return add(-1);
			}
		} else if (key == ENTER || key == RETURN) {
			return set(defaultValue);
		} else if (key == '-') {
			return set(min);
		} else if (key == '=') {
			return set(max);
		}
		return false;
	}

	/* Returns the current value of this option. */
	public int get() {
		return value;
	}

	/* Set the value of this option.
	 * Return true iff the value changes.
	 */
	public boolean set(int toSet) {
		int oldValue = value;
		value = Math.max(Math.min(toSet, max), min);
		return oldValue != value;
	}

	/* Increment the value of this option by some amount. If toAdd is
	 * negative, the value will be decremented.
	 * Returns true iff the value changes.
	 */
	public boolean add(int toAdd) {
		return set(get() + toAdd);
	}
}