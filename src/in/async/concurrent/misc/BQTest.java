package in.async.concurrent.misc;

import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BQTest {

	public static void main(String args[]) {
		doBQTest();
	}

	public static void doBQTest() {
		final BlockingQueue<Student> bq = new LinkedBlockingQueue<Student>();

		// CONSUMER::
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				Calendar cal = null;
				cal = Calendar.getInstance();
				try {
					Student obj = bq.take();
					System.out.println("Student::" + obj.getNo());
					if (obj.getNo() == 5) {
						throw new RuntimeException();
					}
					System.out.println("Consuming::" + obj.getName() + "::" + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
					System.out.println("BQSize: " + bq.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 5, TimeUnit.SECONDS);

		// PRODUCER
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 10) {
					Student obj = new Student(i++, "Raj");
					bq.add(obj);
				}
			}
		}, 0, 5, TimeUnit.MINUTES);

	}
}

class Student {
	private int no;
	private String name;

	public Student(int no, String name) {
		this.no = no;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}