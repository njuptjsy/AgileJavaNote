package sis.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import sis.studentinfo.Student;


public class ReportCardTest extends TestCase{
	private ReportCard card;
	
	protected void setUp(){
		card = new ReportCard();
	}
	
	public void testMessage(){
		//ReportCard card = new ReportCard();//已经在setUp中声明过了
		assertEquals(ReportCard.A_MESSAGE,card.getMessage(Student.Grade.A));
		assertEquals(ReportCard.B_MESSAGE,card.getMessage(Student.Grade.B));
		assertEquals(ReportCard.C_MESSAGE,card.getMessage(Student.Grade.C));
		assertEquals(ReportCard.D_MESSAGE,card.getMessage(Student.Grade.D));
		assertEquals(ReportCard.F_MESSAGE,card.getMessage(Student.Grade.F));
	}
	//下面三种不同的方法分别测试了不同的容器对象内容是否一致
	public void testKeys(){
//		Set<Student.Grade> expectedGrades = new HashSet<Student.Grade>();
//		expectedGrades.add(Student.Grade.A);
//		expectedGrades.add(Student.Grade.B);
//		expectedGrades.add(Student.Grade.C);
//		expectedGrades.add(Student.Grade.D);
//		expectedGrades.add(Student.Grade.F);
//		Refactor
		
		Set<Student.Grade> expectedGrades = EnumSet.allOf(Student.Grade.class);
		
		
		//Set<Student.Grade> grades = new HashSet<Student.Grade>();
		Set<Student.Grade> grades = EnumSet.noneOf(Student.Grade.class);
		for (Student.Grade grade: card.getMessage().keySet()) {
			grades.add(grade);
		}
		assertEquals(expectedGrades, grades);
		assertTrue(expectedGrades.equals(grades));
	}
	
	public void testValues(){
		List<String> expectedMessages = new ArrayList<String>();
		expectedMessages.add(ReportCard.A_MESSAGE);
		expectedMessages.add(ReportCard.B_MESSAGE);
		expectedMessages.add(ReportCard.C_MESSAGE);
		expectedMessages.add(ReportCard.D_MESSAGE);
		expectedMessages.add(ReportCard.F_MESSAGE);
		
		Collection<String> messages = card.getMessage().values();
		for(String message:messages){
			assertTrue(expectedMessages.contains(message));
		}
		assertEquals(expectedMessages.size(), messages.size());
		assertFalse(expectedMessages.equals(messages));//这里要注意的是Set和list的equals方法都是经过重载的，他们都不是比较两个引用是否指向同一个对象
		//这里之所以为false是因为用一个list去和一个Collection进行比较，这些容器类的equals方法都要求比较的两个对象是同一个数据结构
		assertFalse(messages.equals(expectedMessages));
	}
	
	public void testEntries(){
		Set<Entry> entries = new HashSet<Entry>();

		for(Map.Entry<Student.Grade, String> entry: card.getMessage().entrySet()){
			entries.add(new Entry(entry.getKey(),entry.getValue()));
		}
		
		Set<Entry> expectedEntries = new HashSet<Entry>();
		expectedEntries.add(new Entry(Student.Grade.A, ReportCard.A_MESSAGE));
		expectedEntries.add(new Entry(Student.Grade.B, ReportCard.B_MESSAGE));
		expectedEntries.add(new Entry(Student.Grade.C, ReportCard.C_MESSAGE));
		expectedEntries.add(new Entry(Student.Grade.D, ReportCard.D_MESSAGE));
		expectedEntries.add(new Entry(Student.Grade.F, ReportCard.F_MESSAGE));

		//assertEquals(expectedEntries, entries);
		assertTrue(expectedEntries.equals(entries));
	}
	
	class Entry{//defined Entry in this class used in testEntries
		private Student.Grade grade;
		private String message;
		Entry(Student.Grade grade,String message) {
			this.grade = grade;
			this.message = message;
		}
		
		@Override
		public boolean equals(Object object){
			if (object.getClass() != this.getClass()) {
				return false;
			}
			Entry that = (Entry)object;
			return this.grade == that.grade && this.message.equals(that.message);
		}
		
		@Override
		public int hashCode(){//wrong without overwrite 
			//在比较一个集合中的两个元素是否相等的时候，需要比较这些元素的hashCode是否相等,如果相等再去比较equals是否为true
			final int hashMultipliper = 41;
			int result = 7;
			result = result * hashMultipliper + grade.hashCode();
			result = result * hashMultipliper + message.hashCode();
			return result;
		}
	}
}
