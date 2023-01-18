import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Tests {

    File testFile = new File("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/10/ArrayTest/Main.jack");
    FileWriter testOut = new FileWriter("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/10/ArrayTest/MainTest.xml");


    private JackTokenizer tokenizer;
    private CompilationEngine engine;

    public Tests () throws IOException {
    }

    @Test
    public void advanceTest() throws FileNotFoundException {
        tokenizer = new JackTokenizer(testFile);
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            System.out.println(tokenizer.getToken());
        }
    }

    @Test
    public void compileClassTest() throws Exception {

        engine = new CompilationEngine(testFile,testOut);
        engine.compileClass();
    }

}
