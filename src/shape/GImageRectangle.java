package shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GConstants;

public class GImageRectangle extends GShape implements Cloneable {
	// attribute
	private static final long serialVersionUID = GConstants.serialVersionUID;
	private BufferedImage image;
	
	public GImageRectangle(File imageFile) {
		// set Attributes
		this.eDrawingStyle = EDrawingStyle.e2Points;
		try { this.image = ImageIO.read(imageFile);	} catch (IOException e) {e.printStackTrace();}
		
		// create components
		this.shape = new Rectangle(); // 이거 할당해줘야 해.!! 그래야 나중에 shape으로 다 할 수 있어.
		
		this.setOrigin(0, 0);
		this.setPoint(this.image.getWidth(),this.image.getHeight());
	}
	@Override
	public void draw(Graphics2D graphics2d) { 
		super.draw(graphics2d);
		Rectangle bound = this.shape.getBounds();
		graphics2d.drawImage(this.image, bound.x, bound.y, bound.width, bound.height, null);
	}
	@Override
	public void setOrigin(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0,0);
	}

	@Override
	public void setPoint(int x, int y) { // setPoint는 rectangle의 사이즈만 변해
		Rectangle rectangle = (Rectangle) this.shape;
		int newWidth = x- rectangle.x; 
		int newHeight = y- rectangle.y;
		rectangle.setSize(newWidth,newHeight);
	}

	@Override
	public void addPoint(int x, int y) {
		
	}

	
}
