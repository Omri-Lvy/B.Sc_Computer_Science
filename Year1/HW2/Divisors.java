/**
 *  Gets a command-line argument (int), and prints all the divisors of the given number.
 */
public class Divisors {
	public static void main (String[] args) {
		// Declaring the variable
		int number = Integer.parseInt(args[0]);
		// Calculating and printing the divisors of the given number
		if (number>0) {
			for(int divisor=1; divisor<number; divisor++) {
				if (number % divisor == 0) {
					System.out.println(divisor);
				}
			}
		}
		else {
			for(int divisor=-1; divisor>number; divisor--) {
				if (number % divisor == 0) {
					System.out.println(divisor);
				}
			}
		}
	}
}
