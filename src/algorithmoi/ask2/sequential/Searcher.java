package algorithmoi.ask2.sequential;

import java.util.List;

public class Searcher {
	public static int binarySearch(List<Integer> array, int key){
		return binarySearch(array, key, 0, array.size() - 1);
	}

	public static int binarySearch(List<Integer> array, int key, int low, int high) {

		if (low > high) return high+1;
		int mid = (low + high) / 2;
		if (array.get(mid) == key) 
			return mid;
		else if (array.get(mid) < key) 
			return binarySearch(array, key, mid + 1, high);
		else
			return binarySearch(array, key, 0, mid - 1);
	}
}
