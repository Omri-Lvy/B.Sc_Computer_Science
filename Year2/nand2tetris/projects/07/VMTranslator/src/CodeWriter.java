import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private FileWriter outfile;

    public CodeWriter(FileWriter outfile) {
        this.outfile = outfile;
    }

    public void writeArithmetic(String command) throws IOException {
        outfile.write(AsmCommandsEnum.valueOf(command).getAsmCommands());
    }

    public void writePushPop(String command) {

    }

    public void close() throws IOException {
        outfile.close();
    }
}
