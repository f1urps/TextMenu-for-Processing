package menu;

import java.util.ArrayList;

import processing.core.*;

/* A MenuItem that serves as an entry into another level of the menu,
 * allowing the menu's options to be organized in a directory-like tree
 * structure. A SubMenu also serves as the underlying data structure for
 * the top level or "root" of the menu.
 */
public class SubMenu extends MenuItem {

	// The MenuItems in this SubMenu. These are the items that
	// will be displayed when this SubMenu is entered.
	private ArrayList<MenuItem> items;

	// The SubMenu which contains this one. May be the TextMenu's top
	// level, or some other SubMenu contained within it.
	private SubMenu superMenu;
	
	// TextMenu that this SubMenu is in.
	private TextMenu menu;

	/* Constructor.
	 * Initializes an empty SubMenu.
	 */
	public SubMenu(PApplet p, String nameInit) {
		super(p, nameInit);
		items = new ArrayList<>();
		superMenu = null;
		menu = null;
		items.add(new BackButton(parent, this));
	}

	/* Adds MenuItems to this SubMenu. Items added through
	 * this function will be displayed when the SubMenu is
	 * entered, with the leftmost item closest to the bottom.
	 */
	public boolean add(MenuItem... itemsToAdd) {
		boolean addedItems = true;
		for (MenuItem item : itemsToAdd) {
			addedItems &= items.add(item);
			
			if (item instanceof SubMenu) {
				((SubMenu)item).superMenu = this;
				((SubMenu)item).setMenu(this.menu);
			} else if (item instanceof ColorOption) {
				((ColorOption)item).colorMenu.superMenu = this;
				((ColorOption)item).setMenu(this.menu);
			}
		}
		return addedItems;
	}
	
	/* Returns true iff the SubMenu contains no items. */
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	/* Returns the MenuItem at an index within this SubMenu.
	 * The item at index 0 is at the bottom of the SubMenu when
	 * it is displayed, and higher indices are higher on screen.
	 */
	public MenuItem get(int index) {
		return items.get(index);
	}
	
	/* Returns the number of items in this SubMenu. */
	public int size() {
		return items.size();
	}
	
	/* Remove an item from the given index. Returns the Item removed. */
	public MenuItem remove(int index) {
		return items.remove(index);
	}
	
	/* Remove all items from this SubMenu, making it empty. */
	public void clear() {
		items.clear();
	}

	/* Set the menu field of this SubMenu and all SubMenus contained
	 * within it. Used internally for initialization. */
	protected void setMenu(TextMenu menuSet) {
		menu = menuSet;
		for (MenuItem item : items) {
			if (item instanceof SubMenu) {
				((SubMenu)item).setMenu(menuSet);
			} else if (item instanceof ColorOption) {
				((ColorOption)item).setMenu(menuSet);
			}
		}
	}

	/* Return the string that should be displayed for this SubMenu
	 * in the parent SubMenu's listing. */
	public String toString() {
		return "> " + name;
	}

	/* Implementation of MenuItem.action().
	 * Perform an action on key input when this item is selected.
	 * 
	 * Upon receipt of the keys enter, return, or right arrow,
	 * the menu will enter this SubMenu, switching the display contents
	 * to the contents of this SubMenu, along with a back button.
	 */
	protected boolean action(char key, int keyCode) {
		if (key == RETURN || key == ENTER || (key == CODED && keyCode == RIGHT)) {
			if (menu != null)
				menu.setCurrMenu(this);
			return true;
		}
		return false;
	}

	/* Set this SubMenu to not have a back button.
	 * This SubMenu will not be able to be exited once entered.
	 * 
	 * Used internally for initialization. The only SubMenu that
	 * should not have a back button is the top-level directory
	 * of a TextMenu.
	 */
	protected void noBack() {
		if (items.size() > 0 && items.get(0) instanceof BackButton) {
			items.remove(0);
		}
	}

	
	/* Private nested class for the "back button" that appears at the bottom
	 * of every SubMenu.
	 */
	private class BackButton extends MenuItem {

		// The SubMenu that this BackButton is a part of.
		private SubMenu in;

		/* Constructor, which should only be called by SubMenu.
		 * menuIn is the SubMenu that this BackButton is in.
		 */
		protected BackButton(PApplet p, SubMenu menuIn) {
			super(p, menuIn.name + " back");
			in = menuIn;
		}

		/* Implementation of MenuItem.toString().
		 * Returns the string that should be displayed in the parent
		 * SubMenu.
		 */
		public String toString() {
			return "< back";
		}

		/* Implementation of MenuItem.action().
		 * Performs an action on key input when this item is selected.
		 * 
		 * On the following keys: return, enter, left arrow
		 * the menu will navigate to the parent SubMenu of the 
		 * current one.
		 */
		protected boolean action(char key, int keyCode) {
			if (key == RETURN || key == ENTER || (key == CODED && keyCode == LEFT)) {
				in.menu.setCurrMenu(in.superMenu);
				return true;
			}
			return false;
		}
	}
}