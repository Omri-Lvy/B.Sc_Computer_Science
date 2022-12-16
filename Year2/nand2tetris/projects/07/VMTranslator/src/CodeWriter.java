import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter outfile;
    private AssemblyCommands asmCommands = new AssemblyCommands();

    public CodeWriter(FileWriter outfile) throws IOException {
        this.outfile = outfile;
    }

    public void writeArithmetic(String command) throws Exception {
        outfile.write(asmCommands.getArithmeticCommands(command));
    }

    public void writePushPop(String command,String segment, int index) throws Exception {
        if (segment.equals("pointer")){
            outfile.write(asmCommands.getPushPopCommands(command+segment+index));
        }
        else {
            outfile.write(asmCommands.getPushPopCommands(command+segment,index));
        }
    }

    public void close() throws IOException {
        outfile.close();
    }
}
