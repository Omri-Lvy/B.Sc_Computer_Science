import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private Scanner scanner;
    private String currentInstruction;
    private InstructionTable instructionsTable = new InstructionTable();

    public Parser (File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    // check whether the file has more line
    public boolean hasMoreLine () {
        return scanner.hasNextLine();
    }

    // advance to the next instruction line in the code file
    public void advance () {
        if (hasMoreLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith("//") && !line.isEmpty()) {
                currentInstruction = line.trim().split("//")[0];
            } else {
                advance();
            }
        }
    }

    public String commandType () {
        String type = currentInstruction.split(" ")[0];
        return instructionsTable.getType(type);
    }

    public String arg1 () {
        if (commandType() == InstructionsEnum.C_ARITHMETIC.getType()) {
            return currentInstruction.split(" ")[0];
        }
        if (commandType() != InstructionsEnum.C_RETURN.getType()) {
            return currentInstruction.split(" ")[1];
        }
        return null;
    }

    public Integer arg2 () {
        if (commandType() == InstructionsEnum.C_POP.getType() ||
                commandType() == InstructionsEnum.C_PUSH.getType() ||
                commandType() == InstructionsEnum.C_FUNCTION.getType() ||
                commandType() == InstructionsEnum.C_CALL.getType()) {
            return Integer.parseInt(currentInstruction.split(" ")[2]);
        }
        return null;
    }
}
