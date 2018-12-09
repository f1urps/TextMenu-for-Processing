package menu;
import processing.core.*;

/* A MenuItem that stores a 3-component color.
 * "entering" this item through the menu will open a submenu containing
 * integer options for its three components.
 * The three components of the color may be RGB or HSB.
 */
public class ColorOption extends MenuItem {

	// The color mode of this color. May be RGB or HSB.
	final private int colorMode;
	
	protected SubMenu colorMenu;

	// The three component values of this color.
	// If colorMode is RGB, they are red, green, and blue respectively.
	// If colorMode is HSB, they are hue, saturation, and brightness instead.
	private IntegerOption component1, component2, component3;

	/* Constructor.
	 * nameInit is the name of the color option, colorModeInit is its color mode
	 * (RGB or HSB), and comp1...comp3 are initial values for the color's three
	 * components. Values for the components must be within the range [0, 255].
	 */
	public ColorOption(PApplet p, String nameInit, int colorModeInit, int comp1, int comp2, int comp3) {
		super(p, nameInit);
		colorMode = colorModeInit;
		char[] cm;
		if (colorMode == RGB) {
			cm = "RGB".toCharArray();
		} else if (colorMode == HSB) {
			cm = "HSB".toCharArray();
		} else {
			throw new IllegalArgumentException("Invalid color mode: " + colorModeInit);
		}
		component1 = new IntegerOption(parent, cm[0] + "", comp1, 0, 255);
		component2 = new IntegerOption(parent, cm[1] + "", comp2, 0, 255);
		component3 = new IntegerOption(parent, cm[2] + "", comp3, 0, 255);
		
		colorMenu = new SubMenu(parent, name + " menu");
		colorMenu.add(component3, component2, component1);
	}

	/* Set this ColorOption to a specific color. Returns true iff the color
	 * changed.
	 */
	public boolean set(int newColor) {
		boolean ret = false;
		if (colorMode == RGB) {
			ret |= component1.set(Math.round(parent.red(newColor)));
			ret |= component2.set(Math.round(parent.green(newColor)));
			ret |= component3.set(Math.round(parent.blue(newColor)));
		} else if (colorMode == HSB) {
			ret |= component1.set(Math.round(parent.hue(newColor)));
			ret |= component2.set(Math.round(parent.saturation(newColor)));
			ret |= component3.set(Math.round(parent.brightness(newColor)));
		}
		return ret;
	}

	/* Set the value of an individual component and return true iff
	 * its value changed.
	 * Argument val is the new value and must be between 0 and 255, inclusive.
	 * Argument comp may be 0, 1, or 2, signifying the first, second, and
	 * third components. Any other values generate an exception.
	 * 
	 * If the colorMode of this option is set to RGB, then the first component is
	 * red, the second green, and the third is blue.
	 * If the colorMode is HSB, then the first component is hue, the second
	 * saturation, and the third brightness.
	 */
	public boolean setComponent(int val, int comp) {
		if (comp == 0)
			return component1.set(val);
		else if (comp == 1)
			return component2.set(val);
		else if (comp == 2)
			return component3.set(val);
		else
			throw new IllegalArgumentException("Component argument must be 0, 1, or 2. Given: " + comp);
	}

	/* Returns the value of one component of this color.
	 * Argument comp may be 0, 1, or 2, signifying the first, second, and
	 * third components. Any other values generate an exception.
	 * 
	 * If the colorMode of this option is set to RGB, then the first component is
	 * red, the second green, and the third is blue.
	 * If the colorMode is HSB, then the first component is hue, the second
	 * saturation, and the third brightness.
	 */
	public int getComponent(int comp) {
		if (comp == 0)
			return component1.get();
		else if (comp == 1)
			return component2.get();
		else if (comp == 2)
			return component3.get();
		else
			throw new IllegalArgumentException("Component argument must be 0, 1, or 2. Given: " + comp);
	}

	/* Returns the color currently stored.
	 * WARNING: This function may change the color mode of the PApplet.
	 * If colors are used elsewhere in the project, be sure to call colorMode()
	 * after calling this function to set the color mode back to your desired mode.
	 */
	public int get() {
		int comp1value = component1.get();
		int comp2value = component2.get();
		int comp3value = component3.get();
		parent.colorMode(colorMode, 255, 255, 255);
		return parent.color(comp1value, comp2value, comp3value);
	}
	
	/* Implementation of MenuItem.action().
	 * Performs some action on a keypress and returns true iff it did something.
	 * 
	 * On keys ENTER, RETURN, or RIGHT ARROW, the color menu is entered,
	 * displaying the three components and a back button.
	 */
	protected boolean action(char key, int keyCode) {
		return colorMenu.action(key, keyCode);
	}
	
	/* Implementation of MenuItem.toString().
	 * Returns the String that should be displayed on screen for this item.
	 */
	public String toString() {
		return "> " + name;
	}
	
	/* Set the menu field of colorMenu. Used internally
	 * for initialization.
	 */
	protected void setMenu(TextMenu menu) {
		colorMenu.setMenu(menu);
	}
}