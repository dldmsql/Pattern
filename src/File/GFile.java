package File;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GFile {

	private File file;

	public GFile() {
		this.file = null;
	}
	// fileMenu에서 쓰려고 만든거야.
	public Object readObject(File file) { // 자바 오브젝트는 한번에 읽어올 수 있어. 부모한테만 명령하면 돼. 다이얼로그에서 얘 호출해서 파일 찾아서 오브젝트 읽어서 리턴시켜
		this.file = file; // 파일을 저장해놔. serialize를 설명하겠어.
		try {
			FileInputStream fileInputStream	 = new FileInputStream(this.file); // file에서 읽어서 bit로 serialize시켜주는 애야. bit array를 쭉 읽어주는 애.
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream); //데이터가 쭉 들어오면 bit로 읽어오면 자바의 object로 바꿔주는 애야. // 자바마크업이 들어있어.
			Object object = objectInputStream.readObject();
			objectInputStream.close();

			return object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null; // 에러나면 null
	}
	public void saveObject(Object object, File file) {
		this.file = file; 
		try {
			FileOutputStream fileOutputStream	 = new FileOutputStream(this.file); 
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
