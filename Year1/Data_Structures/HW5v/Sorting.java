import java.util.Random;

import Plotter.Plotter;

public class Sorting {

	final static int SELECT_VS_QUICK_LENGTH = 12;
	final static int MERGE_VS_QUICK_LENGTH = 15;
	final static int COUNTING_VS_QUICK_LENGTH = 16;
	final static int BUBBLE_VS_MERGE_LENGTH = 12;
	final static int MERGE_VS_QUICK_SORTED_LENGTH = 11;
	final static double T = 600.0;

	/**
	 * Sorts a given array using the quick sort algorithm.
	 * At each stage the pivot is chosen to be the rightmost element of the
	 * subarray.
	 * 
	 * Should run in average complexity of O(nlog(n)), and worst case complexity of
	 * O(n^2)
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void quickSort(double[] arr) {
		quickSort(arr, arr.length - 1, 0);
	}

	/**
	 * Helper private function of quickSort.
	 * Being called by the public method
	 * 
	 * @param arr - The array to be sorted
	 * @param r   - Sub-array first index
	 * @param p   - Sub-array last index
	 */
	private static void quickSort(double[] arr, int p, int r) {
		if (r < p - 2) {
			int q = partition(arr, p, r);
			quickSort(arr, r, q - 1);
			quickSort(arr, q + 1, p);
		} else {
			bubbleSort(arr);
		}
	}

	/**
	 * Private helper method
	 * Divide the array into left and right sub arrays
	 * 
	 * @param arr - The array to be sorted
	 * @param r   - Sub-array first index
	 * @param p   - Sub-array last index
	 */
//	private static int partition(double[] arr, int p, int r) {
//		double x = arr[p];
//		int j = p;
//		int i = r;
//		while (true) {
//			while (j >= r) {
//				if (arr[j] < x) {
//					break;
//				}
//				j--;
//			}
//			while (i < p) {
//				if (arr[i] >= x) {
//					break;
//				}
//				i++;
//			}
//			if (i < j) {
//				swap(arr, i, j);
//			} else {
//				swap(arr, j + 1, p);
//				return (j + 1);
//			}
//		}
//	}

	private static int partition(double[] arr,int p, int r){
		double x = arr[p];
		int i = r - 1;
		for (int j=r;j<p;j++){
			if(arr[j]<=x){
				i++;
				swap(arr,i,j);
			}
		}
		swap(arr,i+1,p);
		return i+1;
	}

	/**
	 * Private helper method
	 * Swap places of two elements in the array
	 * 
	 * @param arr - The array to be sorted
	 * @param i   - Index of first element
	 * @param j   - Index of second element
	 */
	private static void swap(double[] arr, int i, int j) {
		double temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	/**
	 * Given an array arr and an index i returns the the i'th order statistics in
	 * arr.
	 * In other words, it returns the element with rank i in the array arr.
	 * 
	 * At each stage the pivot is chosen to be the rightmost element of the
	 * subarray.
	 * 
	 * Should run in average complexity of O(n), and worst case complexity of O(n^2)
	 * 
	 **/
	public static double QuickSelect(double[] arr, int i) {
		return QuickSelect(arr, arr.length - 1, 0, i);
	}

	/**
	 * Helper private function of QuickSelect.
	 * Being called by the public method
	 * 
	 * @param arr - The array to be sorted
	 * @param r   - Sub-array first index
	 * @param p   - Sub-array last index
	 * @param i   - Rank to be found in the array
	 */
	private static double QuickSelect(double[] arr, int p, int r, int i) {
		if (p == r)
			return arr[r];
		int q = partition(arr, p, r);
		int m = q - r + 1;
		if (i == m)
			return arr[q];
		if (i < m)
			return QuickSelect(arr, q - 1, r, i);
		else
			return QuickSelect(arr, p, q + 1, i - m);
	}

	/**
	 * Sorts a given array using the merge sort algorithm.
	 * 
	 * Should run in complexity O(nlog(n)) in the worst case.
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void mergeSort(double[] arr) {
		mergeSort(arr, 0, arr.length - 1);
	}

	/**
	 * Helper private function of mergeSort.
	 * Sorts the given array with mergeSort within the given range.
	 * 
	 * 
	 * @param arr - the array to be sorted
	 * @param p   - the first index in the range that need to be sorted
	 * @param r   - the last index in the range that need to be sorted
	 */
	private static void mergeSort(double[] arr, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort(arr, p, q);
			mergeSort(arr, q + 1, r);
			merge(arr, p, q, r);
		}
	}

