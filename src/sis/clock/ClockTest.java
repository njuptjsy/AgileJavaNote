package sis.clock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;

public class ClockTest extends TestCase{
	private Clock clock;
	private Lock lock;
	private Condition receivedEnoughTics;
	
	protected void setUp() {
		lock = new ReentrantLock();
		receivedEnoughTics = lock.newCondition();//返回用来与此 Lock 实例一起使用的 Condition 实例。
		//在使用内置监视器锁时，返回的 Condition 实例支持与 Object 的监视器方法（wait、notify 和 notifyAll）相同的用法。
	}
	
	public void testClock() throws Exception {
		final int seconds = 2;
		final List<Date> tics = new ArrayList<Date>();
		ClockListener listener = createClockListener(tics,seconds);
		
		clock = new Clock(listener);//新建好就启动了
		lock.lock();
		try {
			receivedEnoughTics.await();//阻塞，挂起当前线程当必须记得的在finally中手动的释放锁
		} finally {
			lock.unlock();
		}
		clock.stop();
		verify(tics, seconds);
	}

	private ClockListener createClockListener(final List<Date> tics,final int seconds) {
		return new ClockListener() {
			private int count = 0;
			@Override
			public void update(Date date) {
				tics.add(date);
				if (++count == seconds) {
					lock.lock();
					try {
						receivedEnoughTics.signalAll();//通知被挂起的线程启动
					} finally {//必须在finally块中调用，手动释放锁
						lock.unlock();
					}
				}
			}
		};
	}

	private void verify(List<Date> tics, int seconds) {//验证等待了5秒
		assertEquals(seconds, tics.size());
		for (int i = 1; i < seconds; i++) {
			assertEquals(1, getSecondsFromLast(tics, i));
		}
	}

	private long getSecondsFromLast(List<Date> tics, int i) {//验证每个tics之间相差的是一秒
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tics.get(i));
		int now = calendar.get(Calendar.SECOND);
		calendar.setTime(tics.get(i - 1));
		int then = calendar.get(Calendar.SECOND);
		if (now == 0) {//如果真好是第60秒，这是秒位为0
			now = 60;
		}
		return now - then;
	}
	
}
