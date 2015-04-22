package sis.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import sis.studentinfo.Student;

public class StudentUI {
	static final String MENU = "(A)dd or (Q)uit?";
	static final String ADD_OPTION = "A";
	static final String QUIT_OPTION = "Q";
	static final String NAME_PROMPT = "Name: ";
	static final String ADDED_MESSAGE = "Added";
	
	private BufferedReader reader;
	private BufferedWriter writer;
	private List<Student> students = new ArrayList<Student>();
	
	public StudentUI() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));//这里是这个程序可以接受控制台的输入和输出
		this.writer = new BufferedWriter(new OutputStreamWriter(System.out));//并且将控制台的字节流包装在字符流中并将包装好的字符流再包装在带缓冲的字符流中
	}
	
	public void run() throws IOException{
		String line;
		do {
			write(MENU);
			line = reader.readLine();
			if (line.equals(ADD_OPTION)) {
				addStudent();
			}
		} while (!line.equals(QUIT_OPTION));
	}
	
	List<Student> getAddedStudents(){
		return students;
	}
	
	private void addStudent() throws IOException{
		write(NAME_PROMPT);
		String name = reader.readLine();//name默认在ADD_OPTION的下一行
		
		students.add(new Student(name));
		writeln(ADDED_MESSAGE);
	}

	private void writeln(String line) throws IOException{
		write(line);
		writer.newLine();
		writer.flush();
	}

	private void write(String line) throws IOException{
		writer.write(line, 0, line.length());
		writer.flush();
	}
	
	public static final void main(String[] args) throws  IOException{
		new StudentUI().run();
	}
}
