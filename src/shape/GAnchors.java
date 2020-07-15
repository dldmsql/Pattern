package shape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import javax.imageio.ImageIO;

import main.GConstants;

public class GAnchors implements Serializable{
	// attributes
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	transient BufferedImage image;
	
	private final int ANCHOR_W = 10; // final = const
	private final int ANCHOR_H = 10; 
	private final int ANCHORS_RRHEIGHT = 40;
	
	public enum EAnchors{
		NW, NN, NE, EE, SE, SS, SW, WW, RR, MM
	}
	
	private Vector<Ellipse2D> anchors;
	
	public GAnchors() {
		try {
			this.image = ImageIO.read(new File(GConstants.rotateImageIcon));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.anchors = new Vector<Ellipse2D>();
		
		for(int i =0; i<EAnchors.values().length-1; i++) { // 앵커 미리 생성. -1을 하는 이유는 MM 때문에. MM은 앵커를 그리면 안돼(moving)
			Ellipse2D anchor = new Ellipse2D.Double();
			this.anchors.add(anchor);
		}
	}
	
	private void setCoordinate(EAnchors eAnchor, Ellipse2D anchor,Rectangle bounds) {
		int x =0, y=0, w=ANCHOR_W, h=ANCHOR_H;
		
		switch(eAnchor) {
		case NW:
			x = bounds.x;
			y = bounds.y;
			break;
		case NN:
			x = bounds.x + bounds.width/2;
			y = bounds.y;
			break;
		case NE:
			x = bounds.x + bounds.width;
			y = bounds.y;
			break;
		case EE:
			x = bounds.x + bounds.width;
			y = bounds.y + bounds.height/2;
			break;
		case SE:
			x = bounds.x + bounds.width;
			y = bounds.y + bounds.height;
			break;
		case SS:
			x = bounds.x + bounds.width/2;
			y = bounds.y + bounds.height;
			break;
		case SW:
			x = bounds.x;
			y = bounds.y + bounds.height;
			break;
		case WW:
			x = bounds.x;
			y = bounds.y + bounds.height/2;
			break;	
		case RR:
			x = bounds.x + bounds.width/2;
			y = bounds.y - ANCHORS_RRHEIGHT;
			break;
		default:
			break;
		}
		x = x-w/2;
		y = y-h/2;

		anchor.setFrame(x,y,w,h);
	}

	public void setBounds(Rectangle bounds) {
		for(int i =0; i<EAnchors.values().length-1; i++) {
			Ellipse2D anchor = this.anchors.get(i); // 만든 거에서 뽑아서
			this.setCoordinate(EAnchors.values()[i], anchor, bounds); // 좌표 계산.
		}
	}

	public void draw(Graphics2D graphics2d) {
		for(Ellipse2D anchor : this.anchors) {
			Color penColor = graphics2d.getColor();
			graphics2d.setColor(graphics2d.getBackground()); // back - white
			graphics2d.fill(anchor);
			graphics2d.setColor(penColor); // back - white
			graphics2d.draw(anchor);
		}
		Ellipse2D rotateAnchor = this.anchors.lastElement();
		graphics2d.drawImage(this.image, rotateAnchor.getBounds().x, rotateAnchor.getBounds().y,
				rotateAnchor.getBounds().width, rotateAnchor.getBounds().height, null);
	}

	public EAnchors contains(int x, int y) {
		for(int i =0; i<EAnchors.values().length-1; i++) {
			if(this.anchors.get(i).contains(x,y)) {
				return EAnchors.values()[i];
			}
		}
		return null;
	}
}
