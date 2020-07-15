package shape;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GConstants;

public class GTextBox extends GRectangle{

	private static final long serialVersionUID = GConstants.serialVersionUID;
	private String text;
	private Font font;

	public GTextBox() {
		this.text = "Hello";
		this.font = new Font("Helvetica", Font.PLAIN, 12);

	}
	public void setText(String text) {
		this.text = text;
	}
	public void setFont(Font font) {
		this.font = font;
	}

	@Override
	public void draw(Graphics2D graphics2d) {
		graphics2d.setStroke(new BasicStroke(1));
		graphics2d.setFont(this.font);
		if(this.getBounds().getMaxX()-this.getBounds().getMinX() > 1) {
			graphics2d.drawString(text, (int) this.getBounds().getCenterX(), (int) this.getBounds().getCenterY());
			if(this.bSelected) {
				this.anchors.draw(graphics2d);
			}	
		}
	}


}
