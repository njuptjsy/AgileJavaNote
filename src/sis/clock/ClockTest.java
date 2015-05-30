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
		receivedEnoughTics = lock.newCondition();//����������� Lock ʵ��һ��ʹ�õ� Condition ʵ����
		//��ʹ�����ü�������ʱ�����ص� Condition ʵ��֧���� Object �ļ�����������wait��notify �� notifyAll����ͬ���÷���
	}
	
	public void testClock() throws Exception {
		final int seconds = 2;
		final List<Date> tics = new ArrayList<Date>();
		ClockListener listener = createClockListener(tics,seconds);
		
		clock = new Clock(listener);//�½��þ�������
		lock.lock();
		try {
			receivedEnoughTics.await();//����������ǰ�̵߳�����ǵõ���finally���ֶ����ͷ���
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
						receivedEnoughTics.signalAll();//֪ͨ��������߳�����
					} finally {//������finally���е��ã��ֶ��ͷ���
						lock.unlock();
					}
				}
			}
		};
	}

	private void verify(List<Date> tics, int seconds) {//��֤�ȴ���5��
		assertEquals(seconds, tics.size());
		for (int i = 1; i < seconds; i++) {
			assertEquals(1, getSecondsFromLast(tics, i));
		}
	}

	private long getSecondsFromLast(List<Date> tics, int i) {//��֤ÿ��tics֮��������һ��
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tics.get(i));
		int now = calendar.get(Calendar.SECOND);
		calendar.setTime(tics.get(i - 1));
		int then = calendar.get(Calendar.SECOND);
		if (now == 0) {//�������ǵ�60�룬������λΪ0
			now = 60;
		}
		return now - then;
	}
	
}
