package tranformer;

import java.awt.Graphics2D;

import main.GConstants;
import shape.GShape;

public class GDrawer extends GTransformer {
	private static final long serialVersionUID = GConstants.serialVersionUID;
	public GDrawer(GShape shape) {
		super(shape);
	}
	public void initTransforming(Graphics2D graphics2d , int x, int y) {
		this.shape.setOrigin(x, y);
	}
	
	public void keepTransforming(Graphics2D graphics2d , int x, int y) {
		graphics2d.setXORMode(graphics2d.getBackground());
		this.shape.draw(graphics2d);
		this.shape.setPoint(x, y);
		this.shape.draw(graphics2d);
	}
	public void finishTransforming(Graphics2D graphics2d , int x, int y) {
		this.shape.setShape(this.shape.getShape());
	}
	
	public void continueTranforming(Graphics2D graphics2d , int x, int y) {
		this.shape.addPoint(x, y);
	}
}
