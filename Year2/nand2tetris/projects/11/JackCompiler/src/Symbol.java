public class Symbol {

    private String name;
    private String type;
    private SymbolKindEnum kind;
    private int index;

    public Symbol (String name, String type, SymbolKindEnum kind, int index) {
        this.name = name;
        this.type = type;
        this.kind = kind;
        this.index = index;
    }

    public String name() { return name; }

    public String type() { return type; }

    public String kind() { return kind.getKind(); }

    public int index() { return index; }
}
