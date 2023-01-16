import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JackTokenizer {

    private Scanner file;
    private String token;
    private ArrayList<String> tokens;
    private int position;
    private List<Character> SYMBOL_LIST = Arrays.asList(new Character[]{ '{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*',
            '/', '&', ',', '<', '>', '=', '~' });

    public JackTokenizer (File inputFile) throws FileNotFoundException {
        position = 0;
        tokens = new ArrayList<String>();
        file = new Scanner(inputFile);
        while (file.hasNextLine()) {
            String line = file.nextLine().split("//")[0].trim();
            if (line.startsWith("/*")) {
                while (!line.endsWith("*/")) {
                    line = file.nextLine();
                }
                line = file.nextLine();
            }
            if (!line.equals("")) {
                String[] wards = line.split(" ");
                for (String word : wards) {
                    splitSymbols(word);
                }
            }
        }

    }

    private void splitSymbols(String word) {
        int lastIndex = 0;
        boolean inserted = false;
        for (char c: word.toCharArray()) {
            if (SYMBOL_LIST.contains(c)) {
                String tkn = word.substring(lastIndex,word.indexOf(c));
                if (!tkn.equals("")) {
                    tokens.add(tkn);
                }
                tokens.add(""+c);
                lastIndex = word.indexOf(c)+1;
                inserted = true;
            }
        }
        if (!inserted) {
            tokens.add(word);
        }
    }

    private boolean isKeyword (String key) {
        for (KeywordsEnum keyword : KeywordsEnum.values()) {
            if (keyword.getType().equals(key))
                return true;
        }
        return false;
    }

    private boolean isSymbol (char symbol) {
        return SYMBOL_LIST.contains(symbol);
    }


    private boolean isIdentifier (String identifier) {
        String pattern = "^[a-zA-Z_][a-zA-Z0-9_]*";
        return identifier.matches(pattern);
    }

    private boolean isIntValue (int intValue) {
        String pattern = "^(327[0-5]|327[0-4][0-9]|3[0-1][0-9]{3}|[1-9][0-9]{0,3}|0)$";
        return String.valueOf(intValue).matches(pattern);
    }

    private boolean isStringVal (String stringVal) {
        String pattern = "[^\"\n]*";
        return stringVal.matches(pattern);
    }


    public TokenTypeEnum tokenType (String token) {
        if (isKeyword(token)) {
            return TokenTypeEnum.KEYWORD;
        }
        if (isSymbol(token.charAt(0))) {
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

    public String stringVal () {
        return token;
    }

    public String keyWord () {
        return KeywordsEnum.valueOf(token).getType();
    }

    public int intVal () {
        return Integer.parseInt(token);
    }

    public String identifier () {
        return token;
    }

    public char symbol () {
        return token.charAt(0);
    }

    public void advance () {
        token = tokens.get(position);
        position++;
    }

    public boolean hasMoreTokens () {
        return position < tokens.size();
    }

    public String getToken () {
        return token;
    }

}
