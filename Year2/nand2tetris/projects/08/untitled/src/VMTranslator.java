import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;

public class VMTranslator {


    public static void main (String[] args) throws Exception {
        // Filtering only .vm files from given directory
        FileFilter fileFilter = pathname -> pathname.getName().endsWith(".vm");
        // insert all given vm files into array
        File[] filesList = new File(args[0]).isDirectory() ? new File(args[0]).listFiles(fileFilter) : new File[]{ new File(args[0]) };
        // Translate all the given vm files
        for (File file : filesList) {
            FileWriter outputFile = new FileWriter(file.getCanonicalPath().replace(".vm", ".asm"), false);
            CodeWriter codeWriter = new CodeWriter(outputFile);
            codeWriter.setFileName(file.getName().replace(".vm", ""));
            Parser parser = new Parser(file);
            while (parser.hasMoreLine()) {
                parser.advance();
                if (parser.commandType() == InstructionsEnum.C_ARITHMETIC.getType()) {
                    codeWriter.writeArithmetic(parser.arg1());
                } else if (parser.commandType() == InstructionsEnum.C_PUSH.getType()) {
                    codeWriter.writePushPop("push", parser.arg1(), parser.arg2());
                } else if (parser.commandType() == InstructionsEnum.C_POP.getType()) {
                    codeWriter.writePushPop("pop", parser.arg1(), parser.arg2());
                } else if (parser.commandType() == InstructionsEnum.C_IF.getType()) {
                    codeWriter.writeIf(parser.arg1());
                } else if (parser.commandType() == InstructionsEnum.C_LABEL.getType()) {
                    codeWriter.writeLabel(parser.arg1());
                } else if (parser.commandType() == InstructionsEnum.C_GOTO.getType()) {
                    codeWriter.writeGoto(parser.arg1());
                }
            }
            codeWriter.close();
        }
    }
}