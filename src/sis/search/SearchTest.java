package sis.search;

import java.io.FileNotFoundException;
import java.io.IOException;

import sis.db.TestUtil;
import sis.util.LineWriter;
import junit.framework.TestCase;

public class SearchTest extends TestCase{
	//private static final String URL = "http://www.langrsoft.com";
	public static final String[] TEST_HTML = {
		"<html>",
		"<body>",
		"Book: aglie java, by jeff langr<br />",
		"Synopsis:Mr Langr teaches you <br />",
		"Java via test-driven development.<br />",
		"</body></html>"
	};
	
	public static final String FILE = "testFileSearch.html";
	public static final String URL = "file:" + FILE;
	
	protected void setUp() throws IOException {
		TestUtil.delete(FILE);
		LineWriter.write(FILE,TEST_HTML);
	}
	
	protected void tearDown() throws IOException{
		TestUtil.delete(FILE);
	}
	
	public void testCreate() throws IOException{
		Search search = new Search(URL,"x");
		assertEquals(URL, search.getUrl());
		assertEquals("x", search.getText());
	}
	
	public void testPositiveSearch() throws IOException {
		Search search = new Search(URL, "Jeff Langr");
		search.execute();
		assertTrue(search.matches() >= 1);
		assertFalse(search.errored());
	}
	
	public void testNegativeSearch() throws IOException{
		final String unlikelyText = "mama cass elliott";
		Search search = new Search(URL, unlikelyText);
		search.execute();
		assertEquals(0, search.matches());
		assertFalse(search.errored());
	}
	
	public void testErroredSearch() throws IOException{
		final String badUrl = URL + "/z2468.html";
		Search search = new Search(badUrl, "404");
		search.execute();
		//assertTrue(search.matches() >= 1);
		//url为网络地址时，没有网址会跳出404 not found
		
		assertTrue(search.errored());
		assertEquals(FileNotFoundException.class, search.getError().getClass());
	}
}
