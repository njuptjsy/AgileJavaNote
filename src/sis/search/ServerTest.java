package sis.search;

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
		for (String url:URLS)
			server.add(new Search(url, "XXX"));
		long elapsed = System.currentTimeMillis() - start;
		long averageLatency = elapsed / URLS.length;
		assertTrue(averageLatency < 20);
		assertTrue(waitForResults());
	}
	
	private boolean waitForResults(){//挂起当前线程，直到搜索线程执行完毕
		long start = System.currentTimeMillis();
		while (numberOfResults < URLS.length) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
			if (System.currentTimeMillis() - start > TIMEOUT) {
				return false;
			}
		}
		return true;
	}
}
