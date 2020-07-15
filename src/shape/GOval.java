package shape;

import java.awt.geom.Ellipse2D;

import main.GConstants;

public class GOval extends GShape implements Cloneable {

	private static final long serialVersionUID = GConstants.serialVersionUID;


	public GOval() {
		this.eDrawingStyle = EDrawingStyle.e2Points;

		
		this.shape = new Ellipse2D.Float(); 
	}

	@Override
	public void setOrigin(int x, int y) {
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		ellipse.setFrame(x,y,0,0);
	}

	@Override
	public void setPoint(int x, int y) { 
		Ellipse2D ellipse = (Ellipse2D) this.shape;
		int newWidth = (int) (x- ellipse.getX()); 
		int newHeight = (int) (y- ellipse.getY());
		ellipse.setFrame(ellipse.getX(),ellipse.getY(), newWidth, newHeight); // width, height만 바꿔주는 함수가 없어.
	}

	@Override
	public void addPoint(int x, int y) {
		
	}

}
