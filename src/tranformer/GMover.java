package tranformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.GConstants;
import shape.GShape;

public class GMover extends GTransformer {
	private static final long serialVersionUID = GConstants.serialVersionUID;
	public GMover(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.oldPoint.x = x; this.oldPoint.y = y;
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(x-this.oldPoint.x, y-this.oldPoint.y);
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		this.oldPoint.x = x; this.oldPoint.y = y;
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
	}

	@Override
	public void continueTranforming(Graphics2D graphics2d, int x, int y) {
	}

}
