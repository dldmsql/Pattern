package menus;
import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import main.GConstants;

public class GEditMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	// components

	public GEditMenu(String name) {
		super(name);

		this.setMnemonic(KeyEvent.VK_E);

		for (GConstants.EEditMenu eMenu: GConstants.EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenu.getTitle());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(GConstants.editMenuKeyValue[eMenu.ordinal()], Event.CTRL_MASK));
			menuItem.setActionCommand(eMenu.getActionCommand());
			menuItem.addActionListener(this.actionHandler);
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}
	}

	public void initialize() {
	}

	public void undo() {
		this.drawingpanel.undo();
	}
	public void redo() {
		this.drawingpanel.redo();
	}
	public void copy() {
		this.drawingpanel.copy();
	}
	public void cut() {
		this.drawingpanel.cut();
	}
	public void paste() {
		this.drawingpanel.paste();
	}
	public void group() {
		this.drawingpanel.group(); 
	}
	public void unGroup() {
		this.drawingpanel.unGroup();
	}
	public void delete() {
		this.drawingpanel.clearShapes();
	}
	public void shadow() {
		this.drawingpanel.shadow();
	}

	@Override
	public int checkSave() {
		return 0;
	}

	@Override
	public void save() {
	}
}
