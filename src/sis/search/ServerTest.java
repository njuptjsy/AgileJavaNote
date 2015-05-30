package sis.search;

import java.util.List;

import sis.db.TestUtil;
import sis.util.LineWriter;
import junit.framework.TestCase;

public class ServerTest extends TestCase{
	private int numberOfResults = 0;
	private Server server;
	private static final long TIMEOUT = 3000L;
	private static final String[] URLS = {
		SearchTest.URL, SearchTest.URL, SearchTest.URL};
	
	protected void setUp() throws Exception{
		TestUtil.delete(SearchTest.FILE);
		LineWriter.write(SearchTest.FILE, SearchTest.TEST_HTML);
		
		ResultsListener listener = new ResultsListener() {
			@Override
			public void executed(Search search) {
				numberOfResults++;
			}
		};
		
		server = new Server(listener);//新建对象同时线程就启动了
	}
	
	protected void tearDown() throws Exception{
		assertTrue(server.isAlive());
		server.shutDown();
		server.join(3000);//线程终止是要时间的，这里是不如tearDown中的代码向下执行
		assertFalse(server.isAlive());
		TestUtil.delete(SearchTest.FILE);
	}
	
	public void testSearch() throws Exception {
		long start = System.currentTimeMillis();
		executeSearches();
		long elapsed = System.currentTimeMillis() - start;
		assertTrue(elapsed < 20);
		waitForResults();
	}
	
	private void executeSearches() throws Exception{
		for (String url:URLS){
			server.add(new Search(url, "xxx"));
		}
	}

	public void testLogs() throws Exception{
		executeSearches();
		waitForResults();
		verifyLogs();
	}
	
	private void verifyLogs() {//检查log的数量是不是搜索次数的两倍
		List<String> list = server.getLog();
		assertEquals(URLS.length * 2, list.size());
		for (int i = 0; i < URLS.length; i += 2) {
			verifySameSearch(list.get(i),list.get(i + 1));
		}
	}
	
	private void verifySameSearch(String startSearchMsg, String endSearchMsg) {//检查每一对start和end是不是属于同一个搜索线程
		String startSearch = substring(startSearchMsg,Server.START_MSG);
		String endSearch = substring(endSearchMsg,Server.END_MSG);
		assertEquals(startSearch, endSearch);
	}
	
	private String substring(String string,String upTo){//返回upto字符串之前的字符串字串
		int endIndex = string.indexOf(upTo);
		assertTrue("didn't find" + upTo + "in" + string, endIndex != 1);
		return string.substring(0,endIndex);
	}

	private void waitForResults(){//挂起当前线程，直到搜索线程执行完毕
		long start = System.currentTimeMillis();
		while (numberOfResults < URLS.length) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
			if (System.currentTimeMillis() - start > TIMEOUT) {
				fail("timeout");
			}
		}
	}
	
}
