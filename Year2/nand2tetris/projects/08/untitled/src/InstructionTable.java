import java.util.HashMap;

public class InstructionTable {

    private HashMap<String, InstructionsEnum> instructionsTable = new HashMap<String, InstructionsEnum>();

    public InstructionTable() {
        instructionsTable.put("add", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("sub", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("neg", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("eq", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("gt", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("lt", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("and", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("or", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("not", InstructionsEnum.C_ARITHMETIC);
        instructionsTable.put("pop", InstructionsEnum.C_POP);
        instructionsTable.put("push", InstructionsEnum.C_PUSH);
        instructionsTable.put("label", InstructionsEnum.C_LABEL);
        instructionsTable.put("goto", InstructionsEnum.C_GOTO);
        instructionsTable.put("if-goto", InstructionsEnum.C_IF);
        instructionsTable.put("Function", InstructionsEnum.C_FUNCTION);
        instructionsTable.put("Call", InstructionsEnum.C_CALL);
        instructionsTable.put("return", InstructionsEnum.C_RETURN);
    }

    public String getType(String instruction) {
        return instructionsTable.get(instruction).getType();
    }




}
