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

    public String tokenType(String token) {
        if (token.length() == 0) {
            return "";
        }

        if (isKeyword(token)) {
            return "keyword";
        }

        if (token.length() < 2 & isSymbol(token.charAt(0))) {
            return "symbol";
        }

        if (isIdentifier(token)) {
            return "identifier";
        }

        if (isIntValue(Integer.valueOf(token))) {
            return "integerConstant";
        }

        if (isStringVal(token)) {
            return "StringConstant";
        }
        return "";
    }

    public boolean isKeyword(String key) {
        List<String> KEYWORD_LIST = Arrays.asList(new String[] {"class", "constructor", "function",
        "method", "field", "static", "var", "int",
        "char", "boolean", "void", "true", "false",
        "null", "this", "let", "do", "if", "else",
        "while", "return" });
        return KEYWORD_LIST.contains(key);
    }

    public KeywordsEnum keyWord() {
        return null;
    }

    public boolean isSymbol(Character symbol) {
        List<Character> SYMBOL_LIST = Arrays.asList(new Character[] { '{' , '}' , '(' , ')' , '[' , ']' , '.' , ',' , ';' , '+' , '-' , '*' ,
        '/', '&' , ',' , '<' , '>' , '=' , '~' });
        return SYMBOL_LIST.contains(symbol);
    }

    public char symbol() {
        return 'a';
    }

    public boolean isIdentifier(String identifier) {
        String pattern = "^[a-zA-Z_][a-zA-Z0-9_]*";
        return identifier.matches(pattern);
    }

    public String identifier() {
        return "a";
    }

    public boolean isIntValue(int intValue) {
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
