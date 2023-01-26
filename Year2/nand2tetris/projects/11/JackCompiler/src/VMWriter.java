import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VMWriter {

    private FileWriter outFile;

    public VMWriter(FileWriter file) throws IOException {
        outFile = file;
    }

    // write a VM push command
    public void writePush(String segment, int index) throws IOException {
        outFile.write("push " + segment + " " + index + "\n");
    }

    // write a VM pop command
    public void writePop(String segment, int index) throws IOException {
        outFile.write("pop " + segment + " " + index + "\n");
    }

    // write a VM arithmetic-logical command
    public void writeArithmetic(char command) throws IOException {
        if (command = '*') {
            writeCall("Math.multiply",2);
        }
        else if (command = '/' ) {
            writeCall("Math.divide",2);
        }
        else if (command = '+') {
            outFile.write("add\n");
        }
        else if (command = '-') {
            outFile.write("sub\n");
        }
        else if (command = '<') {
            outFile.write("lt\n");
        }
        else if (command = '>') {
            outFile.write("gt\n");
        }
        else {
            outFile.write(command + "\n");
        }
    }

    // write a VM label command
    public void writeLabel(String label) throws IOException {
        outFile.write(label + "\n");
    }

    // write a VM goto command
    public void writeGoto(String label) throws IOException {
        outFile.write("goto " + label + "\n");
    }

    // write a VM if-goto command
    public void writeIf(String label) throws IOException {
        outFile.write("if-goto " + label + "\n");
    }

    // write a VM call command
    public void writeCall(String name, int nArgs) throws IOException {
        outFile.write("call " + name + " " + nArgs + "\n");
    }

    // write a VM function command
    public void writeFunction(String name, int nVars) throws IOException {
        outFile.write("function " + name + " " + nVars + "\n");
    }

    // write a VM return command
    public void writeReturn() throws IOException {
        outFile.write("return\n");
    }

    // closes the output file
    public void close() throws IOException {
        outFile.close();
    }


}
