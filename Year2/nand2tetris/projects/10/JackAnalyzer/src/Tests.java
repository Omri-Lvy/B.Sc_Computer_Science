import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Tests {

    File testFile = new File("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/10/Tests/SquareTest/Main.jack");
    FileWriter testOut = new FileWriter("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/10/Square/SquareTest.xml");
    String[] folderPath = new String[]{"/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/10/Tests/ArrayTest/"};

    private JackTokenizer tokenizer;
    private CompilationEngine engine;
    private JackAnalyzer analyzer;


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
    public void tokenTypeTest() throws FileNotFoundException {
        tokenizer = new JackTokenizer(testFile);
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            System.out.println("<"+tokenizer.tokenType().getType()+"> "+
                    tokenizer.getToken()+" </"+tokenizer.tokenType().getType()+">");
        }
    }

    @Test
    public void compileClassTest() throws Exception {

        engine = new CompilationEngine(testFile,testOut);
        engine.compileClass();
    }

    @Test
    public void isIdentifierTest() throws FileNotFoundException {
        tokenizer = new JackTokenizer(testFile);
        tokenizer.setToken("y");
        System.out.println(tokenizer.tokenType().getType());
    }

    @Test
    public void testCompileEngine() throws Exception {
        JackAnalyzer.main(folderPath);
    }

}
