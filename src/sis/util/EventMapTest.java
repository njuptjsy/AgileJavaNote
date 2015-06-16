package sis.util;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

public class EventMapTest extends TestCase{
	
	public void testGetPastEvents(){
		EventMap<Date, String> events = new EventMap<Date,String>();
		final Date today = new Date();
		final Date yesterday = new Date(today.getTime() - 86400000);
		events.put(today, "sleep");
		final String descriptionA = "birthday";
		final String descriptionB = "drink";
		events.put(yesterday, descriptionA);
		events.put(yesterday, descriptionB);
		List<String> descriptions = events.getPastEvents();
		assertTrue(descriptions.contains(descriptionA));
		assertTrue(descriptions.contains(descriptionB));
	}
}
