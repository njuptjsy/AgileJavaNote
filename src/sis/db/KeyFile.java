package sis.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * keys is a hashMap used to save key 
 * each key mapping a serializable EntryDate which save the position and length of data
 *  
 * */
public class KeyFile {
	private Map<String, EntryData> keys = new HashMap<String, EntryData>();
	private File file;

	KeyFile(String fileName)throws IOException {
		file = new File(fileName);
		if (file.exists()) {
			load();
		}
	}

	void add(String key,long position, int length){
		keys.put(key, new EntryData(position,length));
	}

	int size(){
		return keys.size();
	}

	boolean containsKey(String key){
		return keys.containsKey(key);
	}

	long getPosition(String key){
		return keys.get(key).getPosition();
	}

	int getLength(String key){
		return keys.get(key).getLength();
	}

	void close() throws IOException{
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
		stream.writeObject(keys);//save before close
		stream.close();
	}

	void load() throws IOException{
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(file));
			try {
				keys = (Map<String, EntryData>)input.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} finally{
			input.close();
		}
	}
	
	static class EntryData implements Serializable{
		private long position;
		private int length;
		
		EntryData(long position, int length) {
			this.position = position;
			this.length = length;
		}
		
		private long getPosition(){
			return position;
		}
		
		private int getLength(){
			return length;
		}
	}
}
