public class MyString {
	public static void main(String[] args) {
		// Calls parseInt, and adds 1 to the returned value,
		// to verify that the returned value is indeed the correct int.
		System.out.println(parseInt("5613") + 1);
		System.out.println(parseInt("9a7"));
	}

	/**
	 * Returns the integer value of the given string of digit characters, or -1 if
	 * the string contains one or more non-digit characters.
	 */
	public static int parseInt(String str) {
		int sum = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
				sum += (str.charAt(i) - 48) * Math.pow(10, (str.length() - (i + 1)));
			} else {
				return -1;
			}
		}
		return sum;
	}
}