package sis.studentinfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseCatalog {
	private List<Session> sessions = new ArrayList<Session>();
	public void add(Session session) {
		sessions.add(session);
	}

	public List<Session> getSessions(){
		return sessions;
	}
	
	public void clearAll(){
		sessions.clear();
	}
	//使用对象流而不是数据流
	/*public void store(String filename) throws IOException{
		DataOutputStream output = null;
		try {
			output = new DataOutputStream(new FileOutputStream(filename));
			output.writeInt(sessions.size());
			for(Session session: sessions){
				output.writeLong(session.getStartDate().getTime());
				output.writeInt(session.getNumberOfCredits());
				output.writeUTF(session.getDepartment());
				output.writeUTF(session.getNumber());
			}
			
		} finally {
			output.close();
		}
	}
	
	public void load(String filename) throws IOException{
		DataInputStream input = null;
		try {
			input = new DataInputStream(new FileInputStream(filename));
			int numberOfSessions = input.readInt();
			for (int i = 0; i < numberOfSessions; i++) {
				Date startDate = new Date(input.readLong());
				int credits = input.readInt();
				String department = input.readUTF();
				String number = input.readUTF();
				Course course = new Course(department, number);
				Session session = CourseSession.create(course, startDate);
				session.setNumberOfCredits(credits);
				sessions.add(session);
			}
		} finally {
			input.close();
		}
	}*/
	
	public void store(String filename) throws IOException{
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(filename));
			output.writeObject(sessions);
		} finally {
			output.close();
		}
	}
	
	public void load(String filename) throws IOException,ClassNotFoundException{
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(filename));
			sessions = (List<Session>)input.readObject();
		} finally {
			input.close();
		}
	}
	
}
