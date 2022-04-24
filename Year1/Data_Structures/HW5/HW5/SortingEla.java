import java.util.Random;

import Plotter.Plotter;

public class SortingEla {

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
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(double[] arr, int startIndex, int endIndex) {
        if (startIndex <= (endIndex - 2)) {
            int q = partition(arr, startIndex, endIndex);
            quickSort(arr, startIndex, q - 1);
            quickSort(arr, q + 1, endIndex);
        } else {
            bubbleSort(arr);
        }
    }

    /**
     * divides the array into two sub-arrays according to the pivot
     * and returns the final index of the chosen pivot in the array.
     * side effect- changes the order of elements of the original array.
     */
    private static int partition(double[] arr, int startIndex, int endIndex) {
        double pivot = arr[endIndex];
        int i = startIndex;
        int j = endIndex;
        while (true) {
            while ((arr[j] >= pivot) && (j > startIndex)) {
                j--;
            }
            while ((arr[i] < pivot) && (i < endIndex)) {
                i++;
            }
            if (i < j) {
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            } else {
                double temp2 = arr[endIndex];
                arr[endIndex] = arr[j + 1];
                arr[j + 1] = temp2;
                return j + 1;
            }
        }
    }

    /**
     * Given an array arr and an index i returns the the i'th order statstics in
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
        return QuickSelect(arr, 0, arr.length - 1, i);
    }

    private static double QuickSelect(double[] arr, int startIndex, int endIndex, int i) {
        // checks that the input array contains at least one element.
        if (arr.length < 1) {
            System.out.println("invalid array");
        }
        // checks that the given index is within the dimensions of the array.
        if ((i < startIndex) || (i > (endIndex))) {
            System.out.println("invalid index");
        } else {
            // if the given range only contains one element we return that element
            if (startIndex == endIndex) {
                return arr[startIndex];
            }
            // q is the pivot location in arr.
            int q = partition(arr, startIndex, endIndex);
            int m = (q - startIndex + 1);
            if (i == m) {
                return arr[q];
            } else if (i < m) {
                return QuickSelect(arr, startIndex, q - 1, i);
            } else {
                return QuickSelect(arr, q + 1, endIndex, i - m);
            }
        }
        return 0.0;
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

    private static void mergeSort(double[] arr, int startIndex, int endIndex) {
        int p = startIndex;
        int r = endIndex;
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(arr, p, q);
            mergeSort(arr, q + 1, r);
            merge(arr, p, q, r);
        }
    }

    private static void merge(double[] arr, int startIndex, int intersection, int endIndex) {
        int p = startIndex;
        int r = endIndex;
        int q = intersection;
        int n1 = q - p + 1;
        int n2 = r - q;
        double[] L = new double[n1];
        double[] R = new double[n2];
        for (int i = 0; i < n1; i++) {
            L[i] = arr[i];
        }
        for (int j = n1; j < r; j++) {
            R[j] = arr[j];
        }

        int i = 1;
        int j = 1;

        for (int k = p; k < r; k++) {
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
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    double temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
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
     * @param arr - an array with possitive integers
     * @param k   - an upper bound for the values of all elements in the array.
     */
    public static void countingSort(int[] arr, int k) {
        int[] b = new int[arr.length];
        int[] c = new int[k];
        for (int i = 0; i < arr.length; i++) {
            c[arr[i]]++;
        }
        for (int j = 1; j < c.length; j++) {
            c[j] = c[j] + c[j - 1];
        }
        for (int h = arr.length - 1; h >= 0; h--) {
            b[c[arr[h]] - 1] = arr[h];
            c[arr[h]]--;
        }
    }

    public static void main(String[] args) {

        // countingVsQuick();
        // mergeVsQuick();
        // mergeVsQuickOnSortedArray();
        // mergeVsBubble();
        // QuickSelectVsQuickSort();

        double[] arr = { 2, 9, 4, 5, 3, 1, 6 };
        System.out.println(arr);
        mergeSort(arr);
        System.out.println(arr);

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
