/* Recieves two command line integers, n and k, and returns the respective binomial coefficent.
   Uses memoization to optimize the recursive process. */
public class Binomial {

	public static void main(String[] args) {
		System.out.println(binomial(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
	}

	// Computes and returns the Binomial coefficient
	public static long binomial(int n, int k) {
		long[][] memo = new long[n + 1][k + 1];
		if (k > n) {
			return 0;
		}
		if (k == 0 || n == 0) {
			return 1;
		}
		return (binomial(n - 1, k, memo) + binomial(n - 1, k - 1, memo));
	}

	public static long binomial(int n, int k, long[][] memo) {
		if (k > n) {
			return 0;
		}
		if (k == 0 || n == 0) {
			return 1;
		}
		if (memo[n][k] == 0) {
			memo[n][k] = binomial(n - 1, k, memo) + binomial(n - 1, k - 1, memo);
		}
		return memo[n][k];
	}
}