	/**
	 * Another helper private function of mergeSort.
	 * Being called by the public method
	 * 
	 * 
	 * @param arr - the array to be sorted
	 * @param p   - the first index in the first range that needs to be merged.
	 * @param q   - the last index in the first range that needs to be merged.
	 * @param r   - the last index in the second range that needs to be merged.
	 */
	private static void merge(double[] arr, int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;
		double[] L = new double[n1 + 1];
		double[] R = new double[n2 + 1];
		for (int i = 0; i < n1; i++) {
			L[i] = arr[p + i];
		}
		for (int i = 0; i < n2; i++) {
			R[i] = arr[q + 1 + i];
		}
		L[n1] = Double.POSITIVE_INFINITY;
		R[n2] = Double.POSITIVE_INFINITY;
		int i = 0;
		int j = 0;
		for (int k = p; k <= r; k++) {
			if (L[i] <= R[j]) {
				arr[k] = L[i];
				i++;
			} else {
				arr[k] = R[j];
				j++;
			}
		}
	}

	/**
	 * Sorts a given array using bubble sort.
	 * 
	 * The algorithm should run in complexity O(n^2).
	 * 
	 * @param arr - the array to be sorted
	 */
	public static void bubbleSort(double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 1; j < arr.length - i; j++) {
				if (arr[j - 1] > arr[j]) {
					swap(arr, j, j - 1);
				}
			}
		}
	}

	/**
	 * Sorts a given array, using the counting sort algorithm.
	 * You may assume that all elements in the array are between 0 and k (not
	 * including k).
	 * 
	 * Should run in complexity O(n + k) in the worst case.
	 * 
	 * @param arr - an array with positive integers
	 * @param k   - an upper bound for the values of all elements in the array.
	 */
	public static void countingSort(int[] arr, int k) {
		int[] B = new int[arr.length];
		int[] C = new int[k + 1];
		for (int i = 0; i < arr.length; i++) {
			C[arr[i]]++;
		}
		for (int i = 1; i < C.length; i++) {
			C[i] = C[i] + C[i - 1];
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			B[C[arr[i]] - 1] = arr[i];
			C[arr[i]]--;
		}
		for (int i = 0; i < B.length; i++) {
			arr[i] = B[i];
		}
	}

	public static void main(String[] args) {

		countingVsQuick();
		mergeVsQuick();
		mergeVsQuickOnSortedArray();
		mergeVsBubble();
		QuickSelectVsQuickSort();

	}

	private static void countingVsQuick() {
		double[] quickTimes = new double[COUNTING_VS_QUICK_LENGTH];
		double[] countingTimes = new double[COUNTING_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < COUNTING_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumCounting = 0;
			for (int k = 0; k < T; k++) {
				System.out.println("countingVsQuick: " + i + "." + k);
				int size = (int) Math.pow(2, i);
				double[] a = new double[size];
				int[] b = new int[size];
				for (int j = 0; j < a.length; j++) {
					b[j] = r.nextInt(size);
					a[j] = b[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				countingSort(b, size);
				endTime = System.currentTimeMillis();
				sumCounting += endTime - startTime;
			}
			quickTimes[i] = sumQuick / T;
			countingTimes[i] = sumCounting / T;
		}
		Plotter.plot("Counting sort on arrays with elements < n", countingTimes,
				"Quick sort on arrays with elements < n", quickTimes);

	}

	/**
	 * Compares the merge sort algorithm against quick sort on random arrays
	 */
	public static void mergeVsQuick() {
		double[] quickTimes = new double[MERGE_VS_QUICK_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < MERGE_VS_QUICK_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				System.out.println("mergeVsQuick: " + i + "." + k);
				int size = (int) Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick / T;
			mergeTimes[i] = sumMerge / T;
		}
		Plotter.plot("quick sort on random array", quickTimes, "merge sort on random array", mergeTimes);
	}

	/**
	 * Compares the merge sort algorithm against quick sort on pre-sorted arrays
	 */
	public static void mergeVsQuickOnSortedArray() {
		double[] quickTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		double[] mergeTimes = new double[MERGE_VS_QUICK_SORTED_LENGTH];
		long startTime, endTime;
		for (int i = 0; i < MERGE_VS_QUICK_SORTED_LENGTH; i++) {
			long sumQuick = 0;
			long sumMerge = 0;
			for (int k = 0; k < T; k++) {
				System.out.println("mergeVsQuickOnSortedArray: " + i + "." + k);
				int size = (int) Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = j;
					b[j] = j;
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQuick += endTime - startTime;
				startTime = System.currentTimeMillis();
				mergeSort(b);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
			}
			quickTimes[i] = sumQuick / T;
			mergeTimes[i] = sumMerge / T;
		}
		Plotter.plot("quick sort on sorted array", quickTimes, "merge sort on sorted array", mergeTimes);
	}

	/**
	 * Compares merge sort and bubble sort on random arrays
	 */
	public static void mergeVsBubble() {
		double[] mergeTimes = new double[BUBBLE_VS_MERGE_LENGTH];
		double[] bubbleTimes = new double[BUBBLE_VS_MERGE_LENGTH];
		long startTime, endTime;
		Random r = new Random();
		for (int i = 0; i < BUBBLE_VS_MERGE_LENGTH; i++) {
			long sumMerge = 0;
			long sumBubble = 0;
			for (int k = 0; k < T; k++) {
				int size = (int) Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					System.out.println("mergeVsBubble: " + i + "." + k);
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				mergeSort(a);
				endTime = System.currentTimeMillis();
				sumMerge += endTime - startTime;
				startTime = System.currentTimeMillis();
				bubbleSort(a);
				endTime = System.currentTimeMillis();
				sumBubble += endTime - startTime;
			}
			mergeTimes[i] = sumMerge / T;
			bubbleTimes[i] = sumBubble / T;
		}
		Plotter.plot("merge sort on random array", mergeTimes, "bubble sort on random array", bubbleTimes);
	}

	/**
	 * Compares the quick select algorithm with a random rank, and the quick sort
	 * algorithm.
	 */
	public static void QuickSelectVsQuickSort() {
		double[] QsortTimes = new double[SELECT_VS_QUICK_LENGTH];
		double[] QselectTimes = new double[SELECT_VS_QUICK_LENGTH];
		Random r = new Random();
		long startTime, endTime;
		for (int i = 0; i < SELECT_VS_QUICK_LENGTH; i++) {
			long sumQsort = 0;
			long sumQselect = 0;
			for (int k = 0; k < T; k++) {
				System.out.println("QuickSelectVsQuickSort: " + i + "." + k);
				int size = (int) Math.pow(2, i);
				double[] a = new double[size];
				double[] b = new double[size];
				for (int j = 0; j < a.length; j++) {
					a[j] = r.nextGaussian() * 5000;
					b[j] = a[j];
				}
				startTime = System.currentTimeMillis();
				quickSort(a);
				endTime = System.currentTimeMillis();
				sumQsort += endTime - startTime;
				startTime = System.currentTimeMillis();
				QuickSelect(b, r.nextInt(size) + 1);
				endTime = System.currentTimeMillis();
				sumQselect += endTime - startTime;
			}
			QsortTimes[i] = sumQsort / T;
			QselectTimes[i] = sumQselect / T;
		}
		Plotter.plot("quick sort with an arbitrary pivot", QsortTimes,
				"quick select with an arbitrary pivot, and a random rank", QselectTimes);
	}

}
