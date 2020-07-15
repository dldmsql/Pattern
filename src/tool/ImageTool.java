//package tool;
//
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//
//import javax.imageio.ImageIO;
//
//public class ImageTool {
//	
//	public ImageTool() {
//	}
//	
//	 private void writeObject(ObjectOutputStream out) throws IOException {
//	        out.defaultWriteObject();
//	        out.writeInt(images.size()); // how many images are serialized?
//	        for (BufferedImage eachImage : images) {
//	            ImageIO.write(eachImage, "png", out); // png is losslessdhdh
//	        }
//	    }
//
//	    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
//	        in.defaultReadObject();
//	        final int imageCount = in.readInt();
//	        images = new ArrayList<BufferedImage>(imageCount);
//	        for (int i=0; i<imageCount; i++) {
//	            images.add(ImageIO.read(in));
//	        }
//	    }
//}
