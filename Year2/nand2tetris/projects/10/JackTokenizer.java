import java.io.File;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

public class JackTokenizer {
    public JackTokenizer (File inputFile) {
        try {
            Scanner myReader = new Scanner(inputFile);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              String[] splitted = data.split("\\s+");
              for (int i=0; i < splitted.length; i++) {

              }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean hasMoreTokens() {
        return false;
    }

    public void advance(){}

    public TokenTypeEnum tokenType(String token) {
        if (isKeyword(token)) {
            return TokenTypeEnum.KEYWORD;
        }

        if (token.length() < 2 & isSymbol(token.charAt(0))) {
            return TokenTypeEnum.SYMBOL;
        }

        if (isIdentifier(token)) {
            return TokenTypeEnum.IDENTIFIER;
        }

        if (isIntValue(Integer.valueOf(token))) {
            return TokenTypeEnum.INT_CONST;
        }

        if (isStringVal(token)) {
            return TokenTypeEnum.STRING_CONST;
        }
        return null;
    }

    private boolean isKeyword(String key) {
        return Arrays.stream(KeywordsEnum.values()).toList().contains(key);
    }

    public KeywordsEnum keyWord() {
        return null;
    }
    private boolean isSymbol(char symbol) {
    List<Character> SYMBOL_LIST = Arrays.asList(new Character[] { '{' , '}' , '(' , ')' , '[' , ']' , '.' , ',' , ';' , '+' , '-' , '*' ,
    '/', '&' , ',' , '<' , '>' , '=' , '~' });
    return SYMBOL_LIST.contains(symbol);
    }

    private boolean isSymbol(int intValue) {
        String pattern = "^(327[0-5]|327[0-4][0-9]|3[0-1][0-9]{3}|[1-9][0-9]{0,3}|0)$";
        return String.valueOf(intValue).matches(pattern);
    }

    public char symbol() {
        return 'a';
    }

    private boolean isIdentifier(String identifier) {
        String pattern = "^[a-zA-Z_][a-zA-Z0-9_]*";
        return identifier.matches(pattern);
    }

    public String identifier() {
        return "a";
    }

    private boolean isIntValue(int intValue) {
        String pattern = "^(327[0-5]|327[0-4][0-9]|3[0-1][0-9]{3}|[1-9][0-9]{0,3}|0)$";
        return String.valueOf(intValue).matches(pattern);
    }

    public int intVal(){
        return 0;
    }

    public boolean isStringVal(String stringVal) {
        String pattern = "[^\"\n]*";
        return stringVal.matches(pattern);
    }

    public String stringVal() {
        return "a";
    }
}
