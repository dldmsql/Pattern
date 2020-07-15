package frames;

import java.util.Vector;

import javax.swing.JMenuBar;

import main.GConstants;
import main.GConstants.EMenubar;
import menus.GFileMenu;
import menus.GMenu;

public class GMenuBar extends JMenuBar {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	private Vector<GMenu> menus;
	
	public GMenuBar() {
		super();
		// set attributes
		
		// create components
		this.menus = new Vector<GMenu>();
		
		for (GConstants.EMenubar eMenu: GConstants.EMenubar.values()) {
			GMenu menu = eMenu.getMenu();
			this.menus.add(menu);
			this.add(menu);			
		}
	}

	public void initialize() {
		// set associations
		
		// set associative attributes
		
		// initialize components
		for (GMenu menu: this.menus) {
			menu.initialize();
		}
	}

	public void setAssociation(GDrawingPanel drawingPanel) {
		for (GMenu menu: menus) {
			menu.setAssociation(drawingPanel);
		}
	}

	public GFileMenu getFileMenu() { // 별로 좋은 방식은 아니야..
		return (GFileMenu)this.menus.get(EMenubar.eFile.ordinal());
	}

}
