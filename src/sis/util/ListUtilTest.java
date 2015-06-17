package sis.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

public class ListUtilTest extends TestCase{
	public void testPad(){
		final int count = 5;
		List<Date> list = new ArrayList<Date>();
		final Date element = new Date();
		ListUtil.pad(list, element, count);
		assertEquals(count,list.size());
		for (int i = 0; i < count; i++) {
			assertEquals("unexpected element at " + i, element , list.get(i));
		}
	}
}
