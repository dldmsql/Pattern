package frames;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import main.GConstants;

public class GMainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;
	
	public GMainFrame() {
		super();
		
		// initialize attributes
		this.setSize(GConstants.EMainFrame.eWidth.getValue(), 
				GConstants.EMainFrame.eHeight.getValue());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		// create,register components
		WindowActionHandler windowActionHandler = new WindowActionHandler();
		this.addWindowListener(windowActionHandler);
		
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
	}

	public void initialize() {
		// set associations
		this.toolBar.setAssociation(this.drawingPanel);	
		this.menuBar.setAssociation(this.drawingPanel);	
		// initialize associative attributes
		
		// initialize components
		this.menuBar.initialize();
		this.toolBar.initialize();		
		this.drawingPanel.initialize();	
	}

	private class WindowActionHandler extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent event) {
			menuBar.getFileMenu().checkSave(); // 자식의 자식 객체의 함수 호출 - 문제
		}
	}
}
