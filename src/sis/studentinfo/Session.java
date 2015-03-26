package sis.studentinfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

abstract public class Session implements Comparable<Session>,Iterable<Student>  {
	private String department;
	private String number;
	public List<Student> students = new ArrayList<Student>();
	protected Date startDate;//protected表示只有该变量所在类和其子类可以访问这个变量,但是要注意同一包中的非子类也可以访问protected元素
	private int numberOfCredits;
	private static int count;
	private URL url;
	
	protected Session(String department, String number ,Date startDate){
		this.department = department;
		this.number = number;
		this.startDate = startDate;
	}
	
	public int compareTo(Session that) {
		int compare = this.getDepartment().compareTo(that.getDepartment());
		if (compare == 0) {
			compare = this.getNumber().compareTo(that.getNumber());
		}
		return compare;
	}

	public void setNumberOfCredits(int numberOfCredits) {
		this.numberOfCredits = numberOfCredits;
	}

	public String getDepartment(){
		return department;
	}
	
	public String getNumber(){
		return number;
	}

	public int getNumberOfStudents() {//int -2147483648~2147483647,java中的数字不是对象	
		return students.size();
	}
	
	public void enroll (Student student)
	{
		student.addCredits(numberOfCredits);
		students.add(student);
	}
	
	Student get(int index){
		return students.get(index);
	}
	
	protected Date getStartDate() {
		return startDate;
	}

	public List<Student> getAllStudents()
	{
		return students;
	}

	abstract protected int getSessionLength();
	
	public Date getEndDate(){
		GregorianCalendar calendar =new GregorianCalendar();
		calendar.setTime(getStartDate());//存入表示课程开始的对象
		final int daysInWeek = 7;
		final int daysFromFridayToMonday = 3;
		int numberOfDays = getSessionLength() * daysInWeek - daysFromFridayToMonday;	
		calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);	
		Date endDate = calendar.getTime();
		return endDate;
	}

	double averageGpaForPartTimeStudents(){
		double total = 0.0;
		int count = 0;
		for (Student student: students) {//使用迭代器的方式Iterator<Student> it = students.iterator();it.hasNext();
			if (student.isFullTime()) {
				continue;
			}
			count++;
			total += student.getGpa();
		}
		if (count == 0) {
			return 0.0;
		}
		return total/count;
	}
	
	public Iterator<Student> iterator(){
		return students.iterator();
	}

	public void setUrl (String urlString) throws SessionException{
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {//在尽可能接近源头的地方捕获异常，并进行日志，让后在传递异常。
			log(e);
			throw new SessionException(e);//创建并抛出应用程序特定的异常，可以封装产生异常的特点实现细节
			//这里封装了异常的根源MalformedURLException
		}
	}
	
	public URL getUrl(){
		return url;
	}
	
	private void log(Exception e){
		e.printStackTrace();//将存储在异常当中的堆栈跟踪打印出来（缺省打印到系统控制台）
	}
}
