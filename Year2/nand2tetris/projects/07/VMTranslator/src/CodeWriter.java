import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter outfile;
    private AssemblyCommands asmCommands = new AssemblyCommands();

    private int lineNumber = 1;

    public CodeWriter(FileWriter outfile) throws IOException {
        this.outfile = outfile;
    }

    public void writeArithmetic(String command) throws IOException {
        outfile.write(asmCommands.getArithmeticCommands(command));
    }

    public void writePushPop(String command,String segment, int index) throws IOException {
        if (segment.equals("pointer")){
            outfile.write(asmCommands.getPushPopCommands(command+segment+index,index)+" //"+lineNumber+"\n");
            lineNumber++;
        }
        else {
            outfile.write(asmCommands.getPushPopCommands(command+segment,index)+" //"+lineNumber+"\n");
            lineNumber++;
        }
    }

    public void close() throws IOException {
        outfile.close();
    }
}
