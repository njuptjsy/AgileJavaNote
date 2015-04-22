package sis.studentinfo;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

public class CourseCatalogTest extends TestCase{
	private CourseCatalog catalog;
	private Session session1;
	private Session session2;
	private Course course1;
	private Course course2;
	
	protected void setUp(){
		catalog = new CourseCatalog();
		course1 = new Course("a", "1");
		course2 = new Course("a", "1");
		
		session1 = CourseSession.create(course1, DateUtil.createDate(2005, 1, 15));
		session1.setNumberOfCredits(3);
		session2 = CourseSession.create(course2, DateUtil.createDate(2001,1,17));
		session2.setNumberOfCredits(5);
		session2.enroll(new Student("a"));
		
		catalog.add(session1);
		catalog.add(session2);
	}
	
	public void testStoreAndLoad() throws Exception{
		final String filename = "CourseCatalogTest.testAdd.txt";
		catalog.store(filename);
		catalog.clearAll();
		assertEquals(0, catalog.getSessions().size());
		catalog.load(filename);
		List<Session> sessions = catalog.getSessions();
		assertEquals(2, sessions.size());
		assertSession(session1,sessions.get(0));
		assertSession(session2,sessions.get(1));
		
		Session session = sessions.get(1);
		assertSession(session2, session);
		Student student = session.getAllStudents().get(0);
		assertEquals("a", student.getLastName());
	}
	
	private void assertSession(Session expected, Session retrieved){
		assertNotSame(expected, retrieved);//Asserts that two objects do not refer to the same object
		assertEquals(expected.getNumberOfCredits(), retrieved.getNumberOfCredits());
		assertEquals(expected.getStartDate(), retrieved.getStartDate());
		assertEquals(expected.getDepartment(), retrieved.getDepartment());
		assertEquals(expected.getNumber(), retrieved.getNumber());
	}
}
