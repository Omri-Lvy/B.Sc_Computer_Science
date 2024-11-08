public enum InstructionsEnum {

    C_ARITHMETIC("C_ARITHMETIC"),
    C_PUSH("C_PUSH"),
    C_POP("C_POP"),
    C_LABEL("C_LABEL"),
    C_GOTO("C_GOTO"),
    C_IF("C_IF"),
    C_FUNCTION("C_FUNCTION"),
    C_RETURN("C_RETURN"),
    C_CALL("C_CALL");
    InstructionsEnum(String type) {
        this.type = type;
    };
    private String type;

    public String getType () {
        return type;
    }
}
