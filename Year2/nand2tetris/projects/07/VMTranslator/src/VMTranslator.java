import java.io.File;
import java.io.FileWriter;

public class VMTranslator {

    public static void main (String[] args) throws Exception {
        File inputFile = new File(args[0]);
        FileWriter outputFile = new FileWriter(args[0].replace(".vm", ".asm"),false);
        CodeWriter codeWriter = new CodeWriter(outputFile);
        codeWriter.setFileName(inputFile.getName().replace(".vm",""));
        Parser parser = new Parser(inputFile);
        while (parser.hasMoreLine()) {
            parser.advance();
            if(parser.commandType() == InstructionsEnum.C_ARITHMETIC.getType()) {
                codeWriter.writeArithmetic(parser.arg1());
            }
            if (parser.commandType() == InstructionsEnum.C_PUSH.getType()) {
                codeWriter.writePushPop("push",parser.arg1(), parser.arg2());
            }
            if (parser.commandType() == InstructionsEnum.C_POP.getType()) {
                codeWriter.writePushPop("pop",parser.arg1(), parser.arg2());
            }
        }
        codeWriter.close();
    }
}