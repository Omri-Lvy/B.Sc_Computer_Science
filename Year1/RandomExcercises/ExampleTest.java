public class ExampleTest {
    public static void main(String[] args) {
        int[][] matrix1 = new int[4][5];
        System.out.printf("%3s", "\n");
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                matrix1[i][j] = (int) (Math.random() * 10);
                System.out.printf("%3s", matrix1[i][j]);
            }
            System.out.printf("%3s", "\n");
        }
        System.out.printf("%3s", "\n");
        System.out.println(question1(matrix1));
        System.out.printf("%3s", "\n");
        int[] set1 = { 1, 9, 7, 3, 4 };
        int[] set2 = { 4, 2, 6, 8, 0, 5, 1, 9 };
        int[] union = union(set1, set2);
        System.out.print("Union Set: ");
        for (int i = 0; i < union.length; i++) {
            System.out.printf("%3s", union[i]);
        }
        System.out.printf("%3s", "\n");

    }

    public static boolean question1(int[][] m) {
        int n = m.length - 1;
        int a1 = 0, a2 = 0;
        for (int i = 0; i <= n; i++) {
            a1 += m[i][i];
            a2 += m[i][n - i];
        }
        System.out.println("Main diagonal: " + a1);
        System.out.println("Secondery diagonal: " + a2);
        System.out.printf("%3s", "\n");
        return a1 == a2;
    }

    public static boolean elementOF(int e, int[] set) {
        for (int i = 0; i < set.length; i++) {
            if (set[i] == e) {
                return true;
            }
        }
        return false;
    }

    public static int[] union(int[] set1, int[] set2) {
        int size = set1.length;
        int[] tempSet = new int[set2.length];
        for (int i = 0; i < tempSet.length; i++) {
            if (!elementOF(set2[i], set1)) {
                tempSet[size - set1.length] = set2[i];
                size++;
            }
        }
        int[] unionSet = new int[size];
        for (int j = 0; j < unionSet.length; j++) {
            if (j < set1.length) {
                unionSet[j] = set1[j];
            } else {
                unionSet[j] = tempSet[j - set1.length];
            }
        }
        return unionSet;
    }
}