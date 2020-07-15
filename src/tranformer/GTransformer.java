package tranformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;

import main.GConstants;
import shape.GShape;

public abstract class GTransformer implements Serializable{
	private static final long serialVersionUID = GConstants.serialVersionUID;
	
	protected GShape shape;
	protected Point oldPoint;
	
	public GTransformer(GShape shape) { 
		this.shape = shape; // ���� ����
		this.oldPoint = new Point(0,0);
	}
	
	public abstract void initTransforming(Graphics2D graphics2d , int x, int y);
	
	public abstract void keepTransforming(Graphics2D graphics2d , int x, int y);
	
	public abstract void finishTransforming(Graphics2D graphics2d , int x, int y);
	
	public abstract void continueTranforming(Graphics2D graphics2d , int x, int y); // ������ 4���� ������ ���� ��
}
