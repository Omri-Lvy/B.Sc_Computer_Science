/**========================================================================
 **                           DamkaBoard.java
 *?  Gets a command-line argument n, and prints an n-by-n damka board.
 *@argument n int   
 *@print A damka board
 *========================================================================**/
public class DamkaBoard {
	public static void main(String[] args) {
		// Declare the variable
		int n = Integer.parseInt(args[0]);
		// Printing the n-by-n board
		if (n < 0) {
			System.out.println("Invalid input please with a positive number");
		}
		else {
			for (int i = 0; i < n; i++){	
				for (int j = 0; j < n; j++) {
					if (i % 2 != 0) {
					System.out.print(" *");
					}
					else {
						System.out.print("* ");
					}
				} 
				System.out.println();
			}
		}
	}
}
/*============================ END OF DamkaBoard.java ============================*/


