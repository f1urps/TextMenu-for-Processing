package menu;

import processing.core.*;

/* A MenuItem that does nothing except display a string in the
 * menu. Might be used to visually separate or label groups of options. */

public class StringItem extends MenuItem {

	// The string to display
	private String displayString;
	
	/* Constructor. Takes PApplet and the display string. */
	public StringItem(PApplet p, String disp) {
		super(p, disp);
		if (disp == null) {
			disp = "";
		}
		displayString = disp;
	}
	
	/* Implementation of MenuItem.toString().
	 * Returns the string to display in the menu.
	 */
	public String toString() {
		return displayString;
	}

	/* Implementation of MenuItem.action().
	 * Does nothing on key input.
	 */
	protected boolean action(char key, int keyCode) {
		return false;
	}
	
}
