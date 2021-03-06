import java.util.Random;
/**
 *  Computes some statistics about families in which the parents decide 
 *  to have children until they have at least one child of each gender.
 *  The program expects to get two command-line arguments: an int value
 * 	that determines how many families to simulate, and an int value
 *  that serves as the seed of the random numbers generated by the program.
 *  Example usage: % java OneOfEachStats 1000 1
 */
/**========================================================================
 **                           OneOfEachStats.java
 *?  Computes some statistics about families in which the parents decide 
 *?  to have children until they have at least one child of each gender.
 *@param T int  
 *@param seed Int  
 *@print Average of children in a family 
 *@print Number of families that has two children 
 *@print Number of families that has three children 
 *@print Number of families that has four or more children 
 *@print Most common number of children in a family 
 *========================================================================**/
public class OneOfEachStats {
	public static void main (String[] args) {
		// Gets the two command-line arguments
		int T = Integer.parseInt(args[0]);
		int seed = Integer.parseInt(args[1]);
		// Initializes a random numbers generator with the given seed value
        Random generator = new Random(seed);  
		
		// Declaring the variables
		int hasTwoChildren = 0;
		int hasThreeChildren = 0;
		int hasFourPlusChildren = 0;
		double totalChildrenNum = 0;
		
		// Simulating the families and make the calculation
		if (T <= 0){
			System.out.println("Families number is invalid. Please try again with a positive integer.");
		}
		else {
			for (int i=0; i<T; i++) {
				int boy=0;
				int girl=0;
				while (boy < 1 || girl < 1) {
					double child = generator.nextDouble();
					if(child <= 0.5){
						boy ++;
					}
					else {
						girl ++;
					}
				}
				totalChildrenNum += (boy + girl);
				if ((boy + girl) == 2){
					hasTwoChildren++;
				}
				else if ((boy + girl) == 3) {
					hasThreeChildren++;
				}
				else {
					hasFourPlusChildren++;
				}
			}
			
			// Print the results
			System.out.println("Average: " + (totalChildrenNum/T) + " children to get at least one of each gender.");
			System.out.println("Number of families with 2 children: " + hasTwoChildren);
			System.out.println("Number of families with 3 children: " + hasThreeChildren); 
			System.out.println("Number of families with 4 or more children: " + hasFourPlusChildren);
			
			if (hasTwoChildren >= hasThreeChildren && hasTwoChildren >= hasFourPlusChildren) {
					System.out.println("The most common number of children is 2.");
			}
			else if (hasThreeChildren >= hasFourPlusChildren && hasThreeChildren >= hasTwoChildren) {
					System.out.println("The most common number of children is 3.");
			}
			else {
					System.out.println("The most common number of children is 4.");
			}
		}
	}
}
/*============================ END OF OneOfEachStats.java ============================*/


