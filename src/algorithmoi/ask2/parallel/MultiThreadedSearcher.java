/**
 * 
 */
package algorithmoi.ask2.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author rousojohn
 *
 */
public class MultiThreadedSearcher extends Observable {
	private List<Integer> array, array2;
	private int index = -1, nThreads;
	private Map<Integer, Result> resultMap;
	
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

	
	public MultiThreadedSearcher(List<Integer> _array,List<Integer> _array2, int nThreads){
		this.array = _array;
		this.array2 = _array2;
		this.nThreads = nThreads;
		
		this.setResultMap(new TreeMap<Integer, Result>());
		
		for(int i : array2)
			this.getResultMap().put(i, new Result());
		
		this.executor = Executors.newFixedThreadPool(nThreads);
		for (int i=0; i<nThreads; i++)
			threads.add(new SingleThreadSearcher(i==nThreads-1, i));
	}
	
	
	public void runAllThreads(){
		for (SingleThreadSearcher t : threads)
			getExecutor().execute(t);
	// This will make the executor accept no new threads
    // and finish all existing threads in the queue
    getExecutor().shutdown();
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
	 * @return the executor
	 */
	public ExecutorService getExecutor() {
		return executor;
	}


	/**
	 * @param executor the executor to set
	 */
	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}






	/**
	 * @return the resultMap
	 */
	public Map<Integer, Result> getResultMap() {
		return resultMap;
	}


	/**
	 * @param resultMap the resultMap to set
	 */
	public void setResultMap(Map<Integer, Result> resultMap) {
		this.resultMap = resultMap;
	}






	/**
	 * Inner class SingleThreadSearcher simulating each thread
	 * @author rousojohn
	 *
	 */
	class SingleThreadSearcher implements Runnable{
		private int start, end, key, threadId;
		private boolean isLast;
		
		
		
		SingleThreadSearcher( boolean _isLast,int id){
			this.isLast = _isLast;
			this.threadId = id;
		}
		
		@Override
		public void run() {
			for(int i=0; i<array2.size(); i++){
				this.start = this.threadId * (array.size()/nThreads);
				this.end = this.start + (array.size()/nThreads) - 1;
				this.key = array2.get(i);
				lock.lock();
				if (key > array.get(end) && !isLast){lock.unlock(); continue;}
				int index = algorithmoi.ask2.sequential.Searcher.binarySearch(array, key,start, end);
				if (((Result)getResultMap().get(key)).threadId > this.threadId || ((Result)getResultMap().get(key)).threadId == -1){
					((Result)getResultMap().get(key)).threadId = this.threadId;
					((Result)getResultMap().get(key)).index = index;
					array.add(index, key);
				}
				lock.unlock();
			}
		}
		
	}



	/**
	 * Inner Class Result representing the result object of the multithreaded search
	 * @author rousojohn
	 *
	 */
	static class Result {
		private  int index;
		private  int threadId;
		
		Result(){
			index = -1;
			threadId = -1;
		}
		
		/**
		 * @return the index
		 */
		public  int getIndex() {
			return index;
		}
		/**
		 * @param index the index to set
		 */
		public  void setIndex(int index) {
			this.index = index;
		}
		/**
		 * @return the threadId
		 */
		public  int getThreadId() {
			return threadId;
		}
		/**
		 * @param threadId the threadId to set
		 */
		public  void setThreadId(int threadId) {
			this.threadId = threadId;
		}
	}
}
