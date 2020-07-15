package main;


import java.awt.Cursor;
import java.awt.Font;

import menus.GColorMenu;
import menus.GEditMenu;
import menus.GFileMenu;
import menus.GMenu;
import shape.GBrush;
import shape.GGroup;
import shape.GLine;
import shape.GOval;
import shape.GPolygon;
import shape.GRectangle;
import shape.GShape;
import shape.GTextBox;
import shape.GTriangle;

public class GConstants {

	public static final long serialVersionUID = 1L;

	public GConstants() {
	}

	public enum EMainFrame {
		eWidth(800),
		eHeight(1000);

		private int value;		
		private EMainFrame(int value) {
			this.value = value;
		}	
		public int getValue() {
			return this.value;
		}
	}
	public static final String printingMessage = "Can not Print";
	public static final String rotateImageIcon = "image/rotate.png";
	public static final String textboxMessage = "�ؽ�Ʈ�� �Է��ϼ���.";
	public enum EMenubar {
		eFile(new GFileMenu("����(F)")),
		eEdit(new GEditMenu("����(E)")),
		eColor(new GColorMenu("�÷�(C)"));

		private GMenu menu;
		private EMenubar(GMenu menu) {
			this.menu = menu;
		}		
		public GMenu getMenu() {
			return this.menu;
		}
	}

	public enum EFileMenu {
		eNew("New","nnew"),
		eImageOpen("�̹��� ����","imageOpen"),
//		eclip("clip","clip"),
		eOpen("����","open"),
		eSave("����","save"),
		eSaveAs("�ٸ��̸�����","saveAs"),
		ePrint("����Ʈ","print"),
		eQuit("����","exit");

		private String title;	
		private String actionCommand;
		private EFileMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	// fileMenu Constants
	public final static char[] fileMenuKeyValue = {'N', 'I','L', 'O', 'S', 'A','P','E'};
	public final static String directoryAddress = "./data";
	public final static String imageDirectoryAddress = "./image";
	public final static String confirmDialogMessage = "���泻���� ���� �ұ��?";
	public final static String confirmDialogTitle = "����Ȯ��";
	public final static String cancelMessage = "����ϼ̽��ϴ�.";

	public enum EEditMenu{
		eUndo("�ǵ�����","undo"),
		eRedo("�ٽý���","redo"),
		eCopy("����","copy"),
		eCut("�ڸ���","cut"),
		ePaste("�ٿ��ֱ�","paste"),
		eGroup("�׷�","group"),
//		eUnGroup("�׷� ����","unGroup"),
		eDelete("����","delete"),
		eShadow("�׸���", "shadow");

		private String title;	
		private String actionCommand;
		private EEditMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	public final static char[] editMenuKeyValue = {'Z', 'Y', 'C', 'X', 'V', 'G','U','D','W'};
	
	public enum EColorMenu {
		eLineColor("���� ��","setLineColor"),
		eFillColor("ä��� ��","setFillColor"),
		eFont("��Ʈ", "setFont");

		private String title;	
		private String actionCommand;
		private EColorMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}	
	public final static char[] colorMenuKeyValue = {'L', 'P','F'};
	public final static String StrokeSize = "strokeSize";
	public final static String StrokePattern = "strokePattern";
	public final static Font font = new Font("���", Font.BOLD, 15);
	public enum EToolbar {
		eSelect("����", "����", new GGroup()),
		eRectangle("�׸�", "�׸�", new GRectangle()),
		eTextBox("�ؽ�Ʈ", "�ؽ�Ʈ", new GTextBox()),
		eOval("��","��", new GOval()),
		eLine("��","��", new GLine()),
		eBrush("��","��", new GBrush()),
		eTriangle("�ﰢ��","�ﰢ��", new GTriangle()),
		ePolygon("�ٰ���", "�ٰ���",new GPolygon());

		private String title;
		private String toolTip;
		private GShape tool;

		private EToolbar(String title,String toolTip, GShape tool) {
			this.title = title;
			this.toolTip = toolTip;
			this.tool = tool;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getToolTip() {
			return this.toolTip;
		}
		public GShape getTool() {
			return this.tool;
		}
	}

	public final static int MAXPOINTS = 100;

	public enum ECursor {
		eDefault(new Cursor(Cursor.DEFAULT_CURSOR)),
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),		
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eRotate(new Cursor(Cursor.HAND_CURSOR)), 
		eMove(new Cursor(Cursor.MOVE_CURSOR));

		private Cursor cursor;

		private ECursor(Cursor cursor) {
			this.cursor = cursor;
		}		
		public Cursor getCursor() {
			return this.cursor;
		}
	}
	public enum EPopupMenu{
		eCopy("����","copy"),
		eCut("�ڸ���","cut"),
		ePaste("�ٿ��ֱ�","paste"),
		eFront("�� ������", "shapeGoFront"),
		eBack("�� �ڷ�","shapeGoBack");
		
		private String title;	
		private String actionCommand;
		private EPopupMenu(String title,String actionCommand) {
			this.title = title;
			this.actionCommand = actionCommand;
		}		
		public String getTitle() {
			return this.title;
		}
		public String getActionCommand() {
			return this.actionCommand;
		}
	}
	
}
