package sis.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

import sis.studentinfo.Student;
import junit.framework.TestCase;

public class StudentUITest extends TestCase{
	static private final String name = "Leo Xerces Schmoo";
	public void testCreateStudent() throws IOException{
		StringBuffer expectedOutput = new StringBuffer();
		StringBuffer input = new StringBuffer();
		setUp(expectedOutput,input);
		byte[] buffer = input.toString().getBytes();

		InputStream inputStream = new ByteArrayInputStream(buffer);
		//BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));//将输入字节流包装成字符流
		OutputStream outputStream = new ByteArrayOutputStream();
		//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

		InputStream consoleIn = System.in;
		PrintStream consoleOut = System.out;
		System.setIn(inputStream);
		System.setOut(new PrintStream(outputStream));
		try {
			StudentUI ui = new StudentUI();
			ui.run();
			assertEquals(expectedOutput.toString(), outputStream.toString());
			assertStudent(ui.getAddedStudents());
		} finally {
			System.setIn(consoleIn);//恢复系统的标准输入输出流定向
			System.setOut(consoleOut);
		}
		

	}

	private String line(String input){
		return String.format("%s%n",input);
	}

	private void setUp(StringBuffer expectedOutput, StringBuffer input) {
		expectedOutput.append(StudentUI.MENU);
		input.append(line(StudentUI.ADD_OPTION));
		expectedOutput.append(StudentUI.NAME_PROMPT);
		input.append(line(name));
		expectedOutput.append(line(StudentUI.ADDED_MESSAGE));
		expectedOutput.append(StudentUI.MENU);
		input.append(line(StudentUI.QUIT_OPTION));
	}
	
	private void assertStudent(List<Student> students){
		assertEquals(1, students.size());
		Student student = students.get(0);
		assertEquals(name, student.getName());
	}
}
