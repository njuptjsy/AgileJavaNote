package sis.studentinfo;


import java.util.Arrays;

import junit.framework.TestCase;

public class PerformanceTest extends TestCase{
	private static final double tolerance = 0.005;
	public void testAverage() {
		Performance  performance = new Performance();
		performance.setNumOfTests(4);
		performance.set(0,98);
		performance.set(1,92);
		performance.set(2,81);
		performance.set(3,72);
	
		assertEquals(92, performance.get(1));
		assertEquals(85.75, performance.average(),tolerance);
	}
	
	public void testInitialization(){
		Performance performance = new Performance();
		performance.setScores(75, 72, 90, 60);
		assertEquals(74.25, performance.average(), tolerance);
		
		performance.setScores(100, 90);
		assertEquals(95.0, performance.average(), tolerance);
	}
	
	public void testArraysEquals(){
		int[] a = {1,2,3};
		int[] b = {1,2,3};
		assertTrue(Arrays.equals(a, b));
		assertFalse(a == b);
		assertFalse(a.equals(b));
		//�������鱾����Ӧ�������õ��ںź�equals�Ƚ϶�����ȣ�java�ṩ��arrays.equals�������ڱȽ��������������
	}
	
	public void testAverageForNoScores(){
		Performance performance = new Performance();
		assertEquals(0.0, performance.average());
	}
	
}
