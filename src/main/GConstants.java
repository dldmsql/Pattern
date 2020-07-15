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
	public static final String textboxMessage = "텍스트를 입력하세요.";
	public enum EMenubar {
		eFile(new GFileMenu("파일(F)")),
		eEdit(new GEditMenu("편집(E)")),
		eColor(new GColorMenu("컬러(C)"));

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
		eImageOpen("이미지 열기","imageOpen"),
//		eclip("clip","clip"),
		eOpen("열기","open"),
		eSave("저장","save"),
		eSaveAs("다른이름으로","saveAs"),
		ePrint("프린트","print"),
		eQuit("종료","exit");

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
	public final static String confirmDialogMessage = "변경내용을 저장 할까요?";
	public final static String confirmDialogTitle = "변경확인";
	public final static String cancelMessage = "취소하셨습니다.";

	public enum EEditMenu{
		eUndo("되돌리기","undo"),
		eRedo("다시실행","redo"),
		eCopy("복사","copy"),
		eCut("자르기","cut"),
		ePaste("붙여넣기","paste"),
		eGroup("그룹","group"),
//		eUnGroup("그룹 해제","unGroup"),
		eDelete("삭제","delete"),
		eShadow("그림자", "shadow");

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
		eLineColor("라인 색","setLineColor"),
		eFillColor("채우기 색","setFillColor"),
		eFont("폰트", "setFont");

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
	public final static Font font = new Font("고딕", Font.BOLD, 15);
	public enum EToolbar {
		eSelect("선택", "선택", new GGroup()),
		eRectangle("네모", "네모", new GRectangle()),
		eTextBox("텍스트", "텍스트", new GTextBox()),
		eOval("원","원", new GOval()),
		eLine("선","선", new GLine()),
		eBrush("펜","펜", new GBrush()),
		eTriangle("삼각형","삼각형", new GTriangle()),
		ePolygon("다각형", "다각형",new GPolygon());

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
		eCopy("복사","copy"),
		eCut("자르기","cut"),
		ePaste("붙여넣기","paste"),
		eFront("맨 앞으로", "shapeGoFront"),
		eBack("맨 뒤로","shapeGoBack");
		
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
