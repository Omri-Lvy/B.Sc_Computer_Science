/** String processing exercise 2. */
public class UniqueChars {
    public static void main(String[] args) {
        String str = args[0];
        System.out.println(uniqueChars(str));
    }

    /**
     * Returns a string which is identical to the original string, except that all
     * the duplicate characters are removed, unless they are space characters.
     */
    public static String uniqueChars(String s) {
        String uniqueChars = "";
        for (int i = 0; i < s.length(); i++) {
            int chr = s.charAt(i);
            if (chr != 32) {
                if (uniqueChars.indexOf(s.charAt(i)) == -1)
                    uniqueChars += s.charAt(i);
            } else {
                uniqueChars += s.charAt(i);
            }
        }
        return uniqueChars;
    }
}
