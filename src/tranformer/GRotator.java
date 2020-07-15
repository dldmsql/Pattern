package tranformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import main.GConstants;
import shape.GShape;

public class GRotator extends GTransformer{
	private static final long serialVersionUID = GConstants.serialVersionUID;
	private Point rotatePoint;
	private AffineTransform affineTransform;
	
	public GRotator(GShape shape) {
		super(shape);
		
		affineTransform = new AffineTransform();
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.oldPoint.x = x; this.oldPoint.y = y;
		this.rotatePoint = new Point((int) this.shape.getBounds().getCenterX(), 
				(int) this.shape.getBounds().getCenterY());
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		graphics2d.setXORMode(graphics2d.getBackground());
		this.shape.draw(graphics2d);
		
		double rotateAngle = Math.toRadians(computeAngle(this.rotatePoint, this.oldPoint, new Point(x,y)));
		affineTransform.setToRotation(rotateAngle, this.rotatePoint.getX(), this.rotatePoint.getY());
		
		this.oldPoint.x = x; this.oldPoint.y = y;
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		
		this.shape.draw(graphics2d);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
	}

	@Override
	public void continueTranforming(Graphics2D graphics2d, int x, int y) {
	}
	private double computeAngle(Point centerPoint, Point startPoint, Point endPoint) { // 점 3개를 갖고, tan로 각도를 구한다.
		double startAngle = Math.toDegrees(Math.atan2(centerPoint.getX() - startPoint.getX(),centerPoint.getY() - startPoint.getY()));
		double endAngle = Math.toDegrees(Math.atan2( centerPoint.getX() - endPoint.getX(), centerPoint.getY() - endPoint.getY()));
		double angle = startAngle-endAngle;
		
		if(angle < 0) angle += 360;
		return angle;
	}
	// affineTransform은 선으로 계산하게끔 해줘야 한다.
}
