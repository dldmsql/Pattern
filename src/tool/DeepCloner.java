package tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCloner {
	public Object clone(Object o) {
		try {
			ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
			ObjectOutputStream OOS = new ObjectOutputStream(BAOS);
			OOS.writeObject(o);
			
			ByteArrayInputStream BAIS = new ByteArrayInputStream(BAOS.toByteArray());
			ObjectInputStream OIS = new ObjectInputStream(BAIS);
			return OIS.readObject();
		} catch (Exception e) {e.printStackTrace(); return null;}
	}
}
