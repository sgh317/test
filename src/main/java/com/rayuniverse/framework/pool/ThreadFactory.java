package com.rayuniverse.framework.pool;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactory implements  java.util.concurrent.ThreadFactory{
	ThreadGroup group;
	AtomicInteger threadNumber = new AtomicInteger(1);
	String namePrefix;

	public ThreadFactory(String namePrefix) {
		this.namePrefix = namePrefix;
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
				.getThreadGroup();
	}

	public Thread newThread(Runnable r) {

		Thread t = new Thread(group, r, namePrefix
				+ threadNumber.getAndIncrement(), 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}

}
