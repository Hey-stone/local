package com.fyj.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncExcutor {
	protected final static Logger logger = LoggerFactory.getLogger(AsyncExcutor.class);
	private int concurrentNum;
	private final ExecutorService asyncTaskExecutor;
	private final Semaphore asyncTaskSemaphore;

	public AsyncExcutor(int concurrentnum) {
		this.concurrentNum = concurrentnum;
		asyncTaskExecutor = Executors.newFixedThreadPool(concurrentnum);
		asyncTaskSemaphore = new Semaphore(concurrentnum);
	}

	public void async(Runnable runnable) {

		// 控制往executor添加任务，达到最大并发数时需要阻塞住防止不断加入任务导致内存溢出
		try {
			asyncTaskSemaphore.acquire();// 用于阻塞住调用线程
		} catch (Exception e) {
			logger.error("获取锁异常,", e);
		}

		asyncTaskExecutor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					runnable.run();
				} catch (Exception e) {
					logger.warn("异步任务执行出错", e);
					throw e;
				} finally {
					asyncTaskSemaphore.release();
				}
			}
		});

	}
	
	public boolean waitForComplete(long timeout) {
		if (asyncTaskSemaphore.availablePermits() == concurrentNum) {
			return true;
		}

		try {
			boolean isComplete = asyncTaskSemaphore.tryAcquire(concurrentNum, timeout, TimeUnit.MILLISECONDS);

			if (isComplete) {
				asyncTaskSemaphore.release(concurrentNum);
			}

			return isComplete;
		} catch (Exception e) {
			return false;
		}
	}

	public void shutdownNow() {
		try {
			asyncTaskExecutor.shutdownNow();
		} catch (Exception e) {
			logger.warn("shutdown error", e);
		}
	}
}
