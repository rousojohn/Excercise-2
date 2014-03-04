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

	public MultiThreadedSearcher(List<Integer> _array, int nThreads, int key){
		this.array = _array;
		executor = Executors.newFixedThreadPool(nThreads);
		
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
			threads.add(new SingleThreadSearcher(start, end-1,key));
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


	class SingleThreadSearcher implements Runnable{
		private int start, end, key;
		
		SingleThreadSearcher(int _start, int _end, int _key){
			start = _start;
			end = _end;
			key = _key;
		}
		
		@Override
		public void run() {
			synchronized (this) {
				int index = algorithmoi.ask2.sequential.Searcher.binarySearch(getArray(), key, start, end);
				if (index != -1 )
				{ 
					MultiThreadedSearcher.this.setIndex(index);
					setChanged();
					notifyObservers();
				}
			} 
		}
		
	}

}
