package com.rayuniverse.framework.pool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface ThreadPool {
	 public  Future submit(Runnable run);
	 public <T> Future<T> submit(Runnable task, T result);
	 public <T> Future<T> submit(Callable<T> task);
//	 public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;
//	 public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
//             long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
//	 public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
//     throws InterruptedException;
//	 public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
//             long timeout, TimeUnit unit)
//    throws InterruptedException;
//	public void shutdown();
//	public List<Runnable> shutdownNow();
}
