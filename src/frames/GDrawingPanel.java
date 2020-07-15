package frames;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.GConstants;
import main.GConstants.ECursor;
import main.GConstants.EToolbar;
import menus.GPopupMenu;
import shape.GAnchors;
import shape.GGroup;
import shape.GImageRectangle;
import shape.GShape;
import shape.GShape.EDrawingStyle;
import shape.GTextBox;
import tool.ListStack;
import tranformer.GDrawer;
import tranformer.GMover;
import tranformer.GResizer;
import tranformer.GRotator;
import tranformer.GTransformer;

public class GDrawingPanel extends JPanel implements Printable{
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	private enum EDrawingState {
		eIdle, eDrawing, eTransforming
	}
	private EDrawingState eDrawingState;	
	private Color lineColor, fillColor; 
	private boolean bUpdated;	
	private boolean bDrawing;
	private boolean bGrouping ;
	private boolean bCliped ;
	private Image img;
	
	// components
	private MouseHandler mouseHandler;
	private Vector<GShape> shapes;
//	private Vector<GShape> group;
	private ListStack drawStack, transformStack;

	// association
	private GShape currentTool;
	private GShape currentShape;
	private GShape copyShape;
	private GShape beforeTransforming;
	private GTransformer transformer;

	// for zoom
	private double zoomFactor = 1;
	private double preZoomFactor = 1;
	private boolean zoomer;
	private boolean dragger;
	private boolean released;
	private double xOffset = 0;
	private double yOffset = 0;
	private int xDiff;
	private int yDiff;
	private Point startPoint;

	// for shape
	private boolean front;
	private Vector<GShape> forFront;
	
	
	// constructors and initializers
	public GDrawingPanel() { 
		// attributes
		this.setBackground(Color.white);
		this.eDrawingState = EDrawingState.eIdle;

		this.lineColor = null;
		this.fillColor = Color.WHITE;
		this.bUpdated = false;
		this.bDrawing = true;
		this.bGrouping = false;
		this.bCliped = false;
		
		// components
		this.mouseHandler = new MouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.addMouseWheelListener(this.mouseHandler);

		this.shapes = new Vector<GShape>();
//		this.group = new Vector<GShape>();
		this.drawStack = new ListStack();
		this.transformStack = new ListStack();
		
		this.forFront = new Vector<GShape>();

		// working variables
		this.currentTool = null;
		this.currentShape = null;
		this.copyShape = null;
		this.transformer = null;
	}
	public void initialize() {
		// set associations
		// set associative attributes
		// initialize components
		this.lineColor = this.getForeground(); 
	}

	// setters & getters
	public Vector<GShape> getShapes() {
		return this.shapes;
	}
	public void setShapes(Object shapes) { 
		this.shapes = (Vector<GShape>) shapes; // 이거 고치면 추가 점수
		this.repaint(); 
	}
	public void clearShapes() {
		this.shapes.clear();
		this.repaint();
	}
	public boolean isbUpdated() {
		return bUpdated;
	}
	public void setbUpdated(boolean bUpdated) {
		this.bUpdated = bUpdated;
	}

	public void setCurrentTool(EToolbar eToolBar) {
		this.currentTool = eToolBar.getTool();
	}

