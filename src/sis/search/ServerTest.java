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
		
		server = new Server(listener);//�½�����ͬʱ�߳̾�������
	}
	
	protected void tearDown() throws Exception{
		assertTrue(server.isAlive());
		server.shutDown();
		server.join(3000);//�߳���ֹ��Ҫʱ��ģ������ǲ���tearDown�еĴ�������ִ��
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
	
	private boolean waitForResults(){//����ǰ�̣߳�ֱ�������߳�ִ�����
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
