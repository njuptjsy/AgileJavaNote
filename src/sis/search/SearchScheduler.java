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
				listener.executed(search);//�ص�
			}
		};
		timer.scheduleAtFixedRate(task, 0, interval);//����������timeTask�����ӳ������ĺ�������һ������ĺ�������ÿ��interval��timer�����Ѳ�����timetask�����run����
	}
	
	public void stop(){
		timer.cancel();//ȡ����ʱ����
	}
}
