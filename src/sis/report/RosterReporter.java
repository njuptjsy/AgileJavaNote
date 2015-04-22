package sis.report;

import sis.studentinfo.*;
import static sis.report.ReportConstant.NEWLINE;

import java.io.*;
public class RosterReporter {
	//重构使用静态导入，导入常用类常量
	//static final String NEWLINE = System.getProperty("line.separator");
	static final String ROSTER_REPORT_HEADER = "Student%n-%n";//'%n'行分隔符  结果为特定于平台的行分隔符  
	static final String ROSTER_REPORT_FOOTER = "%n# students = %d%n";
	private Session session;
	private Writer writer; 
	
	RosterReporter(Session session) {
		this.session = session;
	}
	
	/*String getReport(){
		StringBuilder buffer = new StringBuilder();
		buffer.append(ROSTER_REPORT_HEADER);//页眉，ROSTER_REPORT_HEADER是在同一个类中CourseSession.ROSTER_REPORT_HEADER的简写
		for (Student student: session.getAllStudents())
		{
			buffer.append(student.getName());
			buffer.append(NEWLINE);
		}
		buffer.append(ROSTER_REPORT_FOOTER + session.getAllStudents().size() + NEWLINE);//页尾信息
		return buffer.toString();
	}重构删除*/
	
	void writeReport(Writer writer) throws IOException{
		this.writer =  writer;
		writeHeader();
		writrBody();
		writeFooter();
	}
	
	private void writeHeader() throws IOException{
		writer.write(String.format(ROSTER_REPORT_HEADER));
	}
	
	private void writrBody() throws IOException{
		for (Student student: session.getAllStudents()){
			writer.write(String.format(student.getName() + "%n"));
		}
	}
	
	private void writeFooter() throws IOException{
		writer.write(String.format(ROSTER_REPORT_FOOTER,session.getAllStudents().size()));
	}
	
	void writeReport(String filename) throws IOException{
		Writer bufferedWriter = new BufferedWriter(new FileWriter(filename));
		try {
			writeReport(bufferedWriter);
		} finally {
			bufferedWriter.close();//如果忘记关闭文件资源会遇到文件被锁住的情况，而且缓存中的信息在关闭文件之前不会被保存到文件中
		}
	}
}

