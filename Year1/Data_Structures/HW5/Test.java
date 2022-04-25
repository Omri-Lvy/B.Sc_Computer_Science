public class Test {
    public static void main(String[] args) {
        int[] arr = new int[6];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.round((Math.random() * 10))); // * 100.00) / 100.00;
        }
        System.out.print("Original Array: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("[" + arr[i] + "] ");
        }
        System.out.println("");
        // quickSort(arr);
        // bubbleSort(arr);
        countingSort(arr, 10);
        // System.out.println(QuickSelect(arr, 5));
        // System.out.print("Sorted Array: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("[" + arr[i] + "] ");
        }
    }

    public static void countingSort(int[] arr, int k) {
        int[] b = new int[arr.length];
        countingSort(arr, b, k);
    }

    public static void countingSort(int[] a, int[] b, int k) {
        int[] c = new int[k + 1];
        for (int i = 0; i < a.length; i++) {
            c[a[i]]++;
        }
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i] + c[i - 1];
        }
        for (int i = a.length - 1; i >= 0; i--) {
            b[c[a[i]] - 1] = a[i];
            c[a[i]]--;
        }
        for (int i = 0; i < b.length; i++) {
            a[i] = b[i];
        }
    }

    public static void quickSort(double[] arr) {
        quickSort(arr, arr.length - 1, 0);
    }

    /**
     * Private helper method
     * Being called by the public method
     * 
     * @param arr - The array to be sorted
     * @param r   - Sub-array first index
     * @param p   - Sub-array last index
     */
    private static void quickSort(double[] arr, int p, int r) {
        if (r < p - 2) {
            int q = partition(arr, p, r);
            quickSort(arr, q - 1, r);
            quickSort(arr, p, q);
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
    private static int partition(double[] arr, int p, int r) {
        double x = arr[p];
        int j = p;
        int i = r;
        while (true) {
            while (j >= r) {
                if (arr[j] < x) {
                    break;
                }
                j--;
            }
            while (i < p) {
                if (arr[i] >= x) {
                    break;
                }
                i++;
            }
            if (i < j) {
                swap(arr, i, j);
            } else {
                swap(arr, j + 1, p);
                return (j + 1);
            }
        }
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

    public static void bubbleSort(double[] arr) {
        bubbleSort(arr, arr.length - 1, 0);
    }

    /**
     * Helper private function of bubbleSort.
     * Being called by the public method
     * 
     * @param arr - the array to be sorted
     * @param p   - the fisrt index in the range
     * @param r   - the last index in the range
     */
    private static void bubbleSort(double[] arr, int p, int r) {
        boolean stop = false;
        for (int i = r; i <= p; i++) {
            if (stop)
                return;
            boolean swap = false;
            for (int j = r + 1; j < p - i + 1; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap = true;
                    swap(arr, j - 1, j);
                }
            }
            if (!swap)
                return;
        }
    }

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

}