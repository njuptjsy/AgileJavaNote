package sis.studentinfo;

import java.io.IOException;

import junit.framework.TestCase;

public class StudentDirectoryTest extends TestCase{
	private StudentDirectory dir;
	
	protected void setUp() throws IOException{
		dir = new StudentDirectory();
	}
	
	protected void tearDown() throws IOException{
		dir.close();
		dir.remove();
	}
	
	public void testRandomAccess() throws IOException{
		final int numberOfStudents = 10;
		
		for (int i = 0; i < numberOfStudents; i++)
		{
			addStudent(dir,i);
		}
		dir.close();
		
		dir = new StudentDirectory();
		for(int i = 0; i < numberOfStudents; i++){
			verifyStudentLookup(dir,i);
		}
	}
	
	void addStudent(StudentDirectory directory,int i) throws IOException{
		String id = "" +i;
		Student student = new Student(id);
		student.setId(id);
		student.addCredits(i);
		directory.add(student);
	}
	
	void verifyStudentLookup(StudentDirectory directory,int i) throws IOException{
		String id = "" + i;
		Student student = directory.findById(id);
		assertEquals(id, student.getLastName());
		assertEquals(id, student.getId());
		assertEquals(i, student.getCredits());
	}
}