	// menu methods
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		if(this.currentShape != null) {
			this.currentShape.setLineColor(lineColor);
		}
		this.repaint();
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		if(this.currentShape != null) {
			this.currentShape.setFillColor(fillColor);
		}
		this.repaint();
	}
	public void addImage(File imageFile) {
		this.shapes.add(new GImageRectangle(imageFile));
		this.repaint();
	}
	public void setStroke(int index, float[] dash) {
		for(GShape shape:this.shapes) {
			if(shape.isSelected()) {
				shape.setStroke(index);
				shape.setStrokeDash(dash);
			}
		}
		this.repaint();
	}
	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean isPrinted = job.printDialog();
		if(isPrinted) {
			try {
				job.print();
			} catch(PrinterException e) {
				JOptionPane.showMessageDialog(this,GConstants.printingMessage);
			}
		}
	}

	public void undo() {
		if(this.shapes.size() != 0)	{ 
			this.shapes.remove(this.shapes.size()-1); 
		}
		if(!bDrawing) this.shapes.add(this.transformStack.peek());
		this.repaint();
	}
	public void redo() {
		if(this.bDrawing) this.shapes.add(this.drawStack.peek());
		else {
			this.shapes.remove(this.shapes.size()-1);
			this.shapes.add(this.currentShape);
		}

		this.repaint();
	}
	public void copy() {
		this.copyShape = (GShape) this.currentShape.clone();
	}
	public void cut() {
		this.copyShape = (GShape) this.currentShape.clone();
		this.shapes.remove(this.currentShape);
		this.repaint();
	}
	public void paste() {
		if(this.copyShape != null) this.shapes.add((GShape) this.copyShape.clone());
		repaint();
	}

	public void clip(File file) {
		this.bCliped = true;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.repaint();
	}
	public void group() {
		this.bGrouping = true;
	}
	public void unGroup() {
		
	}
	public void shadow() {
		if(this.currentShape.isShadow()) {
			this.currentShape.setShadow(false);
		} else 	{this.currentShape.setShadow(true);}
		this.repaint();
	}
	public void setUserFont(Font font) {
		if(this.currentShape instanceof GTextBox) {
			((GTextBox)this.currentShape).setFont(font);
		}
		this.repaint();
	}
	public void shapeGoFront(boolean front) {
		this.front = front;
		this.forFront.clear();
		
		if(this.front) {
			this.shapes.remove(this.currentShape);
			this.forFront.addAll(this.shapes);
			this.forFront.add(this.currentShape);
			this.shapes.clear();
			this.shapes.addAll(this.forFront);
		}
		else {
			this.forFront.add(this.currentShape);
			this.shapes.remove(this.currentShape);
			this.forFront.addAll(this.shapes);
			this.shapes.clear();
			this.shapes.addAll(this.forFront);
		}
		this.repaint();
	}
	

	@Override 
	public void paint(Graphics graphics) {
		super.paint(graphics); 

		Graphics2D g2d = (Graphics2D) graphics;
		if(zoomer) {
			AffineTransform affineTransform = new AffineTransform();
			double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX(); // 드래깅해서 바뀐 위치의 차
			double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

			double zoomDiv = zoomFactor / preZoomFactor; // 이전 확대 비율 & 현재 확대 비율

			xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel; // zoom비율 만큼 현재 마우스 있는 지점을 기준으로 aff에 적용
			yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

			affineTransform.translate(xOffset, yOffset);
			affineTransform.scale(zoomFactor, zoomFactor);
			preZoomFactor = zoomFactor; 
			g2d.transform(affineTransform);
			zoomer = false;
		}
		if (dragger) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFactor, zoomFactor);
            g2d.transform(at);

            if (released) {
                xOffset += xDiff; // xOffset = xOffset + xDiff
                yOffset += yDiff;
                dragger = false;
            }

        } 
		// user defined drawing
		for (GShape shape: this.shapes) {
			shape.draw(g2d);
		}
		if(bCliped) {
			g2d.setClip(this.currentShape.getShape());
			g2d.drawImage(img, this.currentShape.getBounds().x, this.currentShape.getBounds().y, null);
		}
    		
	}

	private void checkCursor(int x, int y) { 
		GShape selectedShape = this.onShape(x, y);
		if(selectedShape == null) {
			this.setCursor(ECursor.eDefault.getCursor());
		} else {
			GAnchors.EAnchors eSelectedAnchor = selectedShape.getESelectedAnchor();
			switch(eSelectedAnchor) {
			case NW : this.setCursor(ECursor.eNW.getCursor()); break;
			case NN : this.setCursor(ECursor.eNN.getCursor()); break;
			case NE : this.setCursor(ECursor.eNE.getCursor()); break;
			case EE : this.setCursor(ECursor.eEE.getCursor()); break;
			case SE : this.setCursor(ECursor.eSE.getCursor()); break;
			case SS : this.setCursor(ECursor.eSS.getCursor()); break;
			case SW : this.setCursor(ECursor.eSW.getCursor()); break;
			case WW : this.setCursor(ECursor.eWW.getCursor()); break;
			case RR : this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon(GConstants.rotateImageIcon).getImage(),
					new Point(0,0),null)); break;
			case MM : this.setCursor(ECursor.eMove.getCursor()); break;
			default : this.setCursor(ECursor.eDefault.getCursor()); break;
			}
		}
	}
	private GShape onShape(int x, int y) { 
		for(GShape shape : shapes) {
			if(shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}

	private void setSelected(GShape selectedShape) {
		for(GShape shape : shapes) { 
			shape.setSelected(false);
		}
		selectedShape.setSelected(true); 
		this.repaint();
	}

	// transforming method
	private void initTransforming(GShape shape, int x, int y) {
		if(shape == null) {
			this.bDrawing = true;
			// drawing
			this.currentShape = this.currentTool.clone(); // new Shape

			this.currentShape.setLineColor(lineColor);
			this.currentShape.setFillColor(fillColor);
			this.transformer = new GDrawer(this.currentShape);
		} else {
			// transformation
			this.bDrawing = false;
			this.currentShape = shape;

			this.beforeTransforming = this.currentShape.clone();
			this.transformStack.push(this.beforeTransforming);

			switch (shape.getESelectedAnchor()) {
			case MM: 
				this.transformer = new GMover(this.currentShape);
				break;
			case RR: 
				this.transformer = new GRotator(this.currentShape);
				break;
			default: // resize
				this.transformer = new GResizer(this.currentShape);
				break;
			}
		}
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		this.transformer.initTransforming(graphics2d,x, y);
	}
	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setBackground(Color.white); // -> 흔적 지우기
		this.transformer.keepTransforming(graphics2d, x, y);
	}
	private void finishTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		this.transformer.finishTransforming(graphics2d, x, y);
		this.bUpdated = true;
		this.setSelected(this.currentShape);

		if(this.bDrawing){

			if(this.currentShape instanceof GGroup) { // grouping

				Vector<GShape> returnGroups = ((GGroup)(this.currentShape)).contains(this.shapes); // before grouping

				if(this.bGrouping) { // do grouping
					((GGroup)(this.currentShape)).grouping();

					for(int i = 0; i< returnGroups.size()-1; i++) {
						for(GShape ungroup : this.shapes) {
							if(ungroup == returnGroups.get(i)) {
								//	Rectangle re = new Rectangle();
								//	ungroup.setShape(re);
								this.shapes.remove(ungroup);
							}
						}
					}
					this.bGrouping = false;
				}

			}else { // only drawing
				this.shapes.add(this.currentShape);
				this.drawStack.push(this.currentShape);
			}
		}
	}

	private void continueTransforming(int x, int y) {
		Graphics2D graphics = (Graphics2D) this.getGraphics();
		this.transformer.continueTranforming(graphics, x, y);
	}

	// inner class
	class MouseHandler implements MouseMotionListener, MouseListener, MouseWheelListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getClickCount() == 1) {
				this.mouse1Clicked(event);
			} else if (event.getClickCount() == 2) {
				this.mouse2Clicked(event);
			}
		}

		// n point drawing
		private void mouse1Clicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if ( event.getButton() == event.BUTTON3) {
				GPopupMenu popup = new GPopupMenu(GDrawingPanel.this);
				popup.show(GDrawingPanel.this, x, y);
			}
			GShape shape = onShape(x,y);
			if(shape == null) { 
				if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState ==EDrawingState.eIdle) {
					initTransforming(null, x, y);
					eDrawingState = EDrawingState.eDrawing;
				}
			}else { 
				setSelected(shape);
			}
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState ==EDrawingState.eDrawing) {
				continueTransforming(x, y);
			}
		}
		private void mouse2Clicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState==EDrawingState.eDrawing) {
				finishTransforming(x, y);
				eDrawingState = EDrawingState.eIdle;
			}
			if(currentShape instanceof GTextBox) {
				String text = JOptionPane.showInputDialog(GConstants.textboxMessage);
				if(text != null) {
					((GTextBox)currentShape).setText(text);
				}
				repaint();
			}

		}
		@Override
		public void mouseMoved(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();

			if (currentTool.getEDrawingStyle() == EDrawingStyle.eNPoints && eDrawingState==EDrawingState.eDrawing) {
				keepTransforming(x, y);
			}
			checkCursor(x, y); 

		}

		// 2 point drawing
		@Override
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();

			if(eDrawingState ==EDrawingState.eIdle) {
				GShape shape = onShape(x,y);
				if(shape == null) { 
					if(currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) { 
						initTransforming(null, x, y);
						eDrawingState = EDrawingState.eDrawing;
					}

				} else {  
					initTransforming(shape,x,y);
					eDrawingState = EDrawingState.eTransforming;
				}
			} else {
				 released = false; // 휠 놓음
			        startPoint = MouseInfo.getPointerInfo().getLocation(); // 시작점을 휠을 놓은 지점으로 세팅.
			}
		}
		@Override
		public void mouseDragged(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();

			if(eDrawingState==EDrawingState.eTransforming) {
				keepTransforming(x,y);
			} else if (eDrawingState==EDrawingState.eDrawing) {
				if(currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					keepTransforming(x, y);
				}
			} else {
				Point curPoint = event.getLocationOnScreen(); // 휠로 댕기면서 바뀌는 좌표
		        xDiff = curPoint.x - startPoint.x; // 바뀌는 좌표 - 시작점 좌표 값 저장
		        yDiff = curPoint.y - startPoint.y;

		        dragger = true; // 드래깅 했음!
		        repaint();
			}
		}
		@Override
		public void mouseReleased(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();

			if(eDrawingState==EDrawingState.eTransforming) {
				finishTransforming(x,y);
				eDrawingState = EDrawingState.eIdle;
			} else if (eDrawingState==EDrawingState.eDrawing) {
				if(currentTool.getEDrawingStyle() == EDrawingStyle.e2Points) {
					finishTransforming(x, y);
					eDrawingState = EDrawingState.eIdle;
				}
			} else {
				 released = true; // 휠로 움직이는 거 끝
			        repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent event) {
		}

		@Override
		public void mouseExited(MouseEvent event) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			zoomer = true; // 확대 혹은 축소 시작
			//Zoom in
	        if (e.getWheelRotation() < 0) {
	            zoomFactor *= 1.1; // zoomFactor : 휠을 움직이면 그에 맞게 값이 늘거나 줄어듦.
	            repaint();
	        }
	        //Zoom out
	        if (e.getWheelRotation() > 0) {
	            zoomFactor /= 1.1;
	            repaint();
	        }
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if(pageIndex>0) return NO_SUCH_PAGE;
		Graphics2D graphics2d = (Graphics2D)graphics;
		graphics2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		for (GShape shape : this.shapes) {
			shape.draw(graphics2d);
		}
		return PAGE_EXISTS;
	}
}
