package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import main.GConstants;

public class GGroup extends GRectangle{

	private static final long serialVersionUID = GConstants.serialVersionUID;
	private Vector<GShape> gShapes;
	private Area combineShape;
	private AffineTransform at;

	public GGroup() {
		this.at = new AffineTransform();
		this.gShapes = new Vector<GShape>();
	}

	public Vector<GShape> contains(Vector<GShape> shapes) {
		for(GShape shape : shapes) {
			if(this.getShape().contains(shape.getShape().getBounds())) {
				this.gShapes.add(shape);
				shape.setSelected(true);
			}
		}
		return this.gShapes;
	}
	@SuppressWarnings("static-access")
	public void grouping() {
		this.combineShape = new Area();
		for(GShape shape : this.gShapes) {
			combineShape.add(new Area(shape.getShape()));
			shape.setShape(at.getTranslateInstance(0,0).createTransformedShape(combineShape));
		}
	}

	@SuppressWarnings("static-access")
	public void unGrouping() {
		for(GShape shape : gShapes) {
			combineShape.subtract(new Area(shape.getShape()));
			shape.setShape(at.getTranslateInstance(0, 0).createTransformedShape(combineShape));
		}
	}
	public void draw(Graphics2D graphics2d) { 
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics2d.draw(this.shape);
		graphics2d.setStroke(new BasicStroke(1));

		if(this.bSelected) {
			this.anchors.draw(graphics2d);
		}
	}

	public void setLineColor(Color lineColor) {
		for(GShape gShape : this.gShapes) {
			gShape.setLineColor(lineColor);
		}
	}
	public void setFillColor(Color fillColor) {
		for(GShape gShape : this.gShapes) {
			gShape.setFillColor(fillColor);
		}
	}

	public GShape clone() {
		try {
			return this.getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
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
			for(GShape gShaep : this.gShapes) {
				if(gShaep.contains(x,y)) {
					this.eSelectedAnchor = GAnchors.EAnchors.MM; // move
					bContains = true;
					break;
				}
			}
		} else bContains = true;
		return bContains; 
	}
	public Rectangle getBounds() {
		Rectangle unionRectangle = new Rectangle();
		for(GShape gShape : this.gShapes) {
			unionRectangle.union(gShape.getBounds());
		}
		this.shape = unionRectangle;
		return this.shape.getBounds();
	}
}
