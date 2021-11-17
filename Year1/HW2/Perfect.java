/**========================================================================
 **                           Perfect.java
 *? Gets a command-line argument, and chekcs if the given number is perfect.
 *@argument number int  
 *@print If perfect: The number with its divisors. If not perfect: number is not perfect
 *========================================================================**/
public class Perfect {
	public static void main (String[] args) {
		// Declaring the variable
		int number = Integer.parseInt(args[0]);
		int sum = 0;
		String msg = number + " is a perfect number since " + number + " =";
		// Calculating and sum the divisors of the given number
		if (number>0){				
			for(int divisor=1; divisor<number; divisor++) {
				if (number % divisor == 0) {
					sum += divisor;
					if (divisor == 1) {
						msg += " " + divisor;	
					}
					else{
						msg += " + " + divisor;
					}
				}
			}
		}
		else {
			for(int divisor=-1; divisor>number; divisor--) {
				if (number % divisor == 0) {
					sum += divisor;
					if (divisor == -1) {
						msg += " (" + divisor + ")";	
					}
					else{
						msg += " + (" + divisor + ")";
					}
				}
			}
		}
		if (sum == number){
		System.out.println(msg);
		}
		else {
			System.out.println(number + " is not a perfect number");
		}
	}
}
/*============================ END OF Perfect.java ============================*/


