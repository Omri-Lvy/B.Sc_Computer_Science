import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.Arrays;

public class VMTranslator {

    public static void main (String[] args) throws Exception {
        // Filtering only .vm files from given directory
        FileFilter fileFilter = pathname -> pathname.getName().endsWith(".vm");
        // insert all given vm files into array
        File[] filesList = new File(args[0]).isDirectory() ? new File(args[0]).listFiles(fileFilter) : new File[]{ new File(args[0]) };
        FileWriter outputFile = new FileWriter(new File(args[0]).isDirectory() ?
                args[0] + "/" + new File(args[0]).getName() + ".asm" :
                args[0].replace(".vm", ".asm"), false);
        CodeWriter codeWriter = new CodeWriter(outputFile);
        if (new File(args[0]).isDirectory() && new File(args[0]+"/Sys.vm").exists()){
            codeWriter.writeInit();
        }
        // Translate all the given vm files
        for (File file : filesList) {
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
                } else if (parser.commandType() == InstructionsEnum.C_FUNCTION.getType()) {
                    codeWriter.writeFunction(parser.arg1(), parser.arg2());
                } else if (parser.commandType() == InstructionsEnum.C_CALL.getType()) {
                    codeWriter.writeCall(parser.arg1(), parser.arg2());
                } else if (parser.commandType() == InstructionsEnum.C_RETURN.getType()) {
                    codeWriter.writeReturn();
                }
            }
        }
        codeWriter.close();
    }
}