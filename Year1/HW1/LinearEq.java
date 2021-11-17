/*
 *  Computes the root of the equation a * x + b = c.
 *  Expects to get a, b, c as three command-line arguments of type double.
 */
public class LinearEq {
	public static void main(String[] args) {
		// Declaring variables
		double a = Double.parseDouble(args[0]);
		double b = Double.parseDouble(args[1]);
		double c = Double.parseDouble(args[2]);
		// Calculating the equation
		double x = (c - b) / a;
		// print the results
		System.out.println(a + " * x + " + b + " = " + c);
		System.out.println("X = " + x);
	}
}