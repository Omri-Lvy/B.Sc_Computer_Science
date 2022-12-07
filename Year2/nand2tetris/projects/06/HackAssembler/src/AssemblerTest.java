import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;


public class AssemblerTest {

    File testFile = new File("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/06/add/Add.asm");
    SymbolTable table = new SymbolTable();
    Parser parser = new Parser(testFile);

    public AssemblerTest () throws FileNotFoundException {
    }

    @Test
    public void advanceTest() {
        parser.advance();
    }

    @Test
    public void hasNextLineTest() {
        System.out.println(parser.hasMoreLine());
    }

    @Test
    public void instructionTypeTest() {
        parser.advance();
        System.out.println(parser.instructionType());
    }

    @Test
    public void symbolTableTest() {
        table.addEntry("TEST",16);
        System.out.println(table.getAddress("TEST"));
    }

    @Test
    public void symbolTest() {
        parser.advance();
        String symbol = parser.symbol();
        System.out.println(symbol);
    }

    @Test
    public void parserJumps() {

    }


}
