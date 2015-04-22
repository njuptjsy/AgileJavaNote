package sis.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import junit.framework.TestCase;
import sis.studentinfo.*;
//静态导入：这样在这个类中可以直接使用ReportConstant类中的NEWLINE类常量
//import static sis.report.ReportConstant.NEWLINE;
public class RosterReporterTest extends TestCase {
	private Session session;
	
	protected void setUp() {
		session = CourseSession.create(new Course("ENGL", "101"),DateUtil.createDate(2001,1,6));
		session.enroll(new Student("A"));
		session.enroll(new Student("B"));
	}
	
	public void testRosterReport() throws IOException{
		
		Writer writer = new StringWriter();
		new RosterReporter(session).writeReport(writer);
		assertReportContents(writer.toString());
//System.out.println(rosterReport);//将这行代码和页面左端对其，表示这是一行临时代码，方便删除
		
	}

	private void assertReportContents(String rosterReport) {
		assertEquals(String.format(RosterReporter.ROSTER_REPORT_HEADER + 
				"A%n" + 
				"B%n" +
				RosterReporter.ROSTER_REPORT_FOOTER, session.getNumberOfStudents()),rosterReport);
	}

	public void testFileReport() throws IOException{
		final String filename = "testFiledReport.txt";
		try {
			delete(filename);
			new RosterReporter(session).writeReport(filename);
			StringBuffer buffer = new StringBuffer();
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null){//readLine方法使用系统的line.separator进行每一行之间的分隔
				buffer.append(line + "%n");
			}
			reader.close();
			assertReportContents(String.format(buffer.toString()));
			
		} finally {
			delete(filename);
		}
		
	}

	private void delete(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			assertTrue("unable to delete " + filename, file.delete());
		}
	}
	
}
