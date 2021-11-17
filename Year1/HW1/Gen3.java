/*
 *  Generates three random integers in the range [a,b), 
 *  and prints the minimal value that was generated.
 *  Expects to get a, b as command-line arguments of type int.
 */
public class Gen3 {

	public static void main(String[] args) {
		// Declaring variables and calculate thier values
		int a = Integer.parseInt(args[0]);
		int b = Integer.parseInt(args[1]);
		int generated1 = (int) (Math.random() * (b - a) + a);
		int generated2 = (int) (Math.random() * (b - a) + a);
		int generated3 = (int) (Math.random() * (b - a) + a);
		int minimal = Math.min(Math.min(generated1, generated2), generated3);
		// print the results
		System.out.println(generated1);
		System.out.println(generated2);
		System.out.println(generated3);
		System.out.println("The minimal generated number was " + minimal);
	}
}