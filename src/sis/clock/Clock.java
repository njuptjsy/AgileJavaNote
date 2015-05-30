package sis.clock;

import java.util.Date;

public class Clock implements Runnable{
	private ClockListener listener;
	private boolean run = true;
	
	public Clock(ClockListener listener){
		this.listener = listener;
		new Thread(this).start();
	}
	
	public void stop(){
		run = false;
	}
	
	public void run(){
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY - 1);
		long lastTime = System.currentTimeMillis();
		while (run) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
			long now = System.currentTimeMillis();
			if ((now / 1000) - (lastTime / 1000) >= 1) {
				listener.update(new Date());
				lastTime = now;
			}
		}
	}
	
}
