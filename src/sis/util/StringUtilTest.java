package sis.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import sis.studentinfo.DateUtil;
import junit.framework.TestCase;
import junit.runner.Sorter.Swapper;

public class StringUtilTest extends TestCase {
	private static final String TEXT = "this is it, isn't it";
	
	public void testOccurrencesOne() {
		assertEquals(1, StringUtil.occurrences(TEXT, "his"));
	}
	
	public void testOccurrencesNone(){
		assertEquals(0, StringUtil.occurrences(TEXT, "smelt"));
	}
	
	public void testOccurrencesMany(){
		assertEquals(3, StringUtil.occurrences(TEXT, "is"));
		assertEquals(2, StringUtil.occurrences(TEXT, "it"));
	}
	
	public void testOccurrencesSearchStringTooLarge() {
		assertEquals(0, StringUtil.occurrences(TEXT, TEXT + "sadfas"));
	}
	
	public void testConcatenateList(){
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		
		String output = StringUtil.concatenate(list);
		
		assertEquals(String.format("a%nb%n"), output);
	}
	
	public void testConcatenateFormattedDecimals(){
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(new BigDecimal("3.1416"));
		list.add(new BigDecimal("-1.4142"));
		
		String output = StringUtil.concatenateNumbers(list,3);
		assertEquals(String.format("3.142%n-1.414%n"), output);
	}
	
	public void testConcatenateFormattedIntegers(){
		List<Integer> list = new ArrayList<Integer>();
		list.add(12);
		list.add(17);
		
		String output = StringUtil.concatenateNumbers(list, 0);
		assertEquals(String.format("12%n17%n"), output);
	}
	
	//����ʹ�÷��������ͨ��������ġ���Ϊ��֪���������Ͷ��޷����������������
	public void testWildcardCapture(){
		List<String> names = new ArrayList<String>();
		names.add("alpha");
		names.add("beta");
		inPlaceReverse(names);
		assertEquals("beta", names.get(0));
		assertEquals("alpha", names.get(1));
	}

	private void inPlaceReverse(List<?> list) {//�������е�Ԫ�ش�ͷ��β����һ��
		int size = list.size();
		for (int i = 0; i < size /2; i++) {
			Swap(list,i, size - 1- i);//size-1-i�Ǻ�i��Ե�Ԫ�صı��
		}
	}

	private static <T> void Swap(List<T> list, int i,int opposite) {
		T temp = list.get(i);
		list.set(i, list.get(opposite));
		list.set(opposite, temp);
	}
	
	//������map�е�valueӦ��һ���������õ�һ���µ�map
	public void testFilter(){
		MultiHashMap<String, java.sql.Date> meetings = new MultiHashMap<String,java.sql.Date>();
		
		meetings.put("iteration start", createSqlDate(2005, 9 ,12));
		meetings.put("iteration start", createSqlDate(2005, 9, 26));
		meetings.put("VP blather", createSqlDate(2005, 9, 12));
		meetings.put("brown bags", createSqlDate(2005, 9, 14));
		
		MultiHashMap<String, Date> mondayMeetings = new MultiHashMap<String,Date>();
		MultiHashMap.filter(mondayMeetings, meetings, new MultiHashMap.Filter<Date>(){
			@Override
			public boolean apply(Date date){
				return isMonday(date);
			}
		});
	}
	
	private boolean isMonday(Date date){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
	}

	private java.sql.Date createSqlDate(int year, int month, int day) {
		Date date = DateUtil.createDate(year, month, day);
		return new java.sql.Date(date.getTime());
	}
}
