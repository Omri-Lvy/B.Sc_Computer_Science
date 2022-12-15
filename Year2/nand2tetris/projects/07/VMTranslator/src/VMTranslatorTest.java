import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class VMTranslatorTest {

    File testFile = new File("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm");
    FileWriter testFileOut = new FileWriter("/Users/omrilevy/Documents/B.Sc_Computer_Science/Year2/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAddTestOut.asm",false);
    Parser parser = new Parser(testFile);
    CodeWriter writer = new CodeWriter(testFileOut);

    public VMTranslatorTest () throws IOException {
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
        System.out.println(parser.commandType());
    }

    @Test
    public void arg1test() {
        parser.advance();
        System.out.println(parser.arg1());
    }

    @Test
    public void arg2test() {
        parser.advance();
        System.out.println(parser.arg2());
    }

    @Test
    public void writeArithmeticTest() throws IOException {
        parser.advance();
        while (!Objects.equals(parser.commandType(), InstructionsEnum.C_ARITHMETIC.getType())) {
            parser.advance();
        }
        writer.writeArithmetic(parser.arg1());
        writer.close();
    }

    @Test
    public void commandWriteTest() throws IOException {
        while (parser.hasMoreLine()) {
            parser.advance();
            if(parser.commandType() == InstructionsEnum.C_ARITHMETIC.getType()) {
                writer.writeArithmetic(parser.arg1());
            }
            if (parser.commandType() == InstructionsEnum.C_PUSH.getType()) {
                writer.writePushPop("push",parser.arg1(), parser.arg2());
            }
            if (parser.commandType() == InstructionsEnum.C_POP.getType()) {
                writer.writePushPop("pop",parser.arg1(), parser.arg2());
            }
        }
        writer.close();
    }


}
