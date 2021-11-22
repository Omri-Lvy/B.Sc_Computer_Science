/*
 *  Expresses a given quantity of cents as a number of quarter coins and cent coins.
 *  Expects to get the cents quantity as a command-line argument of type int.
 */
public class Coins {
	public static void main(String[] args) {
		// Declaring the variables and calculating their values
		int cents = Integer.parseInt(args[0]);
		int change = cents % 25;
		int quarters = cents / 25;
		// Print the results
		System.out.println("Use " + quarters + " quarters and " + change + " cents");
	}
}