package sis.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Thread{
	//private List<Search> queue =Collections.synchronizedList(new LinkedList<Search>());
	private BlockingQueue<Search> queue = new LinkedBlockingQueue<Search>();
	private ResultsListener listener;
	static final String START_MSG = "started";
	static final String END_MSG = "finished";
	
	public Server(ResultsListener listener){
		this.listener = listener;
		start();
	}
	
	public void run(){
		while(true){
			try {
				execute(queue.take());//在队列空时会阻塞线程，直到队列中有个新的对象可用
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			//Thread.yield();//A hint to the scheduler that the current thread is willing to yield its current use of a processor. The scheduler is free to ignore this hint. 
		}
	}
	
	public void add(Search search) throws Exception {//每次都从链表尾部添加，形成了队列的数据结构
		queue.put(search);
	}
	
	public void execute(final Search search){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				log(START_MSG, search);
				search.execute();
				log(END_MSG, search);
				listener.executed(search);
				completeLog.addAll(threadlog.get());//Appends all of the elements in the specified collection to the end of this list并且各添加操作是一个原子操作
			}
		}).start();
	}
	
	public void shutDown() throws Exception{
		this.interrupt();
	}
	
	private static ThreadLocal<List<String>> threadlog = new ThreadLocal<List<String>>(){//在每一个线程第一次得到ThreadLocal实例是下面方法被调用
		@Override
		protected List<String> initialValue(){
			return new ArrayList<String>();
		}
	};
	
	private List<String> completeLog = Collections.synchronizedList(new ArrayList<String>());
	
	public List<String> getLog(){
		return completeLog;
	}
	
	private void log(String message, Search search){
		threadlog.get().add(search + " " + message + "at" + new Date());
	}
}
