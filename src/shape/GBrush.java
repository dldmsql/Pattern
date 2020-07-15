package shape;

import java.awt.geom.GeneralPath;

import main.GConstants;

public class GBrush extends GShape {
	private static final long serialVersionUID = GConstants.serialVersionUID;
	private GeneralPath path;
	
	public GBrush() {
		this.eDrawingStyle = EDrawingStyle.eNPoints;
		
		this.shape = new GeneralPath();
		this.path = (GeneralPath) this.shape;
	}
	
	@Override
	public void setOrigin(int x, int y) {
		this.path.moveTo(x, y);
	}

	@Override
	public void setPoint(int x, int y) {
		this.path.lineTo(x, y);
	}

	@Override
	public void addPoint(int x, int y) {

	}

}
