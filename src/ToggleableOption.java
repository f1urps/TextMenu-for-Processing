package menu;

import processing.core.*;

/*  Menu item that wraps a boolean value.
 *  This value can be toggled with enter/return, left, or right
 *  when selected in the menu.
 */
public class ToggleableOption extends MenuItem {

	// The option's current value.
	private boolean value;

	/* Constructor.
	 * nameInit is the name of this option and valueInit is its initial value.
	 */
	public ToggleableOption(PApplet p, String nameInit, boolean valueInit) {
		super(p, nameInit);
		value = valueInit;
	}

	/* Implementation of MenuItem.toString().
	 * Returns the string that should be displayed for this item.
	 */
	public String toString() {
		return name + " = " + value;
	}

	/* Implementation of MenuItem.action().
	 * Perform some action on a key press when this item is selected.
	 * 
	 * Toggles the value of this option when right arrow, left arrow,
	 * enter, or return are received.
	 */
	protected boolean action(char key, int keyCode) {
		if ((key == CODED && (keyCode == RIGHT || keyCode == LEFT)) || key == ENTER || key == RETURN) {
			return toggle();
		}
		return false;
	}

	/* Returns the current value that this option is set to. */
	public boolean get() {
		return value;
	}

	/* Set the value of this option. Returns true iff the new value
	 * is different than the previous value.
	 */
	public boolean set(boolean toSet) {
		boolean oldValue = value;
		value = toSet;
		return oldValue != value;
	}

	/* Toggle the value of this option. I.e., if this option is currently
	 * true, set it to false, and vise versa.
	 * Returns true iff the value changed, which should always be the case
	 * unless an error occurs.
	 */
	public boolean toggle() {
		return set(!get());
	}
}
