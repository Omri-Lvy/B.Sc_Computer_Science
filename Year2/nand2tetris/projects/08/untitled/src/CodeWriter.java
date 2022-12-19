import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter outfile;
    private String fileName;
    private AssemblyCommands asmCommands = new AssemblyCommands();

    public CodeWriter(FileWriter outfile) throws IOException {
        this.outfile = outfile;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        System.out.println("Translating " + fileName);
    }

    public void writeArithmetic(String command) throws Exception {
        outfile.write(asmCommands.getArithmeticCommands(command));
    }

    public void writePushPop(String command,String segment, int index) throws Exception {
        if (segment.equals("pointer")){
            outfile.write(asmCommands.getPushPopCommands(command+segment+index));
        }
        else if (segment.equals("static")){
            outfile.write(asmCommands.getPushPopCommands(command+segment,index,fileName));
        }
        else {
            outfile.write(asmCommands.getPushPopCommands(command+segment,index));
        }
    }

    public void writeLabel(String label) throws IOException {
        outfile.write("("+label+")\n");
    }
    public void writeGoto(String label) throws IOException {
        outfile.write(
                "@"+label+"\n"+
                    "D;JMP\n"
        );

    }
    public void writeIf(String label) throws IOException {
        outfile.write(
                "@SP\n" +
                    "AM=M-1\n" +
                    "D=M\n" +
                    "@"+label+"\n"+
                    "D;JNE\n"
        );
    }
    public void writeFunction(String functionName, int nArgs){}
    public void writeCall(String functionName, int nArgs){}
    public void writeReturn(){}

    public void close() throws IOException {
        outfile.close();
    }
}
