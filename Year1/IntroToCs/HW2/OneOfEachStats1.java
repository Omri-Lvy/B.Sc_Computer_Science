/**
 *  Computes some statistics about families in which the parents decide 
 *  to have children until they have at least one child of each gender.
 *  The program expects to get one command-line argument: an int value
 * 	that determines how many families to simulate.
 */
public class OneOfEachStats1 {
	public static void main (String[] args) {
		// Declaring the variables
		int familiesNum = Integer.parseInt(args[0]);
		int hasTwoChildren = 0;
		int hasThreeChildren = 0;
		int hasFourPlusChildren = 0;
		double totalChildrenNum = 0;
		if (familiesNum <= 0){
			System.out.println("Families number is invalid. Please try again with a positive integer.");
		}
		else {	
			// Simulating the families and make the calculation
			for (int i=0; i<familiesNum; i++) {
				int boy=0;
				int girl=0;
				while (boy < 1 || girl < 1) {
					double child = (Math.random());
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
			System.out.println("Average: " + (totalChildrenNum/familiesNum) + " children to get at least one of each gender.");
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
