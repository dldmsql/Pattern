package menus;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import File.GFile;
import main.GConstants;

public class GFileMenu extends GMenu {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	//working variables
	File directory;
	File imageDirectory;
	File file;

	public GFileMenu(String name) {
		super(name);
		
		this.setMnemonic(KeyEvent.VK_F); // alt + F
		
		for (GConstants.EFileMenu eMenu: GConstants.EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenu.getTitle());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(GConstants.fileMenuKeyValue[eMenu.ordinal()], Event.CTRL_MASK));
			menuItem.setActionCommand(eMenu.getActionCommand());
			menuItem.addActionListener(this.actionHandler);
			this.menuItems.add(menuItem);
			this.add(menuItem);
		}
		
		
		this.directory = new File(GConstants.directoryAddress);
		this.imageDirectory = new File(GConstants.imageDirectoryAddress);
		this.file = null;
	}

	public void initialize() {

	}
	public void imageOpen() {
		// Image Select
		JFileChooser fileChooser = new JFileChooser(this.imageDirectory);
		int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.drawingpanel.addImage(fileChooser.getSelectedFile());
		}
	}
	public void clip() {
		// Image Select
		JFileChooser fileChooser = new JFileChooser(this.imageDirectory);
		int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.drawingpanel.clip(fileChooser.getSelectedFile());
		}
	}
	
	@Override
	public int checkSave() {
		int reply = 0;
		if(this.drawingpanel.isbUpdated()) {
			reply = JOptionPane.showConfirmDialog(
					null,
					GConstants.confirmDialogMessage, 
					GConstants.confirmDialogTitle, 
					JOptionPane.YES_NO_CANCEL_OPTION);
		}
		return reply;
	}
	public void nnew() {
		this.checkSave();
		
		this.drawingpanel.clearShapes();
		this.file = null;
	}
	public void open() {
		this.checkSave();
		
		JFileChooser fileChooser = new JFileChooser(this.directory);
		int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.drawingpanel.clearShapes();
			this.directory = fileChooser.getCurrentDirectory();
			this.file = fileChooser.getSelectedFile();
			GFile gFile = new GFile();
			Object shapes = gFile.readObject(this.file);
			this.drawingpanel.setShapes(shapes);
		}
	}
	public void save() {
		if(this.file==null) {
			this.saveAs();
		}else {
			GFile gFile = new GFile();
			gFile.saveObject(drawingpanel.getShapes(), this.file);
			this.drawingpanel.setbUpdated(false);
		}
	}

	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser(this.directory);
		int returnValue = fileChooser.showOpenDialog(this.drawingpanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.directory = fileChooser.getCurrentDirectory();
			this.file = fileChooser.getSelectedFile();
			this.save();
		}
	}
	public void print() {this.drawingpanel.print();}
	public void exit() {
		this.checkSave();
		System.exit(0);
	}
}
