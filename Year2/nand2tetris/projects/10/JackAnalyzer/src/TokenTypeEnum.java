public enum TokenTypeEnum {


    KEYWORD ("keyword"),
    SYMBOL ("symbol"),
    IDENTIFIER ("identifier"),
    INT_CONST ("integerConstant"),
    STRING_CONST ("stringConstant");

    TokenTypeEnum(String type) {
        this.type = type;
    };

    private String type;

    public String getType() {
        return type;
    }
}
