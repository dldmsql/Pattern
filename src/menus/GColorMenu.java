package menus;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import main.GConstants;
import main.GConstants.EColorMenu;

public class GColorMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	// components

	public GColorMenu(String name) {
		super(name);

		this.setMnemonic(KeyEvent.VK_C);

		for (GConstants.EColorMenu eMenu: GConstants.EColorMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenu.getTitle());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(GConstants.colorMenuKeyValue[eMenu.ordinal()], Event.CTRL_MASK));
			menuItem.addActionListener(this.actionHandler);
			menuItem.setActionCommand(eMenu.getActionCommand());
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}	
	}

	public void initialize() {
	}

	public void setLineColor() { // colorChooser 다이얼로그 이쁜걸로 찾아서 바꾸고, stroke도 변경할 수 있으면 추가점수.
		Color selectedColor = JColorChooser.showDialog(drawingpanel, EColorMenu.eLineColor.getTitle(), this.drawingpanel.getForeground());
		this.drawingpanel.setLineColor(selectedColor);
	}
	public void setFillColor() {
		Color selectedColor = JColorChooser.showDialog(drawingpanel, EColorMenu.eFillColor.getTitle(), this.drawingpanel.getForeground());
		this.drawingpanel.setFillColor(selectedColor);
	}
	public void setFont() {
		FontDialog dialog = new FontDialog();
		int returnVal = dialog.showDialog(drawingpanel);
		if(returnVal == dialog.OK_OPTION) {
			Font font = dialog.getSelectedFont();
			this.drawingpanel.setUserFont(font);
		}
	}
	
	@Override
	public int checkSave() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

}
