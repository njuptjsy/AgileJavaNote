﻿package sis.studentinfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.tools.JavaCompiler;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

abstract public class Session implements Comparable<Session>,Iterable<Student>,java.io.Serializable  {
    private transient String name;
	private Course course;
	public transient List<Student> students = new ArrayList<Student>();//加入transient表示在序列化这个类对象的过程中跳过这个成员变量
	protected Date startDate;//protected表示只有该变量所在类和其子类可以访问这个变量,但是要注意同一包中的非子类也可以访问protected元素
	private int numberOfCredits;
	private static int count;
	private URL url;
	
	protected Session(Course course ,Date startDate){
		this.course = course;
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
		return course.getDepartment();
	}
	
	public String getNumber(){
		return course.getNumber();
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

	public int getNumberOfCredits(){
		return numberOfCredits;
	}

	private void writeObject(ObjectOutputStream output)throws IOException{
		output.defaultWriteObject();//将所有非transient的成员变量写入到流中
		output.writeInt(students.size());
		for (Student student: students) {
			output.writeObject(student.getName());
		}
	}
	
	private void readObject(ObjectInputStream input) throws Exception{
		input.defaultReadObject();
		students = new ArrayList<Student>();
		int size = input.readInt();
		for (int i = 0; i < size; i++) {
			String lastName = (String)input.readObject();
			students.add(Student.findByLastName(lastName));
		}
	}
}
