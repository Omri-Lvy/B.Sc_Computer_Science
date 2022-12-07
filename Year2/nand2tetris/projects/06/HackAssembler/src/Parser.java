import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private Scanner scanner;
    private String currentInstruction;
    private Code code = new Code();

    public Parser (File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    public boolean hasMoreLine () {
        return scanner.hasNextLine();
    }
    public void advance () {
        if (hasMoreLine()) {
            String line = scanner.nextLine();
            if (!line.contains("//") && !line.isEmpty()) {
                currentInstruction = line.replaceAll(" ", "");
            } else {
                advance();
            }
        }
    }
    public String instructionType () {
        if (currentInstruction.matches("[(](.*)[)]")) {
            return InstructionsEnum.L_INSTRUCTIONS.getType();
        } else if (currentInstruction.charAt(0) == '@') {
            return InstructionsEnum.A_INSTRUCTIONS.getType();
        }
        return InstructionsEnum.C_INSTRUCTIONS.getType();
    }
    public String symbol () {
        if (instructionType() == InstructionsEnum.A_INSTRUCTIONS.getType()) {
            return (currentInstruction.substring(1));
        }
        return (currentInstruction.substring(1, currentInstruction.length() - 1));
    }
    public String dest () {
        if (currentInstruction.contains("=")){
            return this.code.dest(currentInstruction.split("=")[0]);
        }
        return this.code.dest("null");

    }
    public String comp () {
        try {
            return code.comp(currentInstruction.split("=")[1].split(";")[0]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return code.comp(currentInstruction.split(";")[0]);
        }

    }
    public String jump () {
        try {
            return code.jump(currentInstruction.split(";")[1]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return code.jump("null");
        }
    }

    public String getCurrentInstruction () {
        return currentInstruction;
    }
}
