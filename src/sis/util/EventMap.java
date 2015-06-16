package sis.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventMap <K extends Date, V> extends MultiHashMap<K, V>{//EventMap<Date, String> events = new EventMap<Date,String>();
	
	public List<V> getPastEvents(){
		List<V> events = new ArrayList<V>();
		for (Map.Entry<K, List<V>> entry: entrySet()) {//entry代表单一的Map元素
			K date = entry.getKey();
			if (hasPassed(date)) {
				events.addAll(entry.getValue());
			}
		}
		return events;
	}

	private boolean hasPassed(K date) {
		Calendar when = new GregorianCalendar();
		when.setTime(date);
		Calendar today = new GregorianCalendar();
		if (when.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
			return when.get(Calendar.YEAR) < today.get(Calendar.YEAR);
		}
		else {
			return when.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR);
		}
	}

	private Set<Map.Entry<K, List<V>>> entrySet() {
		return map.entrySet();//将map中的元素取出以集合形式保存
	}
}
