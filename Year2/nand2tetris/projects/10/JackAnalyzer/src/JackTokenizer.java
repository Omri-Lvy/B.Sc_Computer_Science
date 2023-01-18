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
    private String stringConstant = "";
    private boolean stringConstantBuilder = false;

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
                stringConstant = "";
                String[] words = line.split(" ");
                for (String word : words) {
                    if (stringConstantBuilder) {
                        stringConstant += " ";
                    }
                    splitSymbols(word);
                }
            }
        }

    }

    private void splitSymbols (String word) {
        if (word.equals("")) {
            return;
        }
        boolean inserted = false;
        String tkn = word;
        for (char c : word.toCharArray()) {
            if (c == '"') {
                if (!stringConstantBuilder) {
                    stringConstant += "" + c;
                    stringConstantBuilder = true;
                } else {
                    stringConstant += "" + c;
                    stringConstantBuilder = false;
                    tokens.add(stringConstant);
                }

            } else if (stringConstantBuilder) {
                stringConstant += "" + c;
            } else if (SYMBOL_LIST.contains(c)) {
                tkn  = word.substring(0,word.indexOf(c));
                if (!tkn.equals("") && !tkn.equals("\"") && !tkn.equals(""+c)) {
                    tokens.add(tkn);
                }
                tokens.add("" + c);
                word = word.substring(word.indexOf(c)+1);
                inserted = true;
            }
        }
        if(!word.equals(tkn) && !word.equals("")){
            tokens.add(word);
        }
        if (!inserted && !stringConstantBuilder) {
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
        String pattern = "\".*\"";
        return stringVal.matches(pattern);
    }


    public TokenTypeEnum tokenType () {
        if (isKeyword(token)) {
            return TokenTypeEnum.KEYWORD;
        }
        if (isSymbol(token.charAt(0))) {
            return TokenTypeEnum.SYMBOL;
        }
        try {
            if (isIntValue(Integer.valueOf(token))) {
                return TokenTypeEnum.INT_CONST;
            }
        }
        catch (Exception e) {}
        if (isStringVal(token)) {
            return TokenTypeEnum.STRING_CONST;
        }
        if (isIdentifier(token)) {
            return TokenTypeEnum.IDENTIFIER;
        }
        return null;
    }

    public KeywordsEnum isReturningIntBoolCharVoid () {
        String boolType = KeywordsEnum.BOOLEAN.getType();
        String intType = KeywordsEnum.INT.getType();
        String charType = KeywordsEnum.CHAR.getType();
        String voidType = KeywordsEnum.VOID.getType();

        if (token.equals(boolType)) {
            return KeywordsEnum.BOOLEAN;
        }

        if (token.equals(intType)) {
            return KeywordsEnum.INT;
        }

        if (token.equals(charType)) {
            return KeywordsEnum.CHAR;
        }

        if (token.equals(voidType)) {
            return KeywordsEnum.VOID;
        }

        return null;
    }

    public String stringVal () {
        return token.replaceAll("\"", "");
    }

    public KeywordsEnum keyWord () {
        return KeywordsEnum.valueOf(token.toUpperCase());
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

    public void setToken(String token) {
        this.token = token;
    }
}
