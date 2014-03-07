/**
 * 
 */
package algorithmoi.ask2.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rousojohn
 *
 */
public class MultiThreadedSearcher extends Observable {
	private List<Integer> array;//int[] array;
	private int index = -1;
	
	/**
	 * @return the array
	 */
	public List<Integer> getArray() {
		return array;
	}


	/**
	 * @param array the array to set
	 */
	public void setArray(List<Integer> array) {
		this.array = array;
	}
	
	
	private ArrayList<SingleThreadSearcher> threads = new ArrayList<SingleThreadSearcher>();
	private ExecutorService executor;
	private Lock lock = new ReentrantLock();

	public MultiThreadedSearcher(List<Integer> _array, int nThreads, int key){
		this.array = _array;
		executor = Executors.newFixedThreadPool(nThreads);
		Result.threadId = -1;
		int rem = this.array.size() % nThreads;
		int start = 0, oldEnd = -1;
		int end = 0;
		for(int i=0; i<nThreads; i++)
		{
			start = end;
			if (rem > 0){
				end += 1;
				--rem;
			}
			end += this.array.size() / nThreads;
			threads.add(new SingleThreadSearcher(start, end-1,key, i==nThreads-1, i));
			
		}
	}
	
	public void runAllThreads(){
		for (SingleThreadSearcher t : threads)
			executor.execute(t);
	// This will make the executor accept no new threads
    // and finish all existing threads in the queue
    executor.shutdown();
    // Wait until all threads are finish
    try {
		executor.awaitTermination(5L,TimeUnit.SECONDS);
	} catch (InterruptedException e) {
		System.out.println("Thread Interrupted");
	}
	}
	
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}


	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
	
	

	/**
	 * Inner class SingleThreadSearcher simulating each thread
	 * @author rousojohn
	 *
	 */
	class SingleThreadSearcher implements Runnable{
		private int start, end, key, threadId;
		private boolean isLast;
		
		SingleThreadSearcher(int _start, int _end, int _key, boolean _isLast,int id){
			start = _start;
			end = _end;
			key = _key;
			isLast = _isLast;
			threadId = id;
		}
		
		@Override
		public void run() {
			//synchronized (this) {
				lock.lock();
				if (key > array.get(end) && !isLast){lock.unlock(); return;}
				int index = algorithmoi.ask2.sequential.Searcher.binarySearch(getArray(), key, start, end);
				if (index != -1 )
				{
					System.err.println("Result.threadId "+Result.threadId + "  this.threadId "+this.threadId);
					if (Result.threadId > this.threadId || Result.threadId == -1){
						MultiThreadedSearcher.this.setIndex(index);
						Result.threadId = this.threadId;
						Result.index = index;
						setChanged();
						notifyObservers();
					}
				}
				lock.unlock();
		//	} 
		}
		
	}



	/**
	 * Inner Class Result representing the result object of the multithreaded search
	 * @author rousojohn
	 *
	 */
	static class Result {
		private static int index;
		private static int threadId;
		/**
		 * @return the index
		 */
		public static int getIndex() {
			return index;
		}
		/**
		 * @param index the index to set
		 */
		public static void setIndex(int index) {
			Result.index = index;
		}
		/**
		 * @return the threadId
		 */
		public static int getThreadId() {
			return threadId;
		}
		/**
		 * @param threadId the threadId to set
		 */
		public static void setThreadId(int threadId) {
			Result.threadId = threadId;
		}
	}
}
