package shape;

import java.awt.Point;

import main.GConstants;

public class GLine extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;
	private java.awt.geom.Line2D line;

	public GLine() {
		this.eDrawingStyle = EDrawingStyle.e2Points;

		this.shape = new java.awt.geom.Line2D.Double(0,0,0,0);
		this.line = (java.awt.geom.Line2D)this.shape;
	}

	@Override
	public void setOrigin(int x, int y) {
		this.line.setLine(x,y,x,y);
	}

	@Override
	public void setPoint(int x, int y) { 
		this.line.setLine(this.line.getX1(),this.line.getY1(),x,y);
	}

	@Override
	public void addPoint(int x, int y) {

	}
	@Override
	public boolean contains(int x, int y) { 
		//Line2D.Float line = (Line2D.Float) this.shape;
		boolean bContains = false;
		eSelectedAnchor = null;
		if(this.bSelected) {
			eSelectedAnchor = this.anchors.contains(x,y);
		}
		if(eSelectedAnchor == null) {
			if(this.shape.getBounds().contains(new Point(x, y))) {
				eSelectedAnchor = GAnchors.EAnchors.MM; // move
				bContains = true;
			}
		} else bContains = true;
		return bContains; 
	}


}
