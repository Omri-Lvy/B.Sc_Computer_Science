public class Reverse {
/**========================================================================
 **                           Reverse.java
 *? The program expects to get one command-line argument: A string.
 *? Prints a given string, backward. Then prints the middle character in the string.
 *@argument str String  
 *@print Reversed string of str
 *@print Middle char in str
 *========================================================================**/
	public static void main (String[] args){
		// Declaring the variable
		String str = args[0];
		String reversedString = "";
		// Reverse the string and find the middle character
		for (int i=str.length()-1; i >= 0; i--) {
			reversedString += str.charAt(i);
		}
		System.out.println(reversedString);
		if (str.length() % 2 == 0){
			char middleChar = str.charAt((int) (str.length() / 2) - 1);
			System.out.println("The middle character is " + middleChar);
		}
		else {
			char middleChar = str.charAt((int)(str.length() / 2));
			System.out.println("The middle character is " + middleChar);
		}
	}
}
/*============================ END OF Reverse.java ============================*/