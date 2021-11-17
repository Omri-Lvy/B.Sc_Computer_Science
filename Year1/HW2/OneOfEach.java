
/**
 *  Simulates the formation of a family in which the parents decide 
 *  to have children until they have at least one child of each gender.
 */
public class OneOfEach {
	public static void main (String[] args) {
		int child = (int) (Math.random() * 2 + 1);
		String children = "";
		int boy=0;
		int girl=0;
		while (boy < 1 || girl < 1) {
			if(child == 1){
				boy ++;
				if (children.length() == 0) {
					children += "b";
				}
				else {
				 children += " b";
				}
			}
			else {
				girl ++;
				if (children.length() == 0) {
					children += "g";
				}
				else {
				 children += " g";
				}
			}
			child = (int) (Math.random() * 2 + 1);
		}
		System.out.println(children);
		System.out.println("You made it... and you have now " + (boy+girl) + " children");
	}
}
