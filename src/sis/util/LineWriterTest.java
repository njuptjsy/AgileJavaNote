package sis.util;

import java.io.*;

import sis.db.TestUtil;
import junit.framework.TestCase;

public class LineWriterTest extends TestCase{
	
	public void testMutipleRecords() throws IOException{
		final String file = "LineWriterTest.testCreate.txt";
		try {
			LineWriter.write(file,new String[]{"a","b"});
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				assertEquals("a", reader.readLine());
				assertEquals("b", reader.readLine());
				assertNull(reader.readLine());
			} finally{
				if (reader != null) {
					reader.close();
				}
			}
		} finally {
			TestUtil.delete(file);
		}
	}
}
