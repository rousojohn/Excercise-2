package algorithmoi.ask2;

import java.util.Observable;
import java.util.Observer;

import algorithmoi.ask2.parallel.MultiThreadedSearcher;
import algorithmoi.ask2.sequential.Searcher;

public class Program implements Observer {
	private static int[] array = {1,2,3,4,5,6,7,8,9,10,11};
	MultiThreadedSearcher searcher;

	public static void main(String[] args) {
		Program p = new Program();
		
		// Sequential Search for debug purpose
//		System.out.println( Searcher.binarySearch(array, 10));
		
		// Initialize the multi-threaded searcher
		// giving it the data to search the number of threads to use and the integer to find
		p.searcher = new MultiThreadedSearcher(array,3,10);
		
		// add the program as an observer to the observable searcher
		// so as to be informed immediately when the integer is found
		p.searcher.addObserver(p);
		
		// finally run the the threads
		p.searcher.runAllThreads();
	}
	
	
	public void run(){
	}


	
	/**
	 * Overrides the update method inherited by parent class Observer.
	 * It is called whenever an Observable class calls notifyObservers()
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println(((MultiThreadedSearcher)arg0).getIndex());
		// TODO Auto-generated method stub
		
	}

}
