/** String processing exercise 1. */
public class LowerCase {
    public static void main(String[] args) {
        String str = args[0];
        System.out.println(lowerCase(str));
    }

    /**
     * Returns a string which is identical to the original string, except that all
     * the upper-case letters are converted to lower-case letters. Non-letter
     * characters are left as is.
     */
    public static String lowerCase(String s) {
        String lowerCase = "";
        // Running threw the string letters
        for (int i = 0; i < s.length(); i++) {
            int chr = s.charAt(i);
            // If the char is A-Z upper-case transform it to lower-case
            if (chr >= 'A' && chr <= 'Z') {
                chr += 32;
            }
            lowerCase += (char) chr;
        }
        return lowerCase;
    }
}
