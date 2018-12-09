package menu;

import processing.core.*;

/*  Abstract base class for every option in the menu. MenuItems are items
 *  in the menu that the user may select and perform an action upon via key
 *  input. MenuItems are displayed as single lines of text when the menu
 *  is showing on screen and can respond to key input when selected in the menu.
 */
public abstract class MenuItem implements PConstants {

	// Name of this item. May be used for debugging or display purposes.
	public String name;
	
	// Parent PApplet for accessing Processing functions.
	protected PApplet parent;
	
	/* Constructor that sets name and parent. */
	protected MenuItem(PApplet p, String nameInit) {
		name = nameInit;
		parent = p;
	}

	/* toString function must be overridden by child classes.
	 * The String returned by this function is what is displayed on screen
	 * as this MenuItem's entry in the menu. These strings should be relatively
	 * short and should not contain newlines.
	 */
	abstract public String toString();

	/* Called if a key is pressed while this menuItem is selected.
	 * Should return true iff this MenuItem successfully performed
	 * some action as a result of the key press. key and keyCode are
	 * equivalent to the variables of the same name in PApplet.
	 * The DOWN and UP keys have reserved functionality for the top-level
	 * menu, so this function will never receive those keys.
	 */
	abstract protected boolean action(char key, int keyCode);
}
