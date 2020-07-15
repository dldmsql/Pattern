package tranformer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import main.GConstants;
import shape.GAnchors.EAnchors;
import shape.GShape;

public class GResizer extends GTransformer {
	private static final long serialVersionUID = GConstants.serialVersionUID;
	private EAnchors anchor;
	
	public GResizer(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.oldPoint.x = x; this.oldPoint.y = y;
		this.anchor = this.shape.getESelectedAnchor();
	}
	private double getDX(double x, double width) {return (x-this.oldPoint.x)/width;}
	private double getDY(double y, double height) {return (y-this.oldPoint.y)/height;}
	
	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) { // 원래 도형을 기준으로 몇배 길이가 늘어났는지를 구해.
		AffineTransform affineTransform = new AffineTransform();
		Rectangle bound = this.shape.getBounds();
		double dx = this.getDX(x, bound.getWidth());
		double dy = this.getDY(y, bound.getHeight());
		
		switch (this.anchor) {
		case NW:
			affineTransform.setToTranslation(bound.getMinX() + bound.getWidth(), bound.getMinY() + bound.getHeight());
			affineTransform.scale(1-dx, 1-dy);
			affineTransform.translate(-(bound.getMinX() + bound.getWidth()), -(bound.getMinY() + bound.getHeight()));
			break;
		
		case NN:
			affineTransform.setToTranslation(0, bound.getMinY() + bound.getHeight());
			affineTransform.scale(1, 1-dy);
			affineTransform.translate(0, -(bound.getMinY() + bound.getHeight()));
			break;
		
		case NE:
			affineTransform.setToTranslation(bound.getMinX(), bound.getMinY() + bound.getHeight());
			affineTransform.scale(1+dx, 1-dy);
			affineTransform.translate(-(bound.getMinX()), -(bound.getMinY() + bound.getHeight()));
			break;
		
		case WW:
			affineTransform.setToTranslation(bound.getMinX() + bound.getWidth(), 0);
			affineTransform.scale(1-dx, 1);
			affineTransform.translate(-(bound.getMinX() + bound.getWidth()), 0);
			break;
		
		case EE:
			affineTransform.setToTranslation(bound.getMinX(), 0);
			affineTransform.scale(1+dx, 1);
			affineTransform.translate(-(bound.getMinX()), 0);
			break;
		
		case SW:
			affineTransform.setToTranslation(bound.getMinX() + bound.getWidth(), bound.getMinY());
			affineTransform.scale(1-dx, 1+dy);
			affineTransform.translate(-(bound.getMinX() + bound.getWidth()), -(bound.getMinY()));
			break;
		
		case SS:
			affineTransform.setToTranslation(0, bound.getMinY());
			affineTransform.scale(1, 1+dy);
			affineTransform.translate(0, -(bound.getMinY()));
			break;
		
		case SE:
			affineTransform.setToTranslation(bound.getMinX(), bound.getMinY());
			affineTransform.scale(1+dx, 1+dy);
			affineTransform.translate(-(bound.getMinX()), -(bound.getMinY()));
			break;
		default:
			break;
		}
		
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
//		this.shape.keepTransforming(x, y);
		this.oldPoint.x = x; this.oldPoint.y = y;

	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueTranforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub

	}

}
