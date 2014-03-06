package algorithmoi.ask2.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {
	class MyThread implements Runnable{
		private int curIndex;

		@Override
		public void run() {
			do{
				MyThreadPool.lock.lock();
				this.curIndex = MyThreadPool.localIndex;
				MyThreadPool.localIndex += (MyThreadPool.array.size() / (MyThreadPool.nThreads * MyThreadPool.step) );
				if (MyThreadPool.localIndex >= MyThreadPool.array.size() ){
					MyThreadPool.start = this.curIndex;
					MyThreadPool.end = MyThreadPool.array.size() - 1;
					MyThreadPool.localIndex -= (MyThreadPool.array.size() / (MyThreadPool.nThreads * MyThreadPool.step) );
					MyThreadPool.step *= 2;
				}
				MyThreadPool.lock.unlock();
				
				if (MyThreadPool.key == MyThreadPool.array.get(MyThreadPool.localIndex))
					MyThreadPool.found = true;
				
				if (MyThreadPool.key < MyThreadPool.array.get(MyThreadPool.localIndex))
				{
					MyThreadPool.end = MyThreadPool.localIndex;
					MyThreadPool.start = this.curIndex;
					MyThreadPool.localIndex -= (MyThreadPool.array.size() / (MyThreadPool.nThreads * MyThreadPool.step) );
					
					if (MyThreadPool.localIndex < 0)
						MyThreadPool.localIndex = 0;
					MyThreadPool.step *= 2;
					
				}
			} while((MyThreadPool.found == false) && (MyThreadPool.array.size() / (MyThreadPool.nThreads * MyThreadPool.step) ) != 0);
		}
	}
	
	private static int start, end, key, step, localIndex, nThreads;
	private static boolean found;
	private static List<Integer> array;
	private ExecutorService executor; 
	public ExecutorService getExecutor() {
		return executor;
	}


	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	private List<MyThread> threads;
	
	final static Lock lock = new ReentrantLock();
	
	public MyThreadPool(int numThreads)
	{
		super();
		MyThreadPool.nThreads = numThreads;
		executor = Executors.newFixedThreadPool(numThreads);
		threads = new ArrayList<MyThread>();
		for(int i=0; i<numThreads; i++)
			threads.add(new MyThread());
	}
	
	
	public void runThreads(){
		for(MyThread t : threads)
			executor.execute(t);
		
		executor.shutdown();
	}

	/**
	 * @return the found
	 */
	public static boolean isFound() {
		return found;
	}

	/**
	 * @param found the found to set
	 */
	public static void setFound(boolean found) {
		MyThreadPool.found = found;
	}

	/**
	 * @return the localIndex
	 */
	public static int getLocalIndex() {
		return localIndex;
	}

	/**
	 * @param localIndex the localIndex to set
	 */
	public static void setLocalIndex(int localIndex) {
		MyThreadPool.localIndex = localIndex;
	}

	/**
	 * @return the end
	 */
	public static int getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public static void setEnd(int end) {
		MyThreadPool.end = end;
	}

	/**
	 * @return the key
	 */
	public static int getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public static void setKey(int key) {
		MyThreadPool.key = key;
	}

	/**
	 * @return the step
	 */
	public static int getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public static void setStep(int step) {
		MyThreadPool.step = step;
	}

	/**
	 * @return the start
	 */
	public static int getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public static void setStart(int start) {
		MyThreadPool.start = start;
	}

	/**
	 * @return the array
	 */
	public static List<Integer> getArray() {
		return array;
	}

	/**
	 * @param array the array to set
	 */
	public static void setArray(List<Integer> array) {
		MyThreadPool.array = array;
	}
}
