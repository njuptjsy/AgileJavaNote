package sis.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

public class DataFile {
	public static final String DATA_EXT = ".db";
	public static final String KEY_EXT = ".idx";

	private String dataFileName;
	private String keyFileName;

	private RandomAccessFile db;
	private KeyFile keys;

	private DataFile(String filebase, boolean deleteFiles) throws IOException{
		dataFileName = filebase + DATA_EXT;
		keyFileName = filebase + KEY_EXT;

		if (deleteFiles) {
			deleteFiles();
		}
		openFiles();
	}

	public static DataFile create(String filebase) throws IOException{
		return new DataFile(filebase, true);
	}

	public static DataFile open(String filebase) throws IOException{
		return new DataFile(filebase, false);
	}

	public void add(String key, Object object) throws IOException{//��Ӷ���dbͬʱ��keyFile�м�¼��������Ϣ
		long position = db.length();

		byte[] bytes = getBytes(object);
		db.seek(position);//return offset position measured in bytes from the beginning of the file, at which to set the file pointer.
		db.write(bytes, 0, bytes.length);//Writes len bytes from the specified byte array starting at offset off to this file

		keys.add(key, position, bytes.length);
	}

	public Object findBy(String id) throws IOException{
		if (!keys.containsKey(id)) {
			return null;
		}

		long position = keys.getPosition(id);
		db.seek(position);//���ٽ��ڲ�δ��ָ���ƶ����ײ���κ�λ�ã�getFilePointer�������Է����ļ�ָ��ĵ�ǰָ��

		int length = keys.getLength(id);
		return read(length);
	}

	public int size(){
		return keys.size();
	}

	public void close() throws IOException{
		keys.close();
		db.close();
	}

	private Object read(int length) throws IOException {
		byte[] bytes = new byte[length];
		db.readFully(bytes);//��RandomAccessFile�ж�����Ӧ���������ֽڣ���䵽bytes������
		return readObject(bytes);
	}

	private Object readObject(byte[] bytes) throws IOException{//�Ƚ��ֽ������װ���ֽ����������ڽ��ֽ���������װ�ڶ�������
		ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
		try {
			try {
				return input.readObject();
			} catch (ClassNotFoundException unlikely) {
				return null;
			}
		} finally{
			input.close();
		}
	}

	private void openFiles() throws IOException{
		keys = new KeyFile(keyFileName);
		db = new RandomAccessFile(new File(dataFileName), "rw");//r:read-only;rw: read-write;rws:ͬ������/Ԫ���ݸ��µĶ�д;rwd:ͬ�����ݸ��µĶ�д�����������ݸ���ȫ�ı�д��洢�豸�У���ʹ��ϵͳ������Ҳ�ᱣ�棬rwsȷ�����ݺ�Ԫ���ݶ��־û���rwdֻȷ������
	}

	public void deleteFiles() {
		IOUtil.delete(dataFileName, keyFileName);
	}

	private byte[] getBytes(Object object)throws IOException{//���������л���RandomAccesFile����֧�ֱ�����󣬱�����Ѷ���ת�����ַ�����
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);
		outputStream.writeObject(object);
		outputStream.flush();
		byte[] bytes = byteStream.toByteArray();
		outputStream.close();
		return bytes;
	}
}
