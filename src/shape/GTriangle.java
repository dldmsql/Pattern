package shape;

import java.awt.Polygon;

import main.GConstants;

public class GTriangle extends GShape {
	private static final long serialVersionUID = GConstants.serialVersionUID;
	private int tMoveX, tMoveY;
	
	public GTriangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		
		this.shape = new Polygon(); 
	}

	@Override
	public void addPoint(int x, int y) {
	}
	@Override
	   public void setOrigin(int x, int y) {
	      Polygon polygon = (Polygon)this.shape;

	      polygon.addPoint(x, y);
	      polygon.addPoint(x, y);
	      polygon.addPoint(x, y);
	      
	      this.tMoveX = x;
	      this.tMoveY= y;
	      
	   }

	   @Override
	   public void setPoint(int x, int y) {
	      Polygon polygon = (Polygon)this.shape;
	      polygon.reset();

	      double cx = (this.tMoveX+x)/2;
	      double cy= (this.tMoveY+y)/2;
	      double angle = Math.toRadians(120);
	      
	      polygon.addPoint((this.tMoveX+x)/2, this.tMoveY);
	      int x1 = this.newPointX((this.tMoveX+x)/2, this.tMoveY, cx, cy, angle);
	      int y1 = this.newPointY((this.tMoveX+x)/2, this.tMoveY, cx, cy, angle);
	      polygon.addPoint(x1, y1);
	      int x2 = this.newPointX(x1, y1, cx, cy, angle);
	      int y2 = this.newPointY(x1, y1, cx, cy, angle);
	      polygon.addPoint(x2,y2);
	      
	   }

	   private int newPointX(double x, double y, double cx, double cy, double angle) {
	      int changePoint = (int) ((x - cx) * Math.cos(angle) - (y - cy) * Math.sin(angle) + cx);

	      return changePoint;
	   }

	   private int newPointY(int x, int y, double cx, double cy, double angle) {
	      int changePoint = (int) ((x - cx) * Math.sin(angle) + (y - cy) * Math.cos(angle) + cy);
	      return changePoint;
	   }	
}