package sis.search;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Server extends Thread{
	//private List<Search> queue =Collections.synchronizedList(new LinkedList<Search>());
	private BlockingQueue<Search> queue = new LinkedBlockingQueue<Search>();
	private ResultsListener listener;
	
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
	
	private void execute(Search search) {
		search.execute();
		listener.executed(search);
	}
	
	public void shutDown() throws Exception{
		this.interrupt();
	}
}
