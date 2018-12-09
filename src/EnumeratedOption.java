package menu;

import processing.core.*;

/* A MenuItem that stores an enumerated value.
 * An EnumeratedOption has a set of possible String values and at some
 * point in time, the current value is one of them. 
 */
public class EnumeratedOption extends MenuItem {

	// List of possible values.
	private String[] options;

	// Index of current value in the options array.
	private int selected;
	
	// Initial or default value. Used if this option is reset.
	private int defaultIndex;

	/* Constructor.
	 * nameInit is the name of this option.
	 * optionsInit is an array holding all possible values for this option.
	 * (the array is copied and not modified by this function)
	 * index is an index into optionsInit specifying which one should be the
	 * initial value.
	 */
	public EnumeratedOption(PApplet p, String nameInit, String[] optionsInit, int index) {
		super(p, nameInit);
		options = optionsInit.clone();
		selected = index;
		defaultIndex = index;
		if (index < 0 || index >= options.length)
			throw new IllegalArgumentException ("Invalid initial index.");
	}

	/* Implementation of MenuItem.toString().
	 * Returns the string that should be displayed for this item in the menu.
	 */
	public String toString() {
		return name + " = " + options[selected];
	}

	/* Implementation of MenuItem.action().
	 * Performs some action on key press and returns true iff the value changed.
	 * 
	 * Available actions on an EnumeratedOption:
	 * LEFT ARROW and RIGHT ARROW: scroll through possible options, wrapping
	 * around as necessary.
	 * ENTER/RETURN: reset this option to its default.
	 * - and = : set this option to the first or last value in the array this
	 * option was initialized with.
	 */
	protected boolean action(char key, int keyCode) {
		if (key == CODED && keyCode == LEFT) {
			selected = (selected + 1) % options.length;
			return true;
		} else if (key == CODED && keyCode == RIGHT) {
			selected--;
			if (selected < 0) selected = options.length - 1;
			return true;
		} else if (key == ENTER || key == RETURN) {
			return set(defaultIndex);
		} else if (key == '-') {
			return set(0);
		} else if (key == '=') {
			return set(options.length - 1);
		}
		return false;
	}

	/* Get the index of the current value of this option.
	 * The index returned is an index into the array this option
	 * was initialized with.
	 */
	public int get() {
		return selected;
	}
	
	/* Set the value of this option. Must pass an index into the array
	 * this option was initialized with. Throws an exception if the index
	 * is out of bounds. Returns true iff the selected item changed.
	 */
	public boolean set (int index) {
		if (index < 0 || index > options.length)
			throw new IllegalArgumentException ("Invalid index.");
		boolean result = selected != index;
		selected = index;
		return result;
	}
}
