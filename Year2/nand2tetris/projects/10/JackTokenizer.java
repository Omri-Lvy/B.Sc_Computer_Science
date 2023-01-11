import java.io.File;

public class JackTokenizer {

    public JackTokenizer (File inputFile) {}

    public boolean hasMoreTokens() {
        return false;
    }

    public void advance(){}

    public TokenTypeEnum tokenType() {
        return null;
    }

    public KeyworksEnum keyWord() {
        return null;
    }

    public char symbol() {
        return 'a';
    }

    public String identifier() {
        return "a";
    }

    public int intVal(){
        return 0;
    }

    public String stringVal() {
        return "a";
    }
}
