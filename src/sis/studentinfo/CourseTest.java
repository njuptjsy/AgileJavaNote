package sis.studentinfo;

import junit.framework.TestCase;

public class CourseTest extends TestCase{
	public void testCreate(){
		Course course = new Course("CMSC","120");
		assertEquals("CMSC", course.getDepartment());
		assertEquals("120", course.getNumber());
	}
}
