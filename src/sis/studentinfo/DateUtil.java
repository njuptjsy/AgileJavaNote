package sis.studentinfo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	private DateUtil(){
		//避免生产一些无意义的DateUtil对象
	}
	public static Date createDate (int year, int month ,int date){//根据传入的年月日返回相应的Date对象
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, date);
		return calendar.getTime();
	}
}
