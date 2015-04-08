package sis.studentinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class CourseTest extends TestCase{
	public void testCreate(){
		Course course = new Course("CMSC","120");
		assertEquals("CMSC", course.getDepartment());
		assertEquals("120", course.getNumber());
	}
	
	public void testEquality(){
		Course courseA = new Course("NURS", "201");
		Course courseAPrime = new Course("NURS", "201");
		assertEquals(courseA, courseAPrime);
		
		Course courseB = new Course("ARTH", "330");
		assertFalse(courseA.equals(courseB));
		
		assertEquals(courseA, courseA);
		
		Course courseAPrime2 = new Course("NURS", "201");
		assertEquals(courseAPrime, courseAPrime2);
		assertEquals(courseA, courseAPrime2);
		
		assertEquals(courseAPrime, courseA);
		
		assertEquals(courseA, courseAPrime);
		
		assertFalse(courseA.equals(null));
		
		assertFalse(courseA.equals("CMSC-120"));
		
		List<Course> list = new ArrayList<Course>();
		list.add(courseA);
		assertTrue(list.contains(courseAPrime));
		
		Map<Course, String> map = new HashMap<Course, String>();
		map.put(courseA, "");
		assertTrue(map.containsKey(courseAPrime));
	}
	
	public void testHashCode(){
		Course courseA = new Course("NURS", "201");
		Course courseAPrime = new Course("NURS", "201");
		
		Map<Course, String> map = new HashMap<Course,String>();
		map.put(courseA, "");
		assertTrue(map.containsKey(courseAPrime));
		
		assertEquals(courseA.hashCode(), courseAPrime.hashCode());
		assertEquals(courseA.hashCode(), courseA.hashCode());
	}
	
	public void testHashCodePerformance(){
		final int count = 10000;
		long start = System.currentTimeMillis();
		Map<Course, String> map = new HashMap<Course,String>();
		for (int i = 0; i < count; i ++){
			Course course = new Course("C"+i, ""+i);
			map.put(course, "");
		}
		long stop = System.currentTimeMillis();
		long elapsed = stop - start;
		final long arbitraryThreshold = 200;
		assertTrue("elapsed time = "+ elapsed,elapsed < arbitraryThreshold);//如果断言失败则第一个字符串将作为信息打出
	}
}
