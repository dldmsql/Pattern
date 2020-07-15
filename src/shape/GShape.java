package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import main.GConstants;
import tool.DeepCloner;

public abstract class GShape implements Serializable{
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;

	public enum EDrawingStyle {
		e2Points, eNPoints
	}
	protected EDrawingStyle eDrawingStyle;

	protected Shape shape; 
	private Color lineColor, fillColor;
	private int stroke;
	private float[] dash;
	private Shape shadow;
	private boolean bShadow;

	// components
	protected GAnchors anchors;
	protected GAnchors.EAnchors eSelectedAnchor;

	// working variables
	protected boolean bSelected;

	public GShape() {
		this.lineColor = null;
		this.fillColor = null;
		this.bSelected = false;
		this.bShadow = false;
		this.anchors = new GAnchors(); // 메모리 차지 때문에 미리 new 하고
		this.stroke = 0;
		this.dash = null;
		
	}

	public EDrawingStyle getEDrawingStyle() { return this.eDrawingStyle; }

	public GAnchors.EAnchors getESelectedAnchor(){return this.eSelectedAnchor;}

	public Shape getShape() {return this.shape;}

	public void setShape(Shape shape) {this.shape = shape;}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
		if(this.bSelected) {
			this.anchors.setBounds(this.shape.getBounds()); // 여기서 getBounds 넘겨
		}
	}
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setStroke(int index) {
		this.stroke = index;
	}
	public void setStrokeDash(float[] dash) {
		this.dash = dash;
	}

	public void setShadow(boolean bShadow) {
		this.bShadow = bShadow;
	}
	public boolean isShadow() {
		return this.bShadow;
	}
	public void shadow() {
		DeepCloner deep = new DeepCloner();
		AffineTransform at = new AffineTransform();
		shadow = (Shape) deep.clone(this.shape);
		at.translate(7, 7);
		this.shadow = at.createTransformedShape(this.shadow);
	}
	public void draw(Graphics2D graphics2d) { 
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if(bShadow) {
			this.shadow();
			graphics2d.setColor(Color.DARK_GRAY);
			graphics2d.fill(shadow);
		}
		if(this.fillColor != null) {
			graphics2d.setColor(this.fillColor);
			graphics2d.fill(this.shape);
		}
		if(this.lineColor != null) {
			graphics2d.setColor(this.lineColor);
		}
		if(this.stroke != 0) {
			graphics2d.setStroke(new BasicStroke(this.stroke));
		}
		if(this.dash != null) {
			graphics2d.setStroke(new BasicStroke(this.stroke, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 1.0f, this.dash, 0.0f));
		}
		graphics2d.draw(this.shape);
		graphics2d.setStroke(new BasicStroke(1));

		if(this.bSelected) {
			this.anchors.draw(graphics2d);
		}
	}
	public GShape clone() {
		try {
			ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
			ObjectOutputStream OOS = new ObjectOutputStream(BAOS);
			OOS.writeObject(this);

			ByteArrayInputStream BAIS = new ByteArrayInputStream(BAOS.toByteArray());
			ObjectInputStream OIS = new ObjectInputStream(BAIS);
			return (GShape) OIS.readObject();
			//			return this.getClass().getDeclaredConstructor().newInstance();
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean contains(int x, int y) { 
		boolean bContains = false;
		this.eSelectedAnchor = null;
		if(this.bSelected) {
			this.eSelectedAnchor = this.anchors.contains(x,y);
		}
		if(this.eSelectedAnchor == null) {
			if(this.shape.contains(new Point(x,y))) {
				this.eSelectedAnchor = GAnchors.EAnchors.MM; // move
				bContains = true;
			}
		} else bContains = true;
		return bContains; 
	}

	public Rectangle getBounds() {
		return this.shape.getBounds();
	}

	public abstract void setOrigin(int x, int y) ;
	public abstract void setPoint(int x, int y) ;
	public abstract void addPoint(int x, int y) ;

}
