public enum SymbolKindEnum {
    STATIC ("static"),
    FIELD ("field"),
    ARG ("argument"),
    VAR ("var"),
    NONE ("none");

    SymbolKindEnum(String kind) {
        this.kind = kind;
    };

    private String kind;

    public String getKind() {
        return kind;
    }
}
