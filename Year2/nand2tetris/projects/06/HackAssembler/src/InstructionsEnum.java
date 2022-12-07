public enum InstructionsEnum {

    A_INSTRUCTIONS("A_INSTRUCTIONS"),
    C_INSTRUCTIONS("C_INSTRUCTIONS"),
    L_INSTRUCTIONS("L_INSTRUCTIONS");

    InstructionsEnum(String type) {
        this.type = type;
    };
    private String type;

    public String getType () {
        return type;
    }
}
