public enum KeywordsEnum {
    CLASS ("class"),
    METHOD ("method"),
    FUNCTION ("function"),
    CONSTRUCTOR ("constructor"),
    INT ("int"),
    BOOLEAN ("boolean"),
    CHAR ("char"),
    VOID ("void"),
    VAR("var"),
    STATIC ("static"),
    FIELD ("field"),
    LET ("let"),
    DO ("do"),
    IF("if"),
    ELSE ("else"),
    WHILE ("while"),
    RETURN ("return"),
    TRUE ("true"),
    FALSE ("false"),
    NULL ("null"),
    THIS ("this");

    KeywordsEnum(String type) {
        this.type = type;
    };

    private String type;

    public String getType() {
        return type;
    }
}
