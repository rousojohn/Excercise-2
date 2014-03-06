package algorithmoi.ask2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import algorithmoi.ask2.parallel.MultiThreadedSearcher;
import algorithmoi.ask2.parallel.MyThreadPool;
import algorithmoi.ask2.sequential.Searcher;

public class Program implements Observer {
	private static List<Integer> array;
	
	MultiThreadedSearcher searcher;
	private List<Integer> listX;
	private List<Integer> listY;
	
	public static void main(String[] args) {
		Program p = new Program();
		
		array = new ArrayList<Integer>() {
			/**
			 * Auto-generated serial UID 
			 */
			private static final long serialVersionUID = 1L;

			{ 
				add(1);
				add(2);
				add(3);
				add(4);
				add(5);
				add(6);
				add(7);
				add(8);
				add(9);
				add(10);
				add(11);
			}
		};	
		
		MyThreadPool.setStart(0);
		MyThreadPool.setEnd(array.size() - 1);
		MyThreadPool.setKey(0);
		MyThreadPool.setFound(false);
		MyThreadPool.setLocalIndex(0);
		MyThreadPool.setStep(1);
		MyThreadPool.setArray(array);
		
		MyThreadPool mThreadPool = new MyThreadPool(2);
		
		mThreadPool.runThreads();
		
		while (!mThreadPool.getExecutor().isTerminated()) ;
		
		if (MyThreadPool.isFound())
			System.out.println("NUMBER "+MyThreadPool.getKey()+" FOUND AT "+MyThreadPool.getLocalIndex()+" POSITION\n");
		else{
			System.out.println("should be placed after "+MyThreadPool.getEnd()+" place\n");
		}
	}

	public static void main1(String[] args) {
		Program p = new Program();
		
		// Initialize the local variables holding the 2 lists to be merged
		p.listX = new ArrayList<Integer>();
		p.listY = new ArrayList<Integer>();

		// Initialize the Input Stream
		Scanner mScanner = new Scanner(System.in);
		
		// Flag to determine whether or not to read another integer for the current ArrayList
		boolean proceed = true;

		//		Read the 1st List
		do{
			System.out.println("Give The First List (X) ");
			
			// Get the integer and add it to the list
			p.listX.add(mScanner.nextInt());
			
			// Determine whether to continue reading integers or not
			System.out.println("Do you want to continue? Y/N ");
			String option = mScanner.next();
			proceed = (option.equals("y") || option.equals("Y"));
		}while (proceed == true);
		
		
		proceed = true;

		//		Read the 2nd List
		do{
			System.out.println("Give The Second List (Y) ");
			
			// Get the integer and add it to the list
			p.listY.add(mScanner.nextInt());
			
			// Determine whether to continue reading integers or not
			System.out.println("Do you want to continue? Y/N ");
			String option = mScanner.next();
			proceed = (option.equals("y") || option.equals("Y"));
		}while (proceed == true);
		
		// Make sure the lists are sorted.
		p.sortList(p.listX);
		p.sortList(p.listY);
	}

	
	/**
	 * Overrides the update method inherited by parent class Observer.
	 * It is called whenever an Observable class calls notifyObservers()
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println(((MultiThreadedSearcher)arg0).getIndex());		
	}
	
	
	private void sortList(List<Integer> _list){
		Collections.sort(_list);
	}
	
	public static void testFirstPart(){
		Program p = new Program();
		array = new ArrayList<Integer>() {
			/**
			 * Auto-generated serial UID 
			 */
			private static final long serialVersionUID = 1L;

			{ 
				add(1);
				add(2);
				add(3);
				add(4);
				add(5);
				add(6);
				add(7);
				add(8);
				add(9);
				add(10);
				add(11);
			}
		};	
		
		// Initialize the multi-threaded searcher
		// giving it the data to search the number of threads to use and the integer to find
		p.searcher = new MultiThreadedSearcher(array,3,10);
		
		// add the program as an observer to the observable searcher
		// so as to be informed immediately when the integer is found
		p.searcher.addObserver(p);
		
		// finally run the the threads
		p.searcher.runAllThreads();
	}

}
