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

	public void add(String key, Object object) throws IOException{//添加对象到db同时在keyFile中记录下索引信息
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
		db.seek(position);//快速将内部未接指针移动到底层的任何位置，getFilePointer方法可以返回文件指针的当前指针

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
		db.readFully(bytes);//从RandomAccessFile中读出相应的数量的字节，填充到bytes数组中
		return readObject(bytes);
	}

	private Object readObject(byte[] bytes) throws IOException{//先将字节数组包装在字节数组流中在将字节数组流包装在对象流中
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
		db = new RandomAccessFile(new File(dataFileName), "rw");//r:read-only;rw: read-write;rws:同步数据/元数据更新的读写;rwd:同步数据更新的读写，后两种数据更安全的被写入存储设备中，即使在系统崩溃是也会保存，rws确保内容和元数据都持久化，rwd只确保内容
	}

	public void deleteFiles() {
		IOUtil.delete(dataFileName, keyFileName);
	}

	private byte[] getBytes(Object object)throws IOException{//将对象序列化，RandomAccesFile本身不支持保存对象，必须想把对象转化成字符数组
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);
		outputStream.writeObject(object);
		outputStream.flush();
		byte[] bytes = byteStream.toByteArray();
		outputStream.close();
		return bytes;
	}
}
