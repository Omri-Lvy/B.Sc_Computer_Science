import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter outfile;
    private String fileName;
    private String functionName = "";
    private int returnIndex = 0;
    private AssemblyCommands asmCommands = new AssemblyCommands();

    public CodeWriter (FileWriter outfile) throws IOException {
        this.outfile = outfile;
        outfile.write(asmCommands.getInit());
    }

    public void setFileName (String fileName) {
        this.fileName = fileName;
        System.out.println("Translating " + fileName);
    }

    public void writeArithmetic (String command) throws Exception {
        outfile.write(asmCommands.getArithmeticCommands(command));
    }

    public void writePushPop (String command, String segment, int index) throws Exception {
        if (segment.equals("pointer")) {
            outfile.write(asmCommands.getPushPopCommands(command + segment + index));
        } else if (segment.equals("static")) {
            outfile.write(asmCommands.getPushPopCommands(command + segment, index, fileName));
        } else {
            outfile.write(asmCommands.getPushPopCommands(command + segment, index));
        }
    }

    public void writeLabel (String label) throws IOException {
        outfile.write(asmCommands.getLabelCommand(functionName+"$"+label));
    }

    public void writeGoto (String label) throws IOException {
        outfile.write(asmCommands.getGotoCommand(functionName+"$"+label));
    }

    public void writeIf (String label) throws IOException {
        outfile.write(asmCommands.getIfCommand(functionName+"$"+label));
    }

    public void writeFunction (String functionName, int nArgs) throws Exception {
        this.functionName = functionName;
        this.returnIndex = 0;
        outfile.write(asmCommands.getLabelCommand(functionName));
        for (int i = 0; i < nArgs; i++) {
            writePushPop("push", "constant", 0);
        }
    }

    public void writeCall (String functionName, int nArgs) throws IOException {
        outfile.write(asmCommands.getCallCommand(functionName + "$ret." + returnIndex++));
    }

    public void writeReturn () {
    }

    public void close () throws IOException {
        outfile.close();
    }
}
