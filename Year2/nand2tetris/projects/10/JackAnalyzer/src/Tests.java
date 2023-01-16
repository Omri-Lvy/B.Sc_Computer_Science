import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class Tests {

    File testFile = new File("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/10/ArrayTest/Main.jack");


    private JackTokenizer tokenizer;

    @Test
    public void advanceTest() throws FileNotFoundException {
        tokenizer = new JackTokenizer(testFile);
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            System.out.println(tokenizer.getToken());
        }
    }

}
