package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import main.GConstants;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	// components
	private ActionHadler actionHadler;
	private StrokeChooseHandler strokeChooseHandler;
	
	private Vector<JButton> buttons;
	private JComboBox<Integer> strokeSizeCombo;
	private JComboBox<float[]> strokePatternCombo;
	
	// associations
	private GDrawingPanel drawingPanel;
	
	public GToolBar() {
		super();
		// create components
		this.actionHadler = new ActionHadler();
		this.strokeChooseHandler = new StrokeChooseHandler();
		
		this.buttons = new Vector<JButton>();		
		for (GConstants.EToolbar eTool: GConstants.EToolbar.values()) {
			JButton button = new JButton(eTool.getTitle());
			button.setToolTipText(eTool.getToolTip()); // toolTip Àû¿ë
			button.setActionCommand(eTool.toString());
			button.addActionListener(this.actionHadler);
			this.buttons.add(button);
			this.add(button);
		}		
		JLabel lStroke = new JLabel(GConstants.StrokeSize);
		lStroke.setFont(GConstants.font);
		this.add(lStroke);
		// combobox
		this.strokeSizeCombo = new JComboBox<Integer>();
		for(int i=1;i<6; i++) {
			this.strokeSizeCombo.addItem(i);
		}
		this.strokeSizeCombo.addActionListener(this.strokeChooseHandler);
		this.strokeSizeCombo.setActionCommand(GConstants.StrokeSize);
		this.add(this.strokeSizeCombo);
		
		
		JLabel lStrokeP = new JLabel(GConstants.StrokePattern);
		lStrokeP.setFont(GConstants.font);
		this.add(lStrokeP);
		
		this.strokePatternCombo = new JComboBox<>();
		
		// change to combobox item name.!!
		this.strokePatternCombo.addItem(null);
		this.strokePatternCombo.addItem(new float[] {2f, 2f});
		this.strokePatternCombo.addItem(new float[] {10f, 4f});
		this.strokePatternCombo.addItem(new float[] {10f, 10f, 1f, 10f});
		this.strokePatternCombo.addActionListener(strokeChooseHandler);
		this.strokePatternCombo.setActionCommand(GConstants.StrokePattern);
		this.add(this.strokePatternCombo);
	}
	
	public void setAssociation(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;		
	}
	public void initialize() {
		// set associations
		
		// set associative attributes
		this.buttons.get(0).doClick();
		
		// initialize components
	}
	
	class ActionHadler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setCurrentTool(GConstants.EToolbar.valueOf(event.getActionCommand()));
		}		
	}
	class StrokeChooseHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent event) {
			int index = (int) strokeSizeCombo.getSelectedItem();
			float[] dash = (float[]) strokePatternCombo.getSelectedItem();
			drawingPanel.setStroke(index, dash);
		}

	}

}
