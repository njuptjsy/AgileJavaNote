package sis.util;

import java.awt.Event;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

public class MultiHashMapTest extends TestCase{
	private static final Date today = new Date();
	private static final Date tomorrow = new Date(today.getTime() + 8640000);
	private static final String eventA = "wake up";
	private static final String eventB = "eat";
	private MultiHashMap<Date, String> events;

	protected void setUp(){
		events = new MultiHashMap<Date, String>();
	}

	public void testCreate(){
		assertEquals(0,events.size());
	}

	public void testSingleEntry(){
		events.put(today, eventA);
		assertEquals(1, events.size());
		assertEquals(eventA, getSoleEvent(today));
	}

	public void testMultipleEntriesDifferntKey(){
		events.put(today, eventA);
		events.put(tomorrow, eventB);
		assertEquals(2, events.size());
		assertEquals(eventA, getSoleEvent(today));
		assertEquals(eventB, getSoleEvent(tomorrow));
	}

	public void testMultipleEntriesSameKey(){
		events.put(today, eventA);
		events.put(today, eventB);
		assertEquals(1, events.size());
		Collection<String> retirevedEvents = events.get(today);
		assertEquals(2, retirevedEvents.size());
		assertTrue(retirevedEvents.contains(eventA));
		assertTrue(retirevedEvents.contains(eventB));
	}

	private String getSoleEvent(Date date) {//取出MultiHashMap中键Date对应的值list
		Collection<String> retrievedEvents = events.get(date);
		assertEquals(1, retrievedEvents.size());
		Iterator<String> it = retrievedEvents.iterator();
		return it.next();
	}


}
