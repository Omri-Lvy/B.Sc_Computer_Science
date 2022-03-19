public class Ordered {
/**========================================================================
 **                           Ordered.java
 *? Gets three command-line arguments (int values). If the values are strictly
 *?	ascending or strictly descending, prints true. Otherwise prints false.
 *@argument x int  
 *@argument y int  
 *@argument z int  
 *@print True / False
 *========================================================================**/
	public static void main (String[] args) {
		// Declaring the variables
		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		int z = Integer.parseInt(args[2]);
		// Check if the variables are in strictly ascending or descending order
		if ((x < y && y < z) ||  (x > y && y > z)){
			System.out.println("True");
		}
		else {
			System.out.println("False");
		}
	}
}
/*============================ END OF Ordered.java ============================*/


