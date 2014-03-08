package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import algorithmoi.ask2.parallel.MultiThreadedSearcher;

public class TestSecondPart {

	@Test
	public void test() {
		
		int numThreads = 5;
		final Random r = new Random(System.currentTimeMillis());
		
		final List<Integer> listX = new ArrayList<Integer>() {
			private static final long serialVersionUID = -7799880750233948611L;

			{
				for (int i=1; i<15; i++){
					add((i*7)+3);
				}
			}
		};
		
		final List<Integer> listY = new ArrayList<Integer>() {
			private static final long serialVersionUID = -8622889349872321124L;

			{
				for (int i=0; i<5; i++){
					add((i*2)-1);
				}
			}
		};

		Collections.sort(listX);
		Collections.sort(listY);
		
		int totalSize = listX.size()+listY.size();
		
		boolean ascending = true, descending = true;
		for (int i = 1; i < listX.size() && (ascending || descending); i++) {
		    ascending = ascending && listX.get(i) >=  listX.get(i-1);
		    descending = descending && listX.get(i) <= listX.get(i-1);
		}
		assertTrue("ArrayX NOT Sorted", ascending || descending);
		
		ascending = true; descending = true;
		for (int i = 1; i < listY.size() && (ascending || descending); i++) {
		    ascending = ascending && listY.get(i) >=  listY.get(i-1);
		    descending = descending && listY.get(i) <= listY.get(i-1);
		}
		assertTrue("ArrayY NOT Sorted", ascending || descending);
		
		System.out.println("List X:");
		System.out.println(listX);
		System.out.println("List Y:");
		System.out.println(listY);
		
		MultiThreadedSearcher searcher = new MultiThreadedSearcher(listX, listY, numThreads);
		
		searcher.runAllThreads();
		
		while(!searcher.getExecutor().isTerminated()) ;
		
		System.out.println("merged List:");
		System.out.println(listX);
		
		ascending = true; descending = true;
		for (int i = 1; i < listX.size() && (ascending || descending); i++) {
		    ascending = ascending && listX.get(i) >=  listX.get(i-1);
		    descending = descending && listX.get(i) <= listX.get(i-1);
		}
		assertTrue("Merged NOT Sorted", ascending || descending);
		assertTrue("Merged NOT containing all elements of both lists", listX.size() == totalSize);
		
	}
}
