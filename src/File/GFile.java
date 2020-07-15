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
	// fileMenu���� ������ ����ž�.
	public Object readObject(File file) { // �ڹ� ������Ʈ�� �ѹ��� �о�� �� �־�. �θ����׸� ����ϸ� ��. ���̾�α׿��� �� ȣ���ؼ� ���� ã�Ƽ� ������Ʈ �о ���Ͻ���
		this.file = file; // ������ �����س�. serialize�� �����ϰھ�.
		try {
			FileInputStream fileInputStream	 = new FileInputStream(this.file); // file���� �о bit�� serialize�����ִ� �־�. bit array�� �� �о��ִ� ��.
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream); //�����Ͱ� �� ������ bit�� �о���� �ڹ��� object�� �ٲ��ִ� �־�. // �ڹٸ�ũ���� ����־�.
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
		return null; // �������� null
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
