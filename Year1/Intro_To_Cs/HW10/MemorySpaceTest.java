import MMS.*;

public class MemorySpaceTest {
    private static int length1 = 76;
    private static int length2 = 78;
    private static int length3 = 100;
    private static int maxSize = 254;
    private static MemBlock fb1 = new MemBlock(208, 10);
    private static MemBlock fb2 = new MemBlock(352, 12);
    private static MemBlock fb3 = new MemBlock(250, 20);

    private static MemBlock ab1 = new MemBlock(50, 3);
    private static MemBlock ab2 = new MemBlock(370, 2);
    private static MemorySpace memo = new MemorySpace(maxSize);

    public static void main(String[] args) {
        // testMalloc();
        // testFree();
        test();
    }

    private static void testMalloc() {
        System.out.println("\nInitial Memory:\n" + memo);
        memo.malloc(length1);
        memo.malloc(length2);
        System.out.println("\nMemory after 2nd run of malloc:\n" + memo);
        memo.malloc(length3);
        System.out.println("\nMemory after 3rd run of malloc:\n" + memo);
    }

    private static void testFree() {
        memo.malloc(length1);
        memo.malloc(length2);
        memo.malloc(150);
        memo.free(76);
        System.out.println(memo);
    }

    private static void test() {
        MemorySpace m = new MemorySpace(1000);
        int[] addresses = new int[20];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = m.malloc((int) Math.pow(i + 2, 5) % 123);
        }
        for (int i = 0; i < addresses.length; i += 2) {
            m.free(addresses[i]);
        }
        m.defrag();
    }
}
