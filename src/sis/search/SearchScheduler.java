package sis.search;

import java.util.Timer;
import java.util.TimerTask;


public class SearchScheduler {
	private ResultsListener listener;
	private Timer timer;
	
	public SearchScheduler (ResultsListener listener){
		this.listener = listener;
	}
	
	public void repeat(final Search search, long interval) {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				search.execute();
				listener.executed(search);//回调
			}
		};
		timer.scheduleAtFixedRate(task, 0, interval);//参数依次是timeTask对象，延迟启动的毫秒数，一个间隔的毫秒数，每隔interval秒timer对象唤醒并调用timetask对象的run方法
	}
	
	public void stop(){
		timer.cancel();//取消定时任务
	}
}
