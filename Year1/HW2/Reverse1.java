/**
 * Prints a given string, backward. Then prints the middle character in the string.
 * The program expects to get one command-line argument: A string.
 */
public class Reverse1 {
	public static void main (String[] args){
		// Declaring the variable
		String str = args[0];
		String reversedString = "";
		char middleChar = '0';
		int charIndex = str.length() - 1;
		while (charIndex >= 0) {
			reversedString += str.charAt(charIndex);
			charIndex--;
		}
		if (str.length() % 2 == 0){
			middleChar = str.charAt((int) (str.length() / 2) - 1);
		}
		else {
			middleChar = str.charAt((int)(str.length() / 2));
		}
		System.out.println(reversedString);
		System.out.println("The middle character is " + middleChar);
		
	}
}
