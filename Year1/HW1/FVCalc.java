/*
 *  Computes the future value of a given sum of money, invested over a given
 *  number of periods, at a given interest rate.
 *  Input values as three command-line arguments (current Value, rate, years)(int, double, int).
 */
public class FVCalc {
	public static void main(String[]args) {
		// Declaring the variables and calculate the future value
		int currentValue = Integer.parseInt(args[0]);
		int years = Integer.parseInt(args[2]);
		double rate = Double.parseDouble(args[1]);
		double futureValue = currentValue * Math.pow(1 + rate / 100, years);
		// Print the resualts
		System.out.println("After " + years + " years, $" + currentValue + " saved at " + rate + "% will yield $" + futureValue);
	}
}