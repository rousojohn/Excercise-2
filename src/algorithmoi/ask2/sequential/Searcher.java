package algorithmoi.ask2.sequential;

public class Searcher {
	public static int binarySearch(int[] array, int key){
		return binarySearch(array, key, 0, array.length - 1);
	}

	public static int binarySearch(int[] array, int key, int low, int high) {

		if (low > high) return -1;
		int mid = (low + high) / 2;
		if (array[mid] == key) 
			return mid;
		else if (array[mid] < key) 
			return binarySearch(array, key, mid + 1, high);
		else
			return binarySearch(array, key, 0, mid - 1);
	}
}
