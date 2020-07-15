package shape;

import java.awt.Rectangle;

import main.GConstants;

public class GRectangle extends GShape implements Cloneable {
	private static final long serialVersionUID = GConstants.serialVersionUID;

	public GRectangle() {
		this.eDrawingStyle = EDrawingStyle.e2Points;
		
		this.shape = new Rectangle(); // �̰� �Ҵ������ ��.!! �׷��� ���߿� shape���� �� �� �� �־�.
	}

	@Override
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0,0);
	}

	@Override
	public void setPoint(int x, int y) { // setPoint�� rectangle�� ����� ����
		Rectangle rectangle = (Rectangle) this.shape;
		int newWidth = x- rectangle.x; 
		int newHeight = y- rectangle.y;
		rectangle.setSize(newWidth,newHeight);
	}

	@Override
	public void addPoint(int x, int y) {
		
	}
	
}
