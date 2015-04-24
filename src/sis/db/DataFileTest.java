package sis.db;

import java.io.IOException;
import java.io.Serializable;

import junit.framework.TestCase;

public class DataFileTest extends TestCase{
	private static final String ID1 = "12345";
	private static final String ID2 = "23456";
	private static final String FILEBASE = "DataFileTest";
	private DataFile db;
	private TestData testData1;
	private TestData testData2;
	
	protected void setUp() throws IOException{
		db = DataFile.create(FILEBASE);
		assertEquals(0, db.size());
		
		testData1 = new TestData(ID1, "datumla", 1);
		testData2 = new TestData(ID2, "datum2a", 2);
	}

	public void testAdd() throws IOException {
		db.add(ID1, testData1);
		assertEquals(1, db.size());
		
		db.add(ID2, testData2);
		assertEquals(2, db.size());
		
		assertTestDataEquals(testData1, (TestData)db.findBy(ID1));
		assertTestDataEquals(testData2, (TestData)db.findBy(ID2));
	}
	
	public void testPersistence() throws IOException{
		db.add(ID1, testData1);
		db.add(ID2, testData2);
		db.close();
		
		db = DataFile.open(FILEBASE);//打开已有的数据表
		assertEquals(2, db.size());
		
		assertTestDataEquals(testData1, (TestData)db.findBy(ID1));
		assertTestDataEquals(testData2, (TestData)db.findBy(ID2));
		
		db = DataFile.create(FILEBASE);//静态工厂方法create，删除以前的数据表
		assertEquals(0, db.size());
	}
	
	public void testKeyNotFound() throws IOException{
		assertNull(db.findBy(ID2));
	}
	
	private void assertTestDataEquals(TestData expected, TestData actual){
		assertEquals(expected.id, actual.id);
		assertEquals(expected.field1, actual.field1);
		assertEquals(expected.field2, actual.field2);
	}
	
	static class TestData implements Serializable{
		String id;
		String  field1;
		int field2;
		TestData(String id, String field1, int field2) {
			this.id = id;
			this.field1 = field1;
			this.field2 = field2;
		}
	}
}
