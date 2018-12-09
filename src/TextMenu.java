package menu;

import processing.core.*;
import processing.event.*;

/*  A text-based options menu for Processing applications.
 *  Can be shown and hidden and may contain a variety of items.
 *  Navigated with keyboard input, such as arrow keys.
 */

public class TextMenu implements PConstants {

	// parent PApplet for using Processing functions.
	private PApplet parent;

	// Is the menu currently displayed on the screen?
	private boolean isShowing;
	
	// Does the menu currently respond to key input?
	private boolean acceptKeys;

	// Index of the item currently highlighted in the list.
	private int selectedIndex;

	// SubMenu representing the top level of the menu.
	private SubMenu topMenu;

	// SubMenu currently displayed.
	private SubMenu currMenu;

	// Colors for displaying the text of the menu.
	private int textColor;
	private int textColorInverse;

	// Location of the menu's bottom left corner.
	private float xCoord, yCoord;
	
	// Constant for the vertical spacing between lines when displayed.
	private final float TXT_SPACING = 5;


	/* Constructor
	 * Creates a new TextMenu at location (x, y) with text color c.
	 * The TextMenu is initially empty and will display nothing if
	 * show() is called. Use add() to add items to the menu.
	 */
	public TextMenu(PApplet p, float x, float y, int c) {

		parent = p;
		xCoord = x;
		yCoord = y;
		setColor(c);
		isShowing = false;
		acceptKeys = false;

		topMenu = new SubMenu(parent, "top");
		topMenu.noBack();
		topMenu.setMenu(this);
		currMenu = topMenu;
		selectedIndex = 0;
		
		// Register draw and keyEvent functions so they will be called
		// by the PApplet.
		parent.registerMethod("draw", this);
		parent.registerMethod("keyEvent", this);
	}

	/* Set the color of the text when this menu is displayed.
	 * The inverse color will be the RGB colorspace inverse of the
	 * provided color and will be used for text that is highlighted
	 * in the menu.
	 */
	public void setColor(int c) {
		float newR = 255 - parent.red(c);
		float newG = 255 - parent.green(c);
		float newB = 255 - parent.blue(c);
		parent.colorMode(RGB);
		setColor(c, parent.color(newR, newG, newB));
	}
	
	/* Set the color of this menu's text with a specified inverse.
	 * The inverse color is the color used for text that is highlighted
	 * in the menu.
	 */
	public void setColor(int c, int inverse) {
		textColor = c;
		textColorInverse = inverse;
	}
	
	/* Set the location of the menu's bottom left corner. */
	public void setLocation(float x, float y) {
		xCoord = x;
		yCoord = y;
	}

	/* Add items to the menu. Items will be added in left-to-right order
	 * with increasing indices appended to the end of the menu's contents.
	 * When the menu is displayed on-screen, items with lower indices appear
	 * lower on screen (higher y-value), and the item with the highest index
	 * is at the top.
	 * Returns true iff all items added successfully.
	 */
	public boolean add(MenuItem... itemsToAdd) {
		return topMenu.add(itemsToAdd);
	}
	
	/* Remove and return the MenuItem at the given index in the top
	 * level of this menu.
	 */
	public MenuItem remove(int index) {
		return topMenu.remove(index);
	}
	
	/* Remove all items from the top level of this menu. */
	public void clear() {
		topMenu.clear();
	}

	/* Set the menu to showing. After making this call, the menu
	 * will remain displayed on the screen until hide() is called.
	 * If active is true, then the menu will accept key input. Otherwise,
	 * the menu will be displayed but will not respond to input.
	 */
	public void show(boolean active) {
		isShowing = true;
		acceptKeys = active;
	}

	/* Hide the menu. After making this call, the menu will be
	 * invisible and will not respond to key input until show() is called.
	 */
	public void hide() {
		isShowing = false;
		acceptKeys = false;
	}

	/* Draws the menu, reflecting any updates that may have been made.
	 * This function is called after the PApplet's draw() function.
	 * If a sketch uses multiple libraries that include draw() functionality,
	 * there is a potential for interference.
	 */
	public boolean draw() {
		if (isShowing) {

			int yDisplace = 0;
			int index = 0;
			float asc = parent.textAscent();
			float dsc = parent.textDescent();
			parent.noStroke();
			
			// Loop through all items in the current SubMenu
			// and draw the text for each one, starting at the bottom.
			for (int i = 0; i < currMenu.size(); i++) {
				MenuItem m = currMenu.get(i);
				
				parent.fill(textColor);
				String mString = m.toString();
				
				// If current item is selected, draw a highlight box around it.
				if (index == selectedIndex) {
					float highlightWidth = 0;
					for (char c : mString.toCharArray())
						highlightWidth += parent.textWidth(c);
					parent.rect(xCoord, yCoord - yDisplace - asc, highlightWidth, asc + dsc);
					parent.fill(textColorInverse);
				}
				// Draw the text for the current item.
				parent.text(mString, xCoord, yCoord - yDisplace);
				yDisplace += asc + TXT_SPACING;
				index++;
			}
		}
		return isShowing;
	}

	/* Returns true iff this menu is currently being displayed on screen.
	 * true after show(), false after hide().
	 */
	public boolean isShowing() {
		return isShowing;
	}

	/* Called when a key event occurs.
	 * If this menu is currently accepting key input, the keypress
	 * will be processed. If the key is UP or DOWN, the selected item will
	 * change to the one above or below the currently selected item. Otherwise,
	 * the key will be passed on to be handled by the currently selected item.
	 * 
	 * Returns true iff some action was successfully performed as a result.
	 */
	public boolean keyEvent(KeyEvent e) {
		if (acceptKeys && e.getAction() == KeyEvent.PRESS) {
			char key = e.getKey();
			int keyCode = e.getKeyCode();
			if (key == CODED && keyCode == UP) {
				// move selected index up by one, capped at the last item
				selectedIndex = Math.min(selectedIndex + 1, currMenu.size() - 1);
			} else if (key == CODED && keyCode == DOWN) {
				// move selected index down by one, floored at zero
				selectedIndex = Math.max(selectedIndex - 1, 0);
			} else if (!currMenu.isEmpty()) {
				// let the currently selected item handle the key
				return currMenu.get(selectedIndex).action(key, keyCode);
			}
			return true;
		}
		return false;
	}

	/* Used by SubMenu to change the current SubMenu being displayed
	 * when the user navigates up or down a level.
	 */
	protected void setCurrMenu(SubMenu sub) {
		currMenu = sub;
		selectedIndex = 0;
	}

}