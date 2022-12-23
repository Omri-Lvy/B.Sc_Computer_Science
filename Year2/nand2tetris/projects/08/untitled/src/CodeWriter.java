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
    }

    public void writeInit() throws Exception {
        outfile.write(asmCommands.getInit());
        writeCall("Sys.init", 0);
    }

    public void setFileName (String fileName) throws Exception {
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
        outfile.write(asmCommands.getLabelCommand(functionName != "" ? functionName + "$" + label : label));
    }

    public void writeGoto (String label) throws IOException {
        outfile.write(asmCommands.getGotoCommand(functionName != "" ? functionName + "$" + label : label));
    }

    public void writeIf (String label) throws IOException {
        outfile.write(asmCommands.getIfCommand(functionName != "" ? functionName + "$" + label : label));
    }

    public void writeFunction (String functionName, int nVars) throws Exception {
        this.functionName = functionName;
        outfile.write(asmCommands.getLabelCommand(functionName));
        for (int i = 0; i < nVars; i++) {
            writePushPop("push", "constant", 0);
        }
    }

    public void writeCall (String functionName, int nArgs) throws IOException {
        outfile.write(asmCommands.getCallCommand(functionName,functionName + "$ret." + returnIndex,nArgs));
        returnIndex++;
    }

    public void writeReturn () throws IOException {
        outfile.write(asmCommands.getReturnCommand());
    }

    public void close () throws IOException {
        outfile.close();
    }
}
