package algorithmoi.ask2;

import algorithmoi.ask2.parallel.MultiThreadedSearcher;

public class Program {
	private static int[] array = {1,2,3,4,5,6,7,8,9,10,11};
	MultiThreadedSearcher searcher;
	public static void main(String[] args) {
		Program p = new Program();

		p.searcher = new MultiThreadedSearcher(array,3,10);
		p.searcher.runAllThreads();
		
		
		
		System.out.println(p.searcher.getIndex());
	}
	
	
	public void run(){
	}

}
